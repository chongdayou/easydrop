package com.tenavcode.easydrop.file;

import jakarta.annotation.Nonnull;

import java.time.LocalDateTime;

public record FileCreationRequest(
        @Nonnull String name,
        LocalDateTime creationTime
) {
}
