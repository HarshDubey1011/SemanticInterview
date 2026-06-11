package com.hd.SemanticInterview.service;

import com.hd.SemanticInterview.entity.InterviewTranscript;
import com.hd.SemanticInterview.entity.TranscriptChunk;
import com.hd.SemanticInterview.repository.TranscriptChunkRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class TranscriptChunkService {
    private final TranscriptChunkRepository transcriptChunkRepository;
    private final EmbeddingService embeddingService;

    public TranscriptChunkService(TranscriptChunkRepository transcriptChunkRepository,  EmbeddingService embeddingService) {
        this.transcriptChunkRepository = transcriptChunkRepository;
        this.embeddingService = embeddingService;
    }

    public void saveChunk(InterviewTranscript transcript) {

        Arrays.stream(transcript.getContent().split("\\."))
                .map(String::trim)
                .filter(chunk -> !chunk.isBlank())
                .forEach(chunk -> {

                    var embedding = embeddingService
                            .generateEmbedding(chunk)
                            .toString();

                    transcriptChunkRepository.saveChunk(
                            transcript.getId(),
                            chunk,
                            embedding
                    );
                });
    }
}
