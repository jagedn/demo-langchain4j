package com.incsteps.demo5;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;

@Controller("/demo5")
public class Demo5Controller {

    @Inject
    Demo5Assistant demo5Assistant;


    @Post("/formal")
    String chatFormat(String question){
        return demo5Assistant.chatEducado(question);
    }

    @Post("/coloquial")
    String chatColoquial(String question){
        return demo5Assistant.chatColoquial(question);
    }

}
