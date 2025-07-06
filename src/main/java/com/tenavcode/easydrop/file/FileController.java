package com.tenavcode.easydrop.file;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/files")
public class FileController {

    private final FileMetaRepository repository;
    private final FileService fileService;

    public FileController(FileMetaRepository repository, FileService fileService) {
        this.repository = repository;
        this.fileService = fileService;
    }

    @PostMapping
    // @RequestBody will convert incoming JSON into a FileMeta entity
    public ResponseEntity<FileMetaResponse> saveFileMeta(@RequestBody FileCreationRequest fileCreationRequest) {
        return fileService.createFile(fileCreationRequest)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileMetaResponse> getFileById(@PathVariable UUID id) {
        return fileService.findFileById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FileMetaResponse> deleteFileById(@PathVariable UUID id) {
        boolean deleted = fileService.removeFileById(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<FileMeta> list() {
        return repository.findAll();
    }
}
