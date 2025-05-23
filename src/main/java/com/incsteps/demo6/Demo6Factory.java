package com.incsteps.demo6;

import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.pgvector.DefaultMetadataStorageConfig;
import dev.langchain4j.store.embedding.pgvector.MetadataStorageConfig;
import dev.langchain4j.store.embedding.pgvector.MetadataStorageMode;
import dev.langchain4j.store.embedding.pgvector.PgVectorEmbeddingStore;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.util.List;

@Factory
public class Demo6Factory {

    @Singleton
    @Named("EmbeddingModelDemo6")
    EmbeddingModel embeddingModel(){
        return new AllMiniLmL6V2EmbeddingModel();
    }


    @Singleton
    @Named("EmbeddingStoreDemo6")
    EmbeddingStore<TextSegment> embeddingStorePgVector(
            @Value("${demo.pgvector.host}")String host,
            @Value("${demo.pgvector.database}")String database,
            @Value("${demo.pgvector.user}")String user,
            @Value("${demo.pgvector.password}")String password,
            @Named("EmbeddingModelDemo6")EmbeddingModel embeddingModel
    ){
        MetadataStorageConfig metaConfig = DefaultMetadataStorageConfig.builder()
                .columnDefinitions(List.of("metadatas json"))
                .storageMode(MetadataStorageMode.COMBINED_JSON)
                .build();

        return PgVectorEmbeddingStore.builder()
                .host(host)
                .port(5432)
                .database(database)
                .user(user)
                .password(password)
                .table("demo6_embeddings")
                .dimension(embeddingModel.dimension())
                .createTable(true)
                .metadataStorageConfig(metaConfig)
                .build();
    }

    @Singleton
    @Named("EmbeddingStoreIngestorDemo6")
    EmbeddingStoreIngestor embeddingStoreIngestor(@Named("EmbeddingStoreDemo6")EmbeddingStore<TextSegment> embeddingStore,
                                                  @Named("EmbeddingModelDemo6")EmbeddingModel embeddingModel){
        return EmbeddingStoreIngestor.builder()
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .documentSplitter(DocumentSplitters.recursive(300, 50))
                .build();
    }

    @Named("contentRetrieverDemo6")
    @Singleton
    ContentRetriever contentRetriever(
            @Named("EmbeddingStoreDemo6")EmbeddingStore<TextSegment> embeddingStore,
            @Named("EmbeddingModelDemo6")EmbeddingModel embeddingModel,
            @Value("${search.min-score:0.7}") double minScore,
            @Value("${search.max-results:3}") Integer maxResults) {

        return EmbeddingStoreContentRetriever.builder()
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .minScore(minScore)
                .maxResults(maxResults)
                .build();
    }


    @Named("chatModelDemo6")
    @Singleton
    ChatLanguageModel ollamaChatModel(
            @Value("${demo.ollama.url}")String url,
            @Value("${demo.ollama.model}")String model,
            @Value("${demo.ollama.temperature:0.6}")double temperature
    ){
        return OllamaChatModel.builder()
                .baseUrl(url)
                .temperature(temperature)
                .modelName(model)
                .build();
    }

    @Named("chatMemoryDemo6")
    @Singleton
    ChatMemory chatMemory(){
        return MessageWindowChatMemory.withMaxMessages(100);
    }

    @Singleton
    Demo6Assistant assistant(@Named("chatModelDemo6") ChatLanguageModel chatModel,
                                  @Named("chatMemoryDemo6") ChatMemory chatMemory,
                                  @Named("contentRetrieverDemo6")ContentRetriever contentRetriever){
        return AiServices.builder(Demo6Assistant.class)
                .chatLanguageModel(chatModel)
                .chatMemory(chatMemory)
                .contentRetriever(contentRetriever)
                .build();
    }
}
