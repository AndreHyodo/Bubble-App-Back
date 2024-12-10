package com.pucminas.bubble_app_back.model.chat;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pucminas.bubble_app_back.model.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Data
@Setter
@Entity(name = "Messages")
public class ChatMessageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String content;

    @ManyToOne
    @JoinColumn(name = "sender_email")
    User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_email")
    User receiver;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "chat_room_id")
    ChatRoom chatRoom;

    LocalDateTime sendTime;
}
