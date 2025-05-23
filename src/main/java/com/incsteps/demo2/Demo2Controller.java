package com.incsteps.demo2;

import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingStore;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller("/demo2")
public class Demo2Controller {

    @Inject
    @Named("EmbeddingModelDemo2")
    EmbeddingModel embeddingModel;

    @Inject
    @Named("EmbeddingStoreDemo2")
    EmbeddingStore<TextSegment> embeddingStore;

    @Post("/")
    public Map<String, String> save(String input) {

        var segment = TextSegment.from(input,
                Metadata.metadata("timestamp", Instant.now().toString()));

        var embedding = embeddingModel.embed(segment).content();

        return Map.of("id", embeddingStore.add(embedding, segment));
    }


    @Post("/search")
    public List<String>search(String search) {
        var queryEmbedding = embeddingModel.embed(search).content();

        var embeddingSearchRequest = EmbeddingSearchRequest.builder()
                .queryEmbedding(queryEmbedding)
                .maxResults(10)
                .build();

        List<EmbeddingMatch<TextSegment>> relevant = embeddingStore.search(embeddingSearchRequest).matches();

        return relevant.stream()
                .filter(e->e.score() > 0.85)
                .map(e->e.embedded().text()+", score:"+e.score()).collect(Collectors.toList());
    }

}
