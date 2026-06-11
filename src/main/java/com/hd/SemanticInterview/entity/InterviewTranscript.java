package com.hd.SemanticInterview.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="semantic_transcript")
public class InterviewTranscript {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "filename", nullable = false)
    private String fileName;
    @Column(name = "content", nullable = false)
    private String content;
    @CreationTimestamp
    @Column(name = "uploaded_at", nullable = false)
    private LocalDate uploadDate;
    @OneToMany(
            mappedBy = "interviewTranscript",
            cascade = CascadeType.ALL
    )
    private List<TranscriptChunk> chunks = new ArrayList<>();

    public InterviewTranscript() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDate uploadDate) {
        this.uploadDate = uploadDate;
    }
}
