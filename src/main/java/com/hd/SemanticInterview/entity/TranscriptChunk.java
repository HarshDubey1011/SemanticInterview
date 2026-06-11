package com.hd.SemanticInterview.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "transcript_chunk")
public class TranscriptChunk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transcript_id", nullable = false)
    InterviewTranscript  interviewTranscript;

    @Column(name = "chunk_text", nullable = false)
    private String chunkText;

    @Column(columnDefinition = "vector(3072)")
    private String embedding;

    public TranscriptChunk() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InterviewTranscript getInterviewTranscript() {
        return interviewTranscript;
    }

    public void setInterviewTranscript(InterviewTranscript interviewTranscript) {
        this.interviewTranscript = interviewTranscript;
    }

    public String getChunkText() {
        return chunkText;
    }

    public void setChunkText(String chunkText) {
        this.chunkText = chunkText;
    }

    public String getEmbedding() {
        return embedding;
    }

    public void setEmbedding(String embedding) {
        this.embedding = embedding;
    }
}
