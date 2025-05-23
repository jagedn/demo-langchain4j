package com.incsteps.demo2;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
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
public class Demo2Factory {

    @Singleton
    @Named("EmbeddingModelDemo2")
    EmbeddingModel demo1EmbeddingModelOllama(
            @Value("${demo.ollama.url}")String url,
            @Value("${demo.ollama.model}")String model
    ){
        return OllamaEmbeddingModel.builder()
                .baseUrl(url)
                .modelName(model)
                .build();
    }


    @Singleton
    @Named("EmbeddingStoreDemo2")
    EmbeddingStore<TextSegment> embeddingStorePgVector(
            @Value("${demo.pgvector.host}")String host,
            @Value("${demo.pgvector.database}")String database,
            @Value("${demo.pgvector.user}")String user,
            @Value("${demo.pgvector.password}")String password,
            @Named("EmbeddingModelDemo2")EmbeddingModel embeddingModel
    ){
        MetadataStorageConfig metaConfig = DefaultMetadataStorageConfig.builder()
                .columnDefinitions(List.of("metadatas json"))
                .storageMode(MetadataStorageMode.COMBINED_JSON)
                .build();

        return PgVectorEmbeddingStore.builder()
                .host(host)                           // Required: Host of the PostgreSQL instance
                .port(5432)                                  // Required: Port of the PostgreSQL instance
                .database(database)                        // Required: Database name
                .user(user)                             // Required: Database user
                .password(password)                     // Required: Database password
                .table("demo_embeddings")                      // Required: Table name to store embeddings
                .dimension(embeddingModel.dimension())       // Required: Dimension of embeddings
                .createTable(true)
                .metadataStorageConfig(metaConfig)
                .build();
    }

}
