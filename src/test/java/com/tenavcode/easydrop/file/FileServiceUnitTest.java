package com.tenavcode.easydrop.file;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FileServiceUnitTest {

    @Mock
    private FileMetaRepository repo;

    @InjectMocks
    private FileService service;

    @Test
    void createFile() throws Exception {
        // given
        UUID id = UUID.fromString("11111111-1111-1111-1111-111111111111");
        FileMeta fake = new FileMeta();
        fake.setId(id);
        fake.setFileName("test");
        fake.setCreatedAt(LocalDateTime.now());
        when(repo.findById(id)).thenReturn(java.util.Optional.of(fake));

        // when
        // Optional<FileMeta> file = service.createFile(fake);


    }

    @Test
    void findFileById() {
    }

    @Test
    void removeFileById() {
    }
}