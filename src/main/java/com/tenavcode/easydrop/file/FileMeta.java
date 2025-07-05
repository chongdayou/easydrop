package com.tenavcode.easydrop.file;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "file_meta")
public class FileMeta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    protected FileMeta() {
        // For JPA only
    }

    public FileMeta(String fileName, LocalDateTime createdAt) {
        this.createdAt = createdAt;
        this.fileName = fileName;
    }

    public Long getId() {
        return id;
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
