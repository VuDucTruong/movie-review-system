package com.vdt.fileservice.repository;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.vdt.fileservice.dto.FileInfo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

@Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class FileStorageRepository {

    @NonFinal
    @Value("${download.url-prefix}")
    String urlPrefix;

    Cloudinary cloudinary;

    public FileInfo storeFile(MultipartFile file) throws IOException {
        String fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String fileName = UUID.randomUUID().toString() + "." + fileExtension;
        var result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("public_id", fileName));

        return FileInfo.builder()
                .name(fileName)
                .url(urlPrefix + fileName)
                .contentType(file.getContentType())
                .size(file.getSize())
                .cloudinaryUrl(result.get("secure_url").toString())
                .md5CheckSum(
                        DigestUtils.md5DigestAsHex(file.getInputStream()))
                .build();
    }

    public boolean deleteFile(String fileName) {
        try {
            var result = cloudinary.uploader().destroy(fileName, ObjectUtils.emptyMap());
            return "ok".equals(result.get("result"));
        } catch (Exception e) {
            return false;
        }
    }
}
