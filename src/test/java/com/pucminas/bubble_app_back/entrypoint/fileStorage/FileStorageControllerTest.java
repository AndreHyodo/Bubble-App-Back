package com.pucminas.bubble_app_back.entrypoint.fileStorage;

import com.pucminas.bubble_app_back.core.fileStorage.FileStorageService;
import com.pucminas.bubble_app_back.model.fileStorage.FileStorage;
import com.pucminas.bubble_app_back.response.FileStorageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FileStorageController.class)
public class FileStorageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileStorageService fileStorageService;

    private FileStorage fileStorage;
    private String fileId;

    @BeforeEach
    public void setUp() {
        fileId = UUID.randomUUID().toString();
        fileStorage = new FileStorage();
        fileStorage.setId(fileId);
        fileStorage.setFileName("testfile.txt");
        fileStorage.setFiletype("text/plain");
        fileStorage.setData("Sample file content".getBytes(StandardCharsets.UTF_8));
    }

//    @Test
//    public void testUploadFile() throws Exception {
//        MockMultipartFile mockFile = new MockMultipartFile("file", "testfile.txt", "text/plain;charset=UTF-8", "Sample file content".getBytes(StandardCharsets.UTF_8));
//
//        given(fileStorageService.saveFile(any(MultipartFile.class))).willReturn(fileStorage);
//
//        mockMvc.perform(multipart("/file/uploadFile").file(mockFile))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.fileName").value("testfile.txt"))
//                .andExpect(jsonPath("$.fileType").value("text/plain;charset=UTF-8"));
//    }

    @Test
    public void testDownloadFile() throws Exception {
        given(fileStorageService.downloadFile(fileId)).willReturn(fileStorage);

        mockMvc.perform(get("/file/download/{fileId}", fileId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.parseMediaType("text/plain;charset=UTF-8")))
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "fileStorage; filename=\"testfile.txt\""))
                .andExpect(content().bytes("Sample file content".getBytes(StandardCharsets.UTF_8)));
    }
}
