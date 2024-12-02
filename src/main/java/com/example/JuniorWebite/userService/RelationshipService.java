package com.example.JuniorWebite.userService;

import com.example.JuniorWebite.Entity.Notification;
import com.example.JuniorWebite.Entity.Relationship;
import com.example.JuniorWebite.Entity.User;
import com.example.JuniorWebite.userRepository.RelationshipRepository;
import com.example.JuniorWebite.userRepository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelationshipService {

    @Autowired
    private RelationshipRepository relationshipRepository;
    @Autowired
    NotificationService notificationService;
@Autowired
    UserRepo userRepo;
    // Méthode pour créer une relation
    public Relationship createRelationship(int userId, int friendId) {
        Relationship relationship = new Relationship();
        User user = userRepo.findByUserID(userId);
        User friend = userRepo.findByUserID(friendId);

        relationship.setUser(user); // Assurez-vous d'avoir le bon User
        relationship.setFriend(friend); // Assurez-vous d'avoir le bon User
        relationship.setStatus("EN_ATTENTE"); // Par défaut, la relation est en attente
        String message = "Vous avez reçu une demande d'ami de " + user.getUserName();
        notificationService.createNotification(message, friend.getUserName(), Notification.NotificationType.RELATIONSHIP);
        return relationshipRepository.save(relationship);
    }

    // Méthode pour accepter une relation
    public Relationship acceptRelationship(User user1, User user2) {
        Relationship relationship1 = relationshipRepository.findByUserAndFriend(user1, user2);
        relationship1.setStatus("ACCEPTEE");
            String message = "Votre demande d'ami a été acceptée par " + relationship1.getFriend().getUserName();
            notificationService.createNotification(message, relationship1.getUser().getUserName(), Notification.NotificationType.RELATIONSHIP);
            return relationshipRepository.save(relationship1);

    }


    public void rejectRelationship(User user1, User user2) {
        Relationship relationship1 = relationshipRepository.findByUserAndFriend(user1, user2);
            String message = "Votre demande d'ami a été refusée par " + relationship1.getFriend().getUserName();
            notificationService.createNotification(message, relationship1.getUser().getUserName(), Notification.NotificationType.RELATIONSHIP);
            relationshipRepository.deleteById(relationship1.getId());

    }

    // Méthode pour obtenir toutes les relations d'un utilisateur
    public List<Relationship> getRelationshipsByUserId(int userId) {
        return relationshipRepository.findByUser_UserID(userId);
    }
    public String checkFriendshipStatus(User user1, User user2) {
        // Vérifier si une relation existe entre user1 et user2
        Relationship relationship1 = relationshipRepository.findByUserAndFriend(user1, user2);
        Relationship relationship2 = relationshipRepository.findByUserAndFriend(user2, user1);

        if (relationship1 != null && relationship1.getStatus().equals("ACCEPTEE")) {
            return "amis";
        } else if (relationship1 != null && relationship1.getStatus().equals("EN_ATTENTE")) {
            return "toi";
        } else if (relationship2 != null && relationship2.getStatus().equals("EN_ATTENTE")) {
            return "il";
        } else if (relationship2 != null && relationship2.getStatus().equals("ACCEPTEE")) {
            return "amis";}
        else {
            return "non";
        }
    }
}
