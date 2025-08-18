package com.vdt.fileservice.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "files")
@Data
@Builder
public class AppFile {
    @MongoId
    String id;
    String name;
    Long ownerId;
    String contentType;
    long size;
    String md5CheckSum;
    String cloudinaryUrl;
}
