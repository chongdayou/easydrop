package com.tenavcode.easydrop.file;

import com.tenavcode.easydrop.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

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

    public Optional<FileMetaResponse> findFileById(Long id) {
        return repo.findById(id)
                .map(file -> new FileMetaResponse(
                        file.getId(),
                        file.getFileName(),
                        file.getCreatedAt()
                ));
    }

    public boolean removeFileById(Long id) {
        if (!repo.existsById(id)) {
            return false;
        }
        repo.deleteById(id);
        return true;
    }

    public void deleteFileById(Long id) {
        if (!repo.existsById(id)) throw new ResourceNotFoundException("File not found with id " + id);
        repo.deleteById(id);
    }
}
