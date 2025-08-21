package com.vdt.fileservice.repository;

import com.vdt.fileservice.entity.AppFile;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppFileRepository extends MongoRepository<AppFile,String> {
    Optional<AppFile> findByName(String fileName);
}
