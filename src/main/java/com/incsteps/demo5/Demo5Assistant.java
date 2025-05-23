package com.incsteps.demo5;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface Demo5Assistant {

    @SystemMessage("Eres un asistente de viajes. Responde de forma muy educada a la pregunta del usuario.")
    String chatEducado(@UserMessage String question);


    @SystemMessage("Eres un amigo del usuario y le puedes recomendar viajes. Como hay confianza usa slang para contestarle")
    String chatColoquial(@UserMessage String question);
}
