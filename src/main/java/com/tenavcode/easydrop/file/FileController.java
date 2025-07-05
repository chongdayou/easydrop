package com.tenavcode.easydrop.file;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api.files")
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
        FileMeta saved = fileService.createFile(fileCreationRequest);
        FileMetaResponse response = new FileMetaResponse(
                saved.getId(),
                saved.getFileName(),
                saved.getCreatedAt()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public List<FileMeta> list() {
        return repository.findAll();
    }
}
