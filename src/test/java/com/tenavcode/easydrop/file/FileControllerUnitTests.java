package com.tenavcode.easydrop.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenavcode.easydrop.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Replace;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FileController.class)
public class FileControllerUnitTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FileService fileService;
    @MockBean
    private FileMetaRepository repo;
    @Autowired
    private ObjectMapper mapper;



    @Test
    void testFindFileById_ShouldGet() throws Exception {
        UUID id = UUID.fromString("11111111-1111-1111-1111-111111111111");
        LocalDateTime createdAt = LocalDateTime.now();
        FileMetaResponse testFile = new FileMetaResponse(id, "test.txt", createdAt);
        when(fileService.findFileById(id)).thenReturn(Optional.of(testFile));

        // .accept(MediaType.APPLICATION_JSON) not needed
        // because JSON is returned by default
        // format for accessing a json value:
        //      $ = root; $.field.nested_field...
        mvc.perform(get("/api/v1/files/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.filename").value("test.txt"));
                //.andExpect(jsonPath("$.createdAt").value(createdAt.toString()));
    }

    @Test
    void testFindFileById_ShouldNotGet() throws Exception {
        LocalDateTime createdAt = LocalDateTime.now();
        UUID id = UUID.fromString("11111111-1111-1111-1111-111111111111");
        FileMetaResponse testFile = new FileMetaResponse(id, "test.txt", createdAt);
        when(fileService.findFileById(id)).thenReturn(Optional.of(testFile));

        UUID wrongId = UUID.fromString("22222222-2222-2222-2222-222222222222");
        mvc.perform(get("/api/v1/files/" + wrongId))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateFile() throws Exception {
        LocalDateTime createdAt = LocalDateTime.now();
        UUID id = UUID.fromString("11111111-1111-1111-1111-111111111111");
        FileCreationRequest testFile = new FileCreationRequest("file.txt", createdAt);
        when(fileService.createFile(testFile))
                .thenReturn(Optional.of(new FileMetaResponse(id, "file.txt", createdAt)));

        String jsonRequest = mapper.writeValueAsString(testFile);
        mvc.perform(put("/api/v1/files")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated());
    }

    @Test
    void testDeleteFile() throws Exception {
        LocalDateTime createdAt = LocalDateTime.now();
        UUID id = UUID.fromString("11111111-1111-1111-1111-111111111111");
        FileMetaResponse testFile = new FileMetaResponse(id, "file.txt", createdAt);
        when(fileService.removeFileById(id))
                .thenReturn(true);

        mvc.perform(delete("/api/v1/files/" + id))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteFile_NonExistFile() throws Exception {
        LocalDateTime createdAt = LocalDateTime.now();
        UUID id = UUID.fromString("11111111-1111-1111-1111-111111111111");
        FileMetaResponse testFile = new FileMetaResponse(id, "file.txt", createdAt);

        UUID wrongId = UUID.fromString("22222222-2222-2222-2222-222222222222");
        when(fileService.removeFileById(wrongId))
                .thenThrow(new ResourceNotFoundException("File not found"));
        mvc.perform(delete("/api/v1/files/" + wrongId))
                .andExpect(status().isNotFound());
    }
}
