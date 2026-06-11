CREATE TABLE transcript_chunk(
                                 id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,

                                 transcript_id BIGINT NOT NULL,

                                 chunk_text TEXT NOT NULL,

                                 FOREIGN KEY (transcript_id)
                                     REFERENCES semantic_transcript(id)
);