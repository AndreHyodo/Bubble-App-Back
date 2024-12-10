package com.pucminas.bubble_app_back.entrypoint.fileStorage;

import com.pucminas.bubble_app_back.response.FileStorageResponse;
import com.pucminas.bubble_app_back.core.fileStorage.FileStorageService;
import com.pucminas.bubble_app_back.model.fileStorage.FileStorage;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/file")
public class FileStorageController {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE}, name = "/uploadFile")
    public FileStorageResponse uploadFile(@RequestParam("file")MultipartFile file, @RequestParam Integer idPacote) throws Exception{
        FileStorage attachment = null;
        String downloadUrl = "";
        attachment = fileStorageService.saveFile(file,idPacote);
        downloadUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/download/")
                .path(attachment.getId())
                .toUriString();
        return new FileStorageResponse(attachment.getFileName(),downloadUrl,file.getContentType(), file.getSize());
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> download(@PathVariable("fileId") String fileId) throws Exception{
        FileStorage fileStorage = null;
        fileStorage = fileStorageService.downloadFile(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileStorage.getFiletype()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "fileStorage; filename=\""+fileStorage.getFileName()
                +"\"").body(new ByteArrayResource(fileStorage.getData()));
    }

    @GetMapping("/getFilesByPackage")
    public ResponseEntity<List<FileStorage>> getPackage(@RequestParam Integer idPacote){
        return ResponseEntity.ok(fileStorageService.getFilesByPackage(idPacote));
    }
}
