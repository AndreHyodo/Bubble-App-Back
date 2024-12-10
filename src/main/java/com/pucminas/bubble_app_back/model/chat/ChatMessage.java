package com.pucminas.bubble_app_back.model.chat;

import com.pucminas.bubble_app_back.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage implements Serializable {
    String content;
    User sender;
    User receiver;
}
