package com.vdt.fileservice.dto;

public record FileResponse(
        String originalFileName,
        String url
) {
}
