package com.pucminas.bubble_app_back.entrypoint.user;

import com.pucminas.bubble_app_back.common.enums.UsersRole;
import com.pucminas.bubble_app_back.model.user.User;
import com.pucminas.bubble_app_back.usecase.user.ChangeUserPasswordUseCase;
import com.pucminas.bubble_app_back.usecase.user.SaveNewUserUseCase;
import com.pucminas.bubble_app_back.usecase.user.SaveStudentsByExcelUseCase;
import com.pucminas.bubble_app_back.usecase.user.UserLoginUseCase;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/User")
public class UserController {
    @Autowired
    SaveStudentsByExcelUseCase saveStudentsByExcelUseCase;

    @Autowired
    SaveNewUserUseCase saveNewUserUseCase;

    @Autowired
    ChangeUserPasswordUseCase changeUserPasswordUseCase;

    @Autowired
    UserLoginUseCase userLoginUseCase;

    @ApiOperation(value = "Carregar um arquivo Excel contendo estudantes")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE}, name = "/excelUsuarios")
    public ResponseEntity<Void> salvarUsuariosExcel(@RequestParam("file") MultipartFile file) throws IOException {
        saveStudentsByExcelUseCase.salvarEstudantesExcel(file);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/newUser")
    public ResponseEntity<User> salvarUsuario(@RequestBody User user) {
        return ResponseEntity.ok(saveNewUserUseCase.save(user));
    }

    @PostMapping("/userNewPassword")
    public ResponseEntity<User> editarSenha(@RequestParam String email, @RequestParam String newPassword) {
        return ResponseEntity.ok(changeUserPasswordUseCase.changeUserPassword(email, newPassword));
    }

    @GetMapping("/login")
    public ResponseEntity<User> login(@RequestParam String email, @RequestParam String password) {
        return ResponseEntity.ok(userLoginUseCase.login(email, password));
    }

    @GetMapping("/getUsersByRole")
    public ResponseEntity<List<User>> login(@RequestParam UsersRole role) {
        return ResponseEntity.ok(userLoginUseCase.getUsersByRole(role));
    }

    @PutMapping("/updateUser")
    public ResponseEntity<User> updateUser(@RequestParam Long Id, @RequestBody User user) {
        return ResponseEntity.ok(changeUserPasswordUseCase.updateUser(Id, user));
    }
}
