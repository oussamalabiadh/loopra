package com.example.JuniorWebite.userService;
import com.example.JuniorWebite.Entity.ChatMessageEntity;
import com.example.JuniorWebite.Entity.User;
import com.example.JuniorWebite.userRepository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    // Méthode pour enregistrer un message
    public ChatMessageEntity saveMessage(ChatMessageEntity chatMessage) {
        return chatMessageRepository.save(chatMessage);
    }
    public List<ChatMessageEntity> getMessages(User sender , User recipient){
        return chatMessageRepository.findChatMessageEntitiesBySenderAndRecipient(sender, recipient);
    }
}
