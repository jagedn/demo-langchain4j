package com.incsteps.demo1;

import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.CosineSimilarity;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Controller("/demo1")
public class Demo1OllamaController {

    @Inject
    @Named("EmbeddingModelDemo1")
    EmbeddingModel ollamaDemo1EmbeddingModel;

    @Post("/ollama")
    public double vectorizeOllama(String a, String b) {

        var embedding1 = ollamaDemo1EmbeddingModel.embed(a).content();

        var embedding2 = ollamaDemo1EmbeddingModel.embed(b).content();

        return CosineSimilarity.between(embedding1, embedding2);
    }


}
