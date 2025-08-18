package com.vdt.fileservice.mapper;


import com.vdt.fileservice.dto.FileInfo;
import com.vdt.fileservice.entity.AppFile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FileMapper {
    @Mapping(target = "ownerId", ignore = true)
    @Mapping(target = "id", ignore = true)
    AppFile toFile(FileInfo fileInfo);
}
