package com.tenavcode.easydrop.file;

import com.tenavcode.easydrop.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        FileCreationRequest fakeRequest = new FileCreationRequest(
                fake.getFileName(),
                fake.getCreatedAt()
        );
        when(repo.save(any(FileMeta.class))).thenReturn(fake);

        // when
        Optional<FileMetaResponse> result = service.createFile(fakeRequest);

        // then
        assertTrue(result.isPresent());
        assertEquals(fake.getId(), result.get().id());
        assertEquals(fake.getFileName(), result.get().filename());
        assertEquals(fake.getCreatedAt(), result.get().createdAt());
    }

    @Test
    void findFileById() throws Exception {
        // given
        UUID id = UUID.fromString("11111111-1111-1111-1111-111111111111");
        FileMeta fake = new FileMeta();
        fake.setId(id);
        fake.setFileName("test");
        fake.setCreatedAt(LocalDateTime.now());
        FileCreationRequest fakeRequest = new FileCreationRequest(
                fake.getFileName(),
                fake.getCreatedAt()
        );
        when(repo.findById(id)).thenReturn(Optional.of(fake));

        // when
        Optional<FileMetaResponse> result = service.findFileById(id);

        // then
        assertTrue(result.isPresent());
        assertEquals(fake.getId(), result.get().id());
        assertEquals(fake.getFileName(), result.get().filename());
        assertEquals(fake.getCreatedAt(), result.get().createdAt());
    }

    @Test
    void findFileByIdNotFound() throws Exception {
        // given
        UUID NonExistingId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        when(repo.findById(NonExistingId)).thenReturn(Optional.empty());

        // when + then
        Exception ex = assertThrows(ResourceNotFoundException.class, () -> {
            service.findFileById(NonExistingId);
        });
        assertEquals("File not found", ex.getMessage());
    }

    @Test
    void removeFileById() {
        // given
        UUID id = UUID.fromString("11111111-1111-1111-1111-111111111111");
        when(repo.existsById(id)).thenReturn(true);
        doNothing().when(repo).deleteById(id);

        // when
        service.removeFileById(id);

        // then
        verify(repo).deleteById(id);
    }

    @Test
    void removeFileByIdNotFound() {
        // given
        UUID id = UUID.fromString("11111111-1111-1111-1111-111111111111");
        when(repo.existsById(id)).thenReturn(false);

        // when
        Exception ex = assertThrows(ResourceNotFoundException.class, () -> {
            service.removeFileById(id);
        });

        // then
        assertEquals("File not found", ex.getMessage());
        verify(repo, never()).deleteById(id);
    }
}