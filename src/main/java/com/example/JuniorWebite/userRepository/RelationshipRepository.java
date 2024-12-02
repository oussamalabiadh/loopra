package com.example.JuniorWebite.userRepository;

import com.example.JuniorWebite.Entity.Relationship;
import com.example.JuniorWebite.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelationshipRepository extends JpaRepository<Relationship, Integer> {
    List<Relationship> findByUser_UserID(int userId); // Trouver des relations par utilisateur
    Relationship findByUserAndFriend(User user, User friend);

}

