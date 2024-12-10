package com.pucminas.bubble_app_back.entrypoint.chat;

import com.pucminas.bubble_app_back.core.chat.ChatService;
import com.pucminas.bubble_app_back.dataprovider.chat.IMessageRepository;
import com.pucminas.bubble_app_back.dataprovider.user.IUserRepository;
import com.pucminas.bubble_app_back.model.chat.ChatMessage;
import com.pucminas.bubble_app_back.model.chat.ChatMessageModel;
import com.pucminas.bubble_app_back.model.chat.ChatRoom;
import com.pucminas.bubble_app_back.model.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/Chat")
public class ChatController {

    @Autowired
    IMessageRepository messageRepository;

    @Autowired
    ChatService chatService;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @MessageMapping("/chat.private.{receiverEmail}")
    public void sendPrivateMessage(@Payload ChatMessage chatMessage,
                                   @DestinationVariable String receiverEmail) {
        log.info("Enviando mensagem para: {}", receiverEmail);

        Optional<ChatRoom> chatRoomOptional = chatService.findChatRoom(chatMessage.getSender(), chatMessage.getReceiver());
        ChatRoom chatRoom = chatRoomOptional.orElseGet(() -> chatService.createChatRoom(chatMessage.getSender(), chatMessage.getReceiver()));

        ChatMessageModel savedMessage = chatService.sendMessage(
                chatMessage.getSender(),
                chatMessage.getReceiver(),
                chatRoom,
                chatMessage.getContent()
        );

        messagingTemplate.convertAndSend("/topic/private." + receiverEmail, savedMessage);
    }

    @GetMapping("/getUserChats")
    public List<ChatRoom> getUserChats(@RequestParam String userEmail) {
        log.info("Buscando salas de chat para o usuário: {}", userEmail);

        User userOne = userRepository.findByEmail(userEmail);

        return chatService.getAllChatsForUser(userOne);
    }

    @PostMapping("/createRoom")
    public ChatRoom createChatRoom(@RequestParam String senderEmail, @RequestParam String receiverEmail) {
        log.info("Criando sala de chat entre {} e {}", senderEmail, receiverEmail);

        User sender = userRepository.findByEmail(senderEmail);
        User receiver = userRepository.findByEmail(receiverEmail);

        ChatRoom chatRoom = chatService.createChatRoom(sender, receiver);

        log.info("ChatRoom criado com ID: {}", chatRoom.getId());
        return chatRoom;
    }

    @PostMapping("/sendMessage")
    public ChatMessageModel sendMessage(@RequestBody ChatMessage chatMessage) {
        log.info("Enviando mensagem de {} para {}", chatMessage.getSender().getEmail(), chatMessage.getReceiver().getEmail());

        ChatRoom chatRoom = chatService.findOrCreateChatRoom(chatMessage.getSender(), chatMessage.getReceiver());

        ChatMessageModel savedMessage = chatService.sendMessage(
                chatMessage.getSender(),
                chatMessage.getReceiver(),
                chatRoom,
                chatMessage.getContent()
        );

        String receiverEmail = chatMessage.getReceiver().getEmail();
        messagingTemplate.convertAndSend("/topic/private." + receiverEmail, savedMessage);

        log.info("Mensagem enviada com sucesso!");
        return savedMessage;
    }

}
