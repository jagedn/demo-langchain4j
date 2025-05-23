package com.incsteps.demo6;

import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.apache.tika.ApacheTikaDocumentParser;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Controller("/demo6")
public class Demo6Controller {


    @Named("EmbeddingStoreIngestorDemo6")
    @Inject
    EmbeddingStoreIngestor embeddingStoreIngestor;

    @Inject
    Demo6Assistant demo6Assistant;


    @Get("/load{?path}")
    public boolean loadDocuments(Optional<String> path) throws IOException {
        var dir = path.orElse("boes");
        var files = Files.list(Path.of(dir)).toList();
        for (var file : files) {
            System.out.println(file);
            var document = FileSystemDocumentLoader.loadDocument(file, new ApacheTikaDocumentParser());
            embeddingStoreIngestor.ingest(document);
        }
        return true;
    }


    @Post("/search")
    public String searchDocuments(String query) throws IOException {
        var result = demo6Assistant.analizaDocumento(query);
        String answer = result.content();
        var sources = result.sources();
        StringBuilder ret = new StringBuilder();
        ret.append(answer);
        ret.append("\n-----\n");
        for (var source : sources) {
            ret.append("- ");
            source.metadata().forEach((key, value) -> ret.append(key).append("=").append(value).append("\n"));
        }
        return ret.toString();
    }

}
