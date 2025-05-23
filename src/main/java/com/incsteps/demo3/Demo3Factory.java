package com.incsteps.demo3;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Factory
public class Demo3Factory {

    @Named("chatModelDemo3")
    @Singleton
    ChatLanguageModel chatModel(
            @Value("${demo.ollama.url}")String url,
            @Value("${demo.ollama.model}")String model,
            @Value("${demo.ollama.temperature:0.5}")double temperature
    ){
        return OllamaChatModel.builder()
                .baseUrl(url)
                .temperature(temperature)
                .modelName(model)
                .build();
    }
}
