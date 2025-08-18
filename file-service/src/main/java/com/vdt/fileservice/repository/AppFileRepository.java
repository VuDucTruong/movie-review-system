package com.vdt.fileservice.repository;

import com.vdt.fileservice.entity.AppFile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppFileRepository extends MongoRepository<AppFile,String> {
    AppFile findByName(String fileName);
}
