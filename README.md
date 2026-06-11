# Semantic Interview Search Engine

An AI-powered semantic search system for interview transcripts built using **Spring Boot**, **Gemini Embeddings**, **PostgreSQL**, and **pgvector**.

Instead of relying on traditional keyword matching, the system converts transcript chunks into vector embeddings and retrieves results based on semantic similarity.

---

# Features

- Upload interview transcripts
- Automatic transcript chunking
- Gemini Embedding API integration
- PostgreSQL + pgvector vector storage
- Semantic similarity search
- Keyword search
- Flyway database migrations
- Dockerized PostgreSQL
- Swagger/OpenAPI documentation

---

# Tech Stack

| Technology | Purpose |
|------------|---------|
| Java 21 | Programming Language |
| Spring Boot 3 | Backend Framework |
| Spring Data JPA | ORM |
| PostgreSQL 18 | Database |
| pgvector | Vector Database Extension |
| Flyway | Database Migration |
| Gemini Embedding API | Embedding Generation |
| Docker | Containerized Database |
| Swagger/OpenAPI | API Documentation |
| Maven | Dependency Management |

---

# System Architecture

```text
                           ┌─────────────────────┐
                           │     Swagger UI      │
                           │  OpenAPI Interface  │
                           └──────────┬──────────┘
                                      │
                                      ▼
                           ┌─────────────────────┐
                           │ SemanticController  │
                           │ Spring REST API     │
                           └──────────┬──────────┘
                                      │
                ┌─────────────────────┴─────────────────────┐
                ▼                                           ▼
     ┌─────────────────────┐                 ┌─────────────────────┐
     │   SemanticService   │                 │ TranscriptChunkSvc  │
     │ Transcript Handling │                 │ Chunk Management    │
     └──────────┬──────────┘                 └──────────┬──────────┘
                │                                       │
                ▼                                       ▼
     ┌─────────────────────┐                 ┌─────────────────────┐
     │ InterviewTranscript │                 │  TranscriptChunk    │
     │      Entity         │                 │       Entity        │
     └─────────────────────┘                 └──────────┬──────────┘
                                                        │
                                                        ▼
                                          ┌─────────────────────┐
                                          │  EmbeddingService   │
                                          │ Gemini Integration  │
                                          └──────────┬──────────┘
                                                     │
                                                     ▼
                                       ┌─────────────────────────┐
                                       │ Gemini Embedding API    │
                                       │ gemini-embedding-001    │
                                       └──────────┬──────────────┘
                                                  │
                                                  ▼
                                   ┌──────────────────────────────┐
                                   │ PostgreSQL + pgvector        │
                                   │ vector(3072) embeddings      │
                                   └──────────┬───────────────────┘
                                              │
                                              ▼
                                   ┌──────────────────────────────┐
                                   │ Semantic Similarity Search   │
                                   │ ORDER BY embedding <=> ?     │
                                   └──────────────────────────────┘
```

---

# Upload Flow

```text
User Uploads Transcript
          ↓
Save Transcript
          ↓
Split Into Chunks
          ↓
Generate Embeddings
          ↓
Store Vector Embeddings
```

---

# Semantic Search Flow

```text
User Query
     ↓
Generate Query Embedding
     ↓
PostgreSQL pgvector Similarity Search
     ↓
Return Most Relevant Chunks
```

---

# Database Schema

## semantic_transcript

```sql
CREATE TABLE semantic_transcript (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    filename VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    uploaded_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
```

## transcript_chunk

```sql
CREATE TABLE transcript_chunk (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,

    transcript_id BIGINT NOT NULL,

    chunk_text TEXT NOT NULL,

    embedding vector(3072),

    FOREIGN KEY (transcript_id)
        REFERENCES semantic_transcript(id)
);
```

---

# API Endpoints

## Upload Transcript

```http
POST /api/transcripts/upload
```

Uploads and processes a transcript file.

---

## Get All Transcripts

```http
GET /api/transcripts
```

Returns all uploaded transcripts.

---

## Get Transcript By Id

```http
GET /api/transcripts/{id}
```

Returns transcript details by id.

---

## Keyword Search

```http
GET /api/transcripts/search?query=pricing
```

Performs traditional text search.

---

## Semantic Search

```http
GET /api/transcripts/semantic-search?query=subscription issues
```

Uses vector similarity search to find semantically related chunks.

---

# Example

### Transcript

```text
Users complained about pricing.
Customers found the subscription expensive.
The onboarding process was confusing.
People loved the dashboard.
```

### Query

```text
pricing complaints
```

### Result

```text
Customers found the subscription expensive.
Users complained about pricing.
```

The system retrieves results based on semantic meaning instead of exact keyword matching.

---

# Running Locally

## Clone Repository

```bash
git clone <repository-url>
cd SemanticInterview
```

---

## Environment Variables

```properties
DB_USERNAME=postgres
DB_PASSWORD=your_password
GEMINI_API_KEY=your_api_key
```

---

## Start PostgreSQL + pgvector

```bash
docker run -d \
--name semantic_search \
-e POSTGRES_USER=postgres \
-e POSTGRES_PASSWORD=your_password \
-e POSTGRES_DB=semantic_search \
-p 5433:5432 \
pgvector/pgvector:pg18
```

---

## Run Flyway

```bash
mvn flyway:migrate
```

---

## Start Application

```bash
mvn spring-boot:run
```

---

# Swagger UI

```
http://localhost:8080/swagger-ui/index.html
```

---

# Future Improvements

- Hybrid Search (Keyword + Vector Search)
- Metadata Filtering
- Sentiment Analysis
- RAG-based Answer Generation
- Redis Cache
- Multi-file Search
- Evaluation Framework
- MCP Tool Integration
- AI Research Agent

---

# Project Goal

This project demonstrates how semantic search can be used to analyze large collections of interview transcripts and retrieve information based on meaning rather than exact keyword matches.

Inspired by modern AI-powered customer research platforms.
