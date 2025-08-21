package com.vdt.movieservice.repository.client;


import com.vdt.movieservice.config.AuthenticationInterceptor;
import com.vdt.movieservice.dto.ApiResponse;
import com.vdt.movieservice.dto.FileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "file-service", url = "${app.services.file-service.url}", configuration = {
    AuthenticationInterceptor.class})
public interface FileClient {
  @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  ApiResponse<FileResponse> uploadFile(@RequestPart MultipartFile file);
}
