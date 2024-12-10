package com.pucminas.bubble_app_back.model.user;

import com.pucminas.bubble_app_back.common.enums.UsersRole;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String email;

    String password;

    String name;

    UsersRole role;

    Boolean active;

    Boolean firstAcess = true;

    String imageUrl;
}
