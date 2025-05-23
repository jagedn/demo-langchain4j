package com.incsteps.demo3;

import dev.langchain4j.model.chat.ChatLanguageModel;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Controller("/demo3")
public class Demo3Controller {

    @Inject
    @Named("chatModelDemo3")
    ChatLanguageModel chatModel;

    @Post()
    String chat(String question){

        var response = chatModel.chat(question);

        return response;
    }


}
