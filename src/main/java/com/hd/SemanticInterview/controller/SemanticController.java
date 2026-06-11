package com.hd.SemanticInterview.controller;

import com.hd.SemanticInterview.dto.SemanticTranscriptDto;
import com.hd.SemanticInterview.dto.SummaryTranscriptDto;
import com.hd.SemanticInterview.dto.TranscriptChunkDto;
import com.hd.SemanticInterview.dto.UploadTranscriptDto;
import com.hd.SemanticInterview.service.EmbeddingService;
import com.hd.SemanticInterview.service.SemanticService;
import com.pgvector.PGvector;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Tag(name = "Transcript APIs")
@RestController
@RequestMapping("/api/transcripts")
public class SemanticController {
    private final SemanticService semanticService;
    private final EmbeddingService embeddingService;

    public SemanticController(SemanticService semanticService, EmbeddingService embeddingService) {
        this.semanticService = semanticService;
        this.embeddingService = embeddingService;
    }

    @Operation(summary = "Upload transcript file")
    @PostMapping("/upload")
    public ResponseEntity<UploadTranscriptDto> upload(@RequestParam("file") MultipartFile file) throws IOException {
        String content =
                new String(file.getBytes(), StandardCharsets.UTF_8);
        var dto = semanticService.saveData(content, file.getOriginalFilename());
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Get all transcripts")
    @GetMapping
    public ResponseEntity<List<SummaryTranscriptDto>> getAll() {
       var list = semanticService.getData();
       return ResponseEntity.ok(list);
    }

    @Operation(summary = "Get single transcripts")
    @GetMapping("/{id}")
    public ResponseEntity<SemanticTranscriptDto> getById(@PathVariable Long id) {
        var dto = semanticService.getDataById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/search")
    public ResponseEntity<List<SemanticTranscriptDto>> search(@RequestParam("query") String query) {
        var list = semanticService.searchQuery(query);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/test")
    public ResponseEntity<PGvector> test() {
        return ResponseEntity.ok(embeddingService.generateEmbedding(
                "Pricing was confusing"
        ));
    }
    @Operation(summary = "Semantic search using vector embeddings")
    @GetMapping("/semantic-search")
    public ResponseEntity<List<TranscriptChunkDto>> semanticSearch(
            @RequestParam("query") String query) {

        List<TranscriptChunkDto> result =
                embeddingService.semanticSearch(query);

        return ResponseEntity.ok(result);
    }
}
