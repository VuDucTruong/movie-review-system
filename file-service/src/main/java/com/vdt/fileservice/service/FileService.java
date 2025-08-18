package com.vdt.fileservice.service;

import com.vdt.fileservice.dto.FileResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    FileResponse uploadFile(MultipartFile file);

    void deleteFile(String fileName);


    Resource downloadFile(String fileName);
}
