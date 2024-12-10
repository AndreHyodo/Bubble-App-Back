package com.pucminas.bubble_app_back.dataprovider.user;

import com.pucminas.bubble_app_back.common.enums.UsersRole;
import com.pucminas.bubble_app_back.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    @Query("SELECT u FROM Users u WHERE u.email IN :emails")
    List<User> findAllByEmail(@Param("emails") List<String> emails);

    List<User> findByRole(UsersRole role);

    Boolean existsByEmail(String email);
}
