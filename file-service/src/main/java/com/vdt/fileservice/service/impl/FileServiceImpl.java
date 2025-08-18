package com.vdt.fileservice.service.impl;

import com.vdt.fileservice.dto.FileInfo;
import com.vdt.fileservice.dto.FileResponse;
import com.vdt.fileservice.entity.AppFile;
import com.vdt.fileservice.exception.AppException;
import com.vdt.fileservice.exception.ErrorCode;
import com.vdt.fileservice.mapper.FileMapper;
import com.vdt.fileservice.repository.AppFileRepository;
import com.vdt.fileservice.repository.FileStorageRepository;
import com.vdt.fileservice.service.FileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    AppFileRepository appFileRepository;
    FileStorageRepository fileStorageRepository;
    FileMapper fileMapper;

    @Override
    public FileResponse uploadFile(MultipartFile file) {
        try {
            FileInfo fileInfo = fileStorageRepository.storeFile(file);
            String userId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

            AppFile appFileEntity = fileMapper.toFile(fileInfo);
            appFileEntity.setOwnerId(Long.parseLong(userId));
            return new FileResponse(file.getOriginalFilename(), fileInfo.url());
        } catch (IOException e) {
            throw new AppException(ErrorCode.UPLOAD_FILE_ERROR);
        }
    }

    @Override
    public void deleteFile(String fileName) {

        AppFile appFile = appFileRepository.findById(fileName)
                .orElseThrow(() -> new AppException(ErrorCode.FILE_NOT_FOUND));

        String userId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

        if (!appFile.getOwnerId().toString().equals(userId)) {
            throw new AppException(ErrorCode.UNAUTHORIZED_ACCESS);
        }

        boolean isSuccess = fileStorageRepository.deleteFile(fileName);
        if(!isSuccess) {
            throw new AppException(ErrorCode.DELETE_FILE_ERROR);
        }

        appFileRepository.delete(appFile);

    }

    @Override
    public Resource downloadFile(String fileName) {
        try {
            AppFile file = appFileRepository.findById(fileName).orElseThrow(() -> new AppException(ErrorCode.FILE_NOT_FOUND));
            URI uri = URI.create(file.getCloudinaryUrl());
            return new UrlResource(uri);
        } catch (Exception e) {
            throw new AppException(ErrorCode.DOWNLOAD_FILE_ERROR);
        }

    }
}
