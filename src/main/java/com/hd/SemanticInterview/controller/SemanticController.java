package com.hd.SemanticInterview.controller;

import com.hd.SemanticInterview.dto.SemanticTranscriptDto;
import com.hd.SemanticInterview.dto.UploadTranscriptDto;
import com.hd.SemanticInterview.service.SemanticService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
public class SemanticController {
    private final SemanticService semanticService;

    public SemanticController(SemanticService semanticService) {
        this.semanticService = semanticService;
    }

    @PostMapping("/api/transcripts/upload")
    public ResponseEntity<UploadTranscriptDto> upload(@RequestParam("file") MultipartFile file) throws IOException {
        String content =
                new String(file.getBytes(), StandardCharsets.UTF_8);
        var dto = semanticService.saveData(content, file.getOriginalFilename());
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping("/api/transcripts")
    public ResponseEntity<List<SemanticTranscriptDto>> getAll() {
       var list = semanticService.getData();
       return ResponseEntity.ok(list);
    }

    @GetMapping("/api/transactions/{id}")
    public ResponseEntity<SemanticTranscriptDto> getById(@PathVariable Long id) {
        var dto = semanticService.getDataById(id);
        return ResponseEntity.ok(dto);
    }
}
