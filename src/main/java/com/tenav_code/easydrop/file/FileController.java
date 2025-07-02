package com.tenav_code.easydrop.file;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api.files")
public class FileController {

    private final FileMetaRepository repository;

    public FileController(FileMetaRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    // @RequestBody will convert incoming JSON into a FileMeta entity
    public FileMeta saveFileMeta(@RequestBody FileMeta meta) {
        meta.setCreatedAt(LocalDateTime.now());
        return repository.save(meta);
    }

    @GetMapping
    public List<FileMeta> list() {
        return repository.findAll();
    }
}
