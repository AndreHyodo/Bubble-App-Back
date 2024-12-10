package com.pucminas.bubble_app_back.core.chat;

import com.pucminas.bubble_app_back.dataprovider.chat.IChatRoomRepository;
import com.pucminas.bubble_app_back.dataprovider.chat.IMessageRepository;
import com.pucminas.bubble_app_back.dataprovider.user.IUserRepository;
import com.pucminas.bubble_app_back.model.chat.ChatMessageModel;
import com.pucminas.bubble_app_back.model.chat.ChatRoom;
import com.pucminas.bubble_app_back.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IMessageRepository messageRepository;

    @Autowired
    IChatRoomRepository chatRoomRepository;

    public ChatRoom findOrCreateChatRoom(User sender, User receiver) {
        return findChatRoom(sender, receiver).orElseGet(() -> createChatRoom(sender, receiver));
    }

    public Optional<ChatRoom> findChatRoom(User sender, User receiver) {
        User userOne = userRepository.findByEmail(sender.getEmail());
        User userTwo = userRepository.findByEmail(receiver.getEmail());

        return chatRoomRepository.findAll().stream()
                .filter(chatRoom ->
                        (chatRoom.getUserOne().equals(userOne) && chatRoom.getUserTwo().equals(userTwo)) ||
                                (chatRoom.getUserOne().equals(userTwo) && chatRoom.getUserTwo().equals(userOne)))
                .findFirst();
    }

    public ChatRoom createChatRoom(User sender, User receiver) {
        User userOne = userRepository.findByEmail(sender.getEmail());
        User userTwo = userRepository.findByEmail(receiver.getEmail());

        ChatRoom newChatRoom = new ChatRoom();
        newChatRoom.setUserOne(userOne);
        newChatRoom.setUserTwo(userTwo);

        return chatRoomRepository.save(newChatRoom);
    }

    public ChatMessageModel sendMessage(User sender, User receiver, ChatRoom chatRoom, String content) {
        ChatMessageModel message = new ChatMessageModel();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setChatRoom(chatRoom);
        message.setContent(content);
        message.setSendTime(LocalDateTime.now());

        return messageRepository.save(message);
    }

    public List<ChatRoom> getAllChatsForUser(User user) {
        return chatRoomRepository.findAllByUser(user);
    }
}
