package com.pucminas.bubble_app_back.usecase.user;

import com.pucminas.bubble_app_back.common.enums.UsersRole;
import com.pucminas.bubble_app_back.core.user.UserService;
import com.pucminas.bubble_app_back.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserLoginUseCase {
    @Autowired
    UserService userService;

    public User login(String email, String password) {
        return userService.login(email, password);
    }

    public List<User> getUsersByRole(UsersRole role){
        return userService.getUsersByRole(role);
    }
}
