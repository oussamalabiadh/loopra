package com.example.JuniorWebite.userService;

import com.example.JuniorWebite.Entity.Notification;
import com.example.JuniorWebite.Entity.Relationship;
import com.example.JuniorWebite.userRepository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private RelationshipService relationshipService; // Service pour gérer les relations entre utilisateurs

    public void createNotificationForFriends(String message, int sender, Notification.NotificationType type) {
        // Récupérer tous les amis de l'utilisateur
        List<Relationship> friends = relationshipService.getRelationshipsByUserId(sender);
        int maxMessageLength = 255; // Remplacez par la taille maximale de la colonne 'message'

        if (message.length() > maxMessageLength) {
            message = message.substring(0, maxMessageLength); // Tronquer le message
        }
        for (Relationship friend : friends) {
            createNotification(message, friend.getFriend().getUserName(), type);
        }
    }

    public void createNotification(String message, String recipient, Notification.NotificationType type) {
        int maxMessageLength = 255; // Remplacez par la taille maximale de la colonne 'message'

        if (message.length() > maxMessageLength) {
            message = message.substring(0, maxMessageLength); // Tronquer le message
        }
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setRecipient(recipient);
        notification.setRead(false);
        notification.setTimestamp(LocalDateTime.now());
        notification.setType(type);
        notificationRepository.save(notification);

    }

    public List<Notification> getNotificationsForUser(String recipient) {
        return notificationRepository.findByRecipient(recipient);
    }

    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElse(null);
        if (notification != null) {
            notification.setRead(true);
            notificationRepository.save(notification);
        }
    }
}
