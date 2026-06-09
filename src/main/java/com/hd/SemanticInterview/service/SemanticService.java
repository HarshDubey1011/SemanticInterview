package com.hd.SemanticInterview.service;

import com.hd.SemanticInterview.dto.SemanticTranscriptDto;
import com.hd.SemanticInterview.dto.UploadTranscriptDto;
import com.hd.SemanticInterview.entity.InterviewTranscript;
import com.hd.SemanticInterview.repository.SemanticTranscriptRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SemanticService {
    private final SemanticTranscriptRepository semanticTranscriptRepository;

    public SemanticService(SemanticTranscriptRepository semanticTranscriptRepository){
        this.semanticTranscriptRepository = semanticTranscriptRepository;
    }

    public UploadTranscriptDto saveData(String content, String fileName) {
        InterviewTranscript interviewTranscript = new InterviewTranscript();
        interviewTranscript.setContent(content);
        interviewTranscript.setFileName(fileName);

        var savedTranscript = semanticTranscriptRepository.save(interviewTranscript);
        return new UploadTranscriptDto(savedTranscript.getId(),"Upload Successfully!", savedTranscript.getFileName());
    }

    public List<SemanticTranscriptDto> getData() {
        var data = semanticTranscriptRepository.findAll();
        return data.stream()
                .map(t -> new SemanticTranscriptDto(
                        t.getId(),
                        t.getContent(),
                        t.getFileName()
                ))
                .toList();
    }

    public SemanticTranscriptDto getDataById(Long id) {
        var data = semanticTranscriptRepository.findById(id).orElseThrow(() -> new NoSuchElementException("ID does not exist"));
        return new SemanticTranscriptDto(id, data.getContent(), data.getFileName());
    }
}
