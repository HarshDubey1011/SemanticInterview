CREATE TABLE semantic_transcript (
                                     id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                                     filename VARCHAR(255) NOT NULL,
                                     content TEXT NOT NULL,
                                     uploaded_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);