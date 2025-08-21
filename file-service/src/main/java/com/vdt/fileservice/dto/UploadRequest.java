package com.vdt.fileservice.dto;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record UploadRequest(
    @NotNull(message = "File must not be null")
    MultipartFile file
) {
}
