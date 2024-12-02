package com.example.JuniorWebite.userControle;


import com.example.JuniorWebite.Entity.Relationship;
import com.example.JuniorWebite.Entity.User;
import com.example.JuniorWebite.userRepository.UserRepo;
import com.example.JuniorWebite.userService.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/relationships")
public class RelationshipController {

    @Autowired
    private RelationshipService relationshipService;
    @Autowired
    private UserRepo userRepo;

    // Méthode pour créer une relation
    @PostMapping("/create")
    public ResponseEntity<Relationship> createRelationship(@RequestParam("userId") int userId,
                                                           @RequestParam("friendId") int friendId) {
        Relationship createdRelationship = relationshipService.createRelationship(userId, friendId);
        return ResponseEntity.ok(createdRelationship);
    }
    @GetMapping("/status")
    public ResponseEntity<String> checkFriendshipStatus(
            @RequestParam int userId1,
            @RequestParam int userId2) {
        // Récupérer les utilisateurs en fonction de leur ID
        User user1 = userRepo.findByUserID(userId1);
        User user2 = userRepo.findByUserID(userId2);

        if (user1 == null || user2 == null) {
            return ResponseEntity.badRequest().body("Un ou plusieurs utilisateurs n'existent pas.");
        }

        // Vérifier l'état de la relation
        String status = relationshipService.checkFriendshipStatus(user1, user2);
        return ResponseEntity.ok(status);
    }
    // Méthode pour accepter une relation
    @PostMapping("/accept")
    public ResponseEntity<Relationship> acceptRelationship( @RequestParam int userId1,
                                                            @RequestParam int userId2) {
        User user1 = userRepo.findByUserID(userId1);
        User user2 = userRepo.findByUserID(userId2);
        Relationship updatedRelationship = relationshipService.acceptRelationship(user1,user2);
        return ResponseEntity.ok(updatedRelationship);
    }

    // Méthode pour refuser une relation
    @DeleteMapping("/reject")
    public ResponseEntity<Void> rejectRelationship( @RequestParam int userId1,
                                                    @RequestParam int userId2) {
        User user1 = userRepo.findByUserID(userId1);
        User user2 = userRepo.findByUserID(userId2);
        relationshipService.rejectRelationship(user1,user2);
        return ResponseEntity.noContent().build();
    }

    // Méthode pour obtenir toutes les relations d'un utilisateur
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Relationship>> getRelationshipsByUserId(@PathVariable int userId) {
        List<Relationship> relationships = relationshipService.getRelationshipsByUserId(userId);
        return ResponseEntity.ok(relationships);
    }
}

