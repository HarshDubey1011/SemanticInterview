ALTER TABLE transcript_chunk
DROP COLUMN embedding;

ALTER TABLE transcript_chunk
    ADD COLUMN embedding vector(3072);