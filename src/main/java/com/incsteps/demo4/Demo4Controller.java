package com.incsteps.demo4;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Controller("/demo4")
public class Demo4Controller {

    @Named("chatMemoryDemo4")
    @Inject
    ChatMemory chatMemory;

    @Inject
    @Named("chatModelDemo4")
    ChatLanguageModel chatModel;


    @Post()
    String chat(String question){
        chatMemory.add(UserMessage.from(question));

        var response = chatModel.chat(chatMemory.messages()).aiMessage();

        chatMemory.add(response);

        return response.text();
    }


}
