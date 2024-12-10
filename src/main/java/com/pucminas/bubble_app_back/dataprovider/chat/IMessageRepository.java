package com.pucminas.bubble_app_back.dataprovider.chat;

import com.pucminas.bubble_app_back.model.chat.ChatMessageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMessageRepository extends JpaRepository<ChatMessageModel, Long> {

}
