package com.tenavcode.easydrop.file;

import com.tenavcode.easydrop.Exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FileService {

    private final FileMetaRepository repo;

    public FileService(FileMetaRepository repo) {
        this.repo = repo;
    }

    public FileMeta createFile(FileCreationRequest request) {
        FileMeta saved = repo.save(new FileMeta(
                request.name(),
                request.creationTime() == null ? LocalDateTime.now() : request.creationTime()
        ));
        return saved;
    }

    public FileMeta findFileById(Long id) {
        FileMeta file = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("File not found with id " + id));
        return file;
    }

    public void deleteFileById(Long id) {
        if (!repo.existsById(id)) throw new ResourceNotFoundException("File not found with id " + id);
        repo.deleteById(id);
    }
}
