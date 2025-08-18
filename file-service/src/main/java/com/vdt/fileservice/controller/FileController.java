package com.vdt.fileservice.controller;

import com.vdt.fileservice.dto.ApiResponse;
import com.vdt.fileservice.dto.FileResponse;
import com.vdt.fileservice.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class FileController {
    FileService fileService;

    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    ApiResponse<FileResponse> uploadFile(@ModelAttribute MultipartFile file) {
        return ApiResponse.<FileResponse>builder()
                .data(fileService.uploadFile(file))
                .build();
    }

    @GetMapping("/download/{fileName}")
    ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        Resource resource = fileService.downloadFile(fileName);
        return ResponseEntity.ok()
                .header("Content-Disposition",
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


    @DeleteMapping("/delete/{fileName}")
    ApiResponse<Void> deleteFile(@PathVariable String fileName) {
        fileService.deleteFile(fileName);
        return ApiResponse.<Void>builder()
                .message("File deleted successfully")
                .build();
    }
}
