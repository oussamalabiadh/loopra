package com.example.JuniorWebite.userControle;
import com.example.JuniorWebite.Entity.ChatMessageEntity;
import com.example.JuniorWebite.Entity.User;
import com.example.JuniorWebite.userRepository.UserRepo;
import com.example.JuniorWebite.userService.ChatService;
import com.example.JuniorWebite.userService.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserRepo userRepo;



        @Autowired
        private SimpMessagingTemplate messagingTemplate;

        @Autowired
        private ChatService messageService;

        @MessageMapping("/sendMessage")
        public void sendMessage(@Payload ChatMessageEntity message) {
            message.setTimestamp(LocalDateTime.now());
            messageService.saveMessage(message);

            // Envoi du message à un destinataire spécifique
            String destination = "/user/" + message.getRecipient().getUserID() + "/queue/messages";
            messagingTemplate.convertAndSend(destination, message);
        }
    @GetMapping("/messages/{idSender}/{idReception}")
    public List<ChatMessageEntity> getMessage(@PathVariable int idSender, @PathVariable int idReception){
            User sender = userRepo.findByUserID(idSender);
            User recept = userRepo.findByUserID(idReception);
        System.out.println("idSender: " + idSender + ", idReception: " + idReception);

           return  messageService.getMessages(sender,recept);
        }
    }



