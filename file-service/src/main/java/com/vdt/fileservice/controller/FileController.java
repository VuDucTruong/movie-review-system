package com.vdt.fileservice.controller;

import com.vdt.fileservice.dto.ApiResponse;
import com.vdt.fileservice.dto.FileResponse;
import com.vdt.fileservice.dto.UploadRequest;
import com.vdt.fileservice.service.FileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class FileController {

  FileService fileService;

  /*
  * This is the best practice for uploading files in Spring Boot ( can be used with Feign client )
  * */
  @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
  ApiResponse<FileResponse> uploadFile(@RequestPart MultipartFile file) {
    return ApiResponse.<FileResponse>builder()
        .data(fileService.uploadFile(file))
        .build();
  }

  @GetMapping("/download/{fileName}")
  ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
    Resource resource = fileService.downloadFile(fileName);
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_OCTET_STREAM) // Set the content type to application/octet-stream for file download
        .header(HttpHeaders.CONTENT_DISPOSITION,
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
