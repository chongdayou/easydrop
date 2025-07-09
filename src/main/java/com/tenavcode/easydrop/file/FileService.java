package com.tenavcode.easydrop.file;

import com.tenavcode.easydrop.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileService {

    private final FileMetaRepository repo;

    public FileService(FileMetaRepository repo) {
        this.repo = repo;
    }

    public Optional<FileMetaResponse> createFile(FileCreationRequest request) {
        FileMeta saved = repo.save(new FileMeta(
                request.name(),
                request.creationTime() == null ? LocalDateTime.now() : request.creationTime()
        ));
        return Optional.of(new FileMetaResponse(
                saved.getId(),
                saved.getFileName(),
                saved.getCreatedAt()
        ));
    }

    public Optional<FileMetaResponse> findFileById(UUID id) {
        Optional<FileMeta> saved = repo.findById(id);
        if (saved.isEmpty()) throw new ResourceNotFoundException("File not found");
        return saved
                .map(file -> new FileMetaResponse(
                        file.getId(),
                        file.getFileName(),
                        file.getCreatedAt()
                ));
    }

    public boolean removeFileById(UUID id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("File not found");
        }
        repo.deleteById(id);
        return true;
    }
}
