package com.incsteps.demo4;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Factory
public class Demo4Factory {

    @Named("chatModelDemo4")
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

    @Named("chatMemoryDemo4")
    @Singleton
    ChatMemory chatMemory(){
        return MessageWindowChatMemory.withMaxMessages(10);
    }

}
