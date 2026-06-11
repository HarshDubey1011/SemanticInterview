package com.hd.SemanticInterview.repository;

import com.hd.SemanticInterview.entity.InterviewTranscript;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SemanticTranscriptRepository extends JpaRepository<InterviewTranscript, Long> {
    List<InterviewTranscript> findByContentContainingIgnoreCase(String query);
}
