package com.pucminas.bubble_app_back.dataprovider.fileStorage;

import com.pucminas.bubble_app_back.model.fileStorage.FileStorage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.nio.file.Files;
import java.util.List;

public interface FileStorageRepository extends JpaRepository<FileStorage, String> {

    List<FileStorage> findByMaterialPackage_PackageId(Integer packageId);
}
