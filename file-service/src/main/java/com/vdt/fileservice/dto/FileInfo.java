package com.vdt.fileservice.dto;

import lombok.Builder;

@Builder
public record FileInfo(
        String name,
        String contentType,
        long size,
        String md5CheckSum,
        String cloudinaryUrl,
        String url
) {
}
