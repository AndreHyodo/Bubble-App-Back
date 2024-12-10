package com.pucminas.bubble_app_back.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileStorageResponse {

    private String fileName;
    private String donwloadURL;
    private String fileType;
    private long fileSize;

}
