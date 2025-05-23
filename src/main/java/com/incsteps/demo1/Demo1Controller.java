package com.incsteps.demo1;

import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.store.embedding.CosineSimilarity;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

@Controller("/demo1")
public class Demo1Controller {

    EmbeddingModel model = new AllMiniLmL6V2EmbeddingModel();

    @Post("/compare")
    public double vectorize(String a, String b) {

        var embedding1 = model.embed(a).content();

        var embedding2 = model.embed(b).content();

        return CosineSimilarity.between(embedding1, embedding2);
    }


}
