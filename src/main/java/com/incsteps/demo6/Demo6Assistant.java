package com.incsteps.demo6;

import dev.langchain4j.service.Result;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;


public interface Demo6Assistant {

    @SystemMessage("eres un tecnico especializado en la lectura de documentos oficiales.\n" +
            "Response al usuario con respuestas simples.\n" +
            "Utiliza únicamente la información que se te proporciona,\n " +
            "NO uses fuentes de informacion externas")
    Result<String> analizaDocumento(@UserMessage String query);

}
