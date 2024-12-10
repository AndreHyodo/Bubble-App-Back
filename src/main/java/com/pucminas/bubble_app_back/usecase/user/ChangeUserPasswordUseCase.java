package com.pucminas.bubble_app_back.usecase.user;

import com.pucminas.bubble_app_back.core.user.UserService;
import com.pucminas.bubble_app_back.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChangeUserPasswordUseCase {

    @Autowired
    UserService userService;

    public User changeUserPassword(String email, String password) {
        return userService.editPassword(email, password);
    }

    public User updateUser(Long id, User updatedUser) {
        return userService.updateUser(id, updatedUser);
    }
}
