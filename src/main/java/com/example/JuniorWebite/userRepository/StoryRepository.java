package com.example.JuniorWebite.userRepository;


import com.example.JuniorWebite.Entity.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoryRepository extends JpaRepository<Story, Integer> {
    List<Story> findByUser_UserID(int userId);
    // Récupérer toutes les stories d'un utilisateur
    Story findById(int id);
}
