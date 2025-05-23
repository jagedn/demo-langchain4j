package com.incsteps.demo1;

import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Factory
public class Demo1Factory {

    @Singleton
    @Named("EmbeddingModelDemo1")
    EmbeddingModel demo1EmbeddingModelOllama(
            @Value("${demo.ollama.url}")String url,
            @Value("${demo.ollama.model}")String model
    ){
        return OllamaEmbeddingModel.builder()
                .baseUrl(url)
                .modelName(model)
                .build();
    }

}
