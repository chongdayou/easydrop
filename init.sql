CREATE TABLE IF NOT EXISTS file_meta (
    id BIGSERIAL PRIMARY KEY,
    file_name TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL
);