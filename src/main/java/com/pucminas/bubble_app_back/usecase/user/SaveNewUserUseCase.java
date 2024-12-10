package com.pucminas.bubble_app_back.usecase.user;

import com.pucminas.bubble_app_back.core.user.UserService;
import com.pucminas.bubble_app_back.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SaveNewUserUseCase {
    @Autowired
    UserService userService;


    public User save(User user) {
        return  userService.saveUser(user);
    }
}
