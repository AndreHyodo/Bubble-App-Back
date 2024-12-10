package com.pucminas.bubble_app_back.model.chat;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pucminas.bubble_app_back.model.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Data
@Setter
@Entity(name = "ChatRooms")
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_one_id")
    User userOne;

    @ManyToOne
    @JoinColumn(name = "user_two_id")
    User userTwo;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    List<ChatMessageModel> messages;
}