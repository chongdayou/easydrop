package com.tenavcode.easydrop.file;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FileController.class)
@Import(FileControllerUnitTests.TestConfig.class)
public class FileControllerUnitTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private FileService fileService;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public FileService fileService() {
            return Mockito.mock(FileService.class);
        }
    }

    @Test
    public void testFindFileById_ShouldGet() throws Exception {
        Long id = 1L;
        LocalDateTime createdAt = LocalDateTime.now();
        FileMetaResponse testFile = new FileMetaResponse(id, "test.txt", createdAt);
        when(fileService.findFileById(1L)).thenReturn(Optional.of(testFile));

        // .accept(MediaType.APPLICATION_JSON) not needed
        // because JSON is returned by default
        // format for accessing a json value:
        //      $ = root; $.field.nested_field...
        mvc.perform(get("api/v1/files/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.filename").value("test.txt"))
                .andExpect(jsonPath("$.createdAt").value(createdAt));
    }
}
