package com.incsteps.demo5;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Factory
public class Demo5Factory {

    @Named("chatModelDemo5")
    @Singleton
    ChatLanguageModel ollamaChatModel(
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

    @Named("chatMemoryDemo5")
    @Singleton
    ChatMemory chatMemory(){
        return MessageWindowChatMemory.withMaxMessages(100);
    }

    @Singleton
    Demo5Assistant demo5Assistant(
            @Named("chatModelDemo5") ChatLanguageModel chatModel,
            @Named("chatMemoryDemo5") ChatMemory chatMemory
    ){
        return AiServices.builder(Demo5Assistant.class)
                .chatLanguageModel(chatModel)
                .chatMemory(chatMemory)
                .build();
    }

}
