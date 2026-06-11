package com.hd.SemanticInterview.service;

import com.hd.SemanticInterview.dto.*;
import com.hd.SemanticInterview.entity.TranscriptChunk;
import com.hd.SemanticInterview.repository.TranscriptChunkRepository;
import com.pgvector.PGvector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class EmbeddingService {

    private final WebClient webClient;
    private final TranscriptChunkRepository  transcriptChunkRepository;

    @Value("${gemini.api-key}")
    private String apiKey;

    public EmbeddingService(WebClient webClient, TranscriptChunkRepository transcriptChunkRepository) {
        this.webClient = webClient;
        this.transcriptChunkRepository = transcriptChunkRepository;
    }

    public PGvector generateEmbedding(String text) {

        GeminiEmbeddingRequest request =
                new GeminiEmbeddingRequest(
                        new Content(
                                List.of(
                                        new Part(text)
                                )
                        )
                );

        GeminiEmbeddingResponse response =
                webClient.post()
                        .uri(uriBuilder ->
                                uriBuilder
                                        .scheme("https")
                                        .host("generativelanguage.googleapis.com")
                                        .path("/v1beta/models/gemini-embedding-001:embedContent")
                                        .queryParam("key", apiKey)
                                        .build())
                        .bodyValue(request)
                        .retrieve()
                        .bodyToMono(GeminiEmbeddingResponse.class)
                        .block();

        if (response == null) {
            throw new RuntimeException("Embedding response is null");
        }

        List<Float> values = response.embedding().values();

        float[] embeddingArray = new float[values.size()];

        for (int i = 0; i < values.size(); i++) {
            embeddingArray[i] = values.get(i);
        }

        return new PGvector(embeddingArray);
    }

    public List<TranscriptChunkDto> semanticSearch(String query) {

        PGvector queryEmbedding = generateEmbedding(query);

        List<TranscriptChunk> chunks =
                transcriptChunkRepository.semanticSearch(
                        queryEmbedding.toString());

        return chunks.stream()
                .map(chunk -> new TranscriptChunkDto(
                        chunk.getId(),
                        chunk.getChunkText()
                ))
                .toList();
    }
}