package com.tenavcode.easydrop.file;

import java.time.LocalDateTime;
import java.util.UUID;

public record FileMetaResponse(
        UUID id,
        String name,
        LocalDateTime createdAt
) {
}
