package com.hd.SemanticInterview.repository;

import com.hd.SemanticInterview.entity.InterviewTranscript;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SemanticTranscriptRepository extends JpaRepository<InterviewTranscript, Long> {
}
