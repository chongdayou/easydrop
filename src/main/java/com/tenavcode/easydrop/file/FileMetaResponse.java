package com.tenavcode.easydrop.file;

import java.time.LocalDateTime;

public record FileMetaResponse(
        Long id,
        String name,
        LocalDateTime createdAt
) {
}
