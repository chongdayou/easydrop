package com.tenavcode.easydrop.file;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "file_meta")
public class FileMeta {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(
            name = "file_name",
            nullable = false
    )
    private String fileName;

    @Column(
            name = "created_at",
            nullable = false
    )
    private LocalDateTime createdAt;

    @PrePersist
    public void ensureId() {
        if (id == null) id = UUID.randomUUID();
    }

    protected FileMeta() {
        // For JPA only
    }

    public FileMeta(String fileName, LocalDateTime createdAt) {
        this.createdAt = createdAt;
        this.fileName = fileName;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
