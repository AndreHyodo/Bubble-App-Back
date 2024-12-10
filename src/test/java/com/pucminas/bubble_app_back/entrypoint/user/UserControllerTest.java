package com.pucminas.bubble_app_back.entrypoint.user;

import com.pucminas.bubble_app_back.common.enums.UsersRole;
import com.pucminas.bubble_app_back.model.user.User;
import com.pucminas.bubble_app_back.usecase.user.ChangeUserPasswordUseCase;
import com.pucminas.bubble_app_back.usecase.user.SaveNewUserUseCase;
import com.pucminas.bubble_app_back.usecase.user.SaveStudentsByExcelUseCase;
import com.pucminas.bubble_app_back.usecase.user.UserLoginUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SaveStudentsByExcelUseCase saveStudentsByExcelUseCase;

    @MockBean
    private SaveNewUserUseCase saveNewUserUseCase;

    @MockBean
    private ChangeUserPasswordUseCase changeUserPasswordUseCase;

    @MockBean
    private UserLoginUseCase userLoginUseCase;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("user@example.com");
        user.setPassword("password");
        user.setName("User Name");
        user.setRole(UsersRole.STUDENT);
        user.setActive(true);
        user.setFirstAcess(true);
        user.setImageUrl("http://example.com/image.jpg");
    }

    @Test
    public void testSalvarUsuario() throws Exception {
        given(saveNewUserUseCase.save(any(User.class))).willReturn(user);

        mockMvc.perform(post("/api/User/newUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"email\": \"user@example.com\", \"password\": \"password\", \"name\": \"User Name\", \"role\": \"STUDENT\", \"active\": true, \"firstAcess\": true, \"imageUrl\": \"http://example.com/image.jpg\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.name").value(user.getName()));
    }

    @Test
    public void testEditarSenha() throws Exception {
        given(changeUserPasswordUseCase.changeUserPassword(anyString(), anyString())).willReturn(user);

        mockMvc.perform(post("/api/User/userNewPassword")
                        .param("email", "user@example.com")
                        .param("newPassword", "newpassword"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }

    @Test
    public void testLogin() throws Exception {
        given(userLoginUseCase.login(anyString(), anyString())).willReturn(user);

        mockMvc.perform(get("/api/User/login")
                        .param("email", "user@example.com")
                        .param("password", "password"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.name").value(user.getName()));
    }

    @Test
    public void testGetUsersByRole() throws Exception {
        List<User> users = Arrays.asList(user);
        given(userLoginUseCase.getUsersByRole(any(UsersRole.class))).willReturn(users);

        mockMvc.perform(get("/api/User/getUsersByRole")
                        .param("role", "STUDENT"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(user.getId()))
                .andExpect(jsonPath("$[0].email").value(user.getEmail()))
                .andExpect(jsonPath("$[0].name").value(user.getName()));
    }

    @Test
    public void testUpdateUser() throws Exception {
        given(changeUserPasswordUseCase.updateUser(anyLong(), any(User.class))).willReturn(user);

        mockMvc.perform(put("/api/User/updateUser")
                        .param("Id", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"email\": \"user@example.com\", \"password\": \"password\", \"name\": \"User Name\", \"role\": \"STUDENT\", \"active\": true, \"firstAcess\": true, \"imageUrl\": \"http://example.com/image.jpg\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.name").value(user.getName()));
    }
}
