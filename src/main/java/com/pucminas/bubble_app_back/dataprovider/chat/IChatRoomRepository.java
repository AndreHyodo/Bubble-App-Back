package com.pucminas.bubble_app_back.dataprovider.chat;

import com.pucminas.bubble_app_back.model.chat.ChatRoom;
import com.pucminas.bubble_app_back.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query("SELECT c FROM ChatRooms c WHERE c.userOne = :user OR c.userTwo = :user")
    List<ChatRoom> findAllByUser(User user);

}
