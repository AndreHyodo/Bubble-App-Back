package com.pucminas.bubble_app_back.core.fileStorage;

import com.pucminas.bubble_app_back.dataprovider.fileStorage.FileStorageRepository;
import com.pucminas.bubble_app_back.dataprovider.lessonpackage.IPackageRepository;
import com.pucminas.bubble_app_back.exception.FileStorageException;
import com.pucminas.bubble_app_back.model.fileStorage.FileStorage;
import com.pucminas.bubble_app_back.model.lessonpackage.Package;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class FileStorageService {

    @Autowired
    private FileStorageRepository fileStorageRepository;

    @Autowired
    IPackageRepository packageRepository;

    public FileStorage saveFile(MultipartFile file,Integer idPackage){
        Package materialPackage = packageRepository.findById(idPackage).orElse(null);

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if(fileName.contains("..")){
                throw new FileStorageException("File name contains invalid path sequence "+ fileName);
            }

            FileStorage fileStorage = new FileStorage(fileName, file.getContentType(), file.getBytes());
            fileStorage.setMaterialPackage(materialPackage);
            return fileStorageRepository.save(fileStorage);
        }catch (Exception e){
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!",e);
        }
    }

    public FileStorage downloadFile (String fileId) throws Exception{
        return fileStorageRepository.findById(fileId).
                orElseThrow(()-> new Exception("A file with Id: "+ fileId + " could not be found!"));
    }

    public List<FileStorage> getFilesByPackage(Integer idPackage){
        return fileStorageRepository.findByMaterialPackage_PackageId(idPackage);
    }
}
