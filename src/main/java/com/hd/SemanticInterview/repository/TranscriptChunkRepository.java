package com.hd.SemanticInterview.repository;

import com.hd.SemanticInterview.entity.TranscriptChunk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TranscriptChunkRepository extends JpaRepository<TranscriptChunk, Long> {
    @Query(value = """
    SELECT *
    FROM transcript_chunk
    ORDER BY embedding <=> CAST(:embedding AS vector)
    LIMIT 5
    """, nativeQuery = true)
    List<TranscriptChunk> semanticSearch(String embedding);

        @Modifying
        @Transactional
        @Query(value = """
    INSERT INTO transcript_chunk
    (transcript_id, chunk_text, embedding)
    VALUES (
        :transcriptId,
        :chunkText,
        CAST(:embedding AS vector)
    )
    """, nativeQuery = true)
    void saveChunk(
            @Param("transcriptId") Long transcriptId,
            @Param("chunkText") String chunkText,
            @Param("embedding") String embedding
    );
}
