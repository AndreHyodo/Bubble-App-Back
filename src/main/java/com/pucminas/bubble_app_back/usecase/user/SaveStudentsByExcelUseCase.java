package com.pucminas.bubble_app_back.usecase.user;

import com.pucminas.bubble_app_back.core.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class SaveStudentsByExcelUseCase {
    @Autowired
    UserService userService;

    public void salvarEstudantesExcel(MultipartFile file) throws IOException {
        userService.salvarUsuariosDoExcel(file);
    }
}
