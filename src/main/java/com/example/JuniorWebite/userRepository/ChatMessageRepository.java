package com.example.JuniorWebite.userRepository;


import com.example.JuniorWebite.Entity.ChatMessageEntity;
import com.example.JuniorWebite.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Long> {
    // Tu peux ajouter des méthodes supplémentaires si nécessaire
    List<ChatMessageEntity> findChatMessageEntitiesBySenderAndRecipient(User sender , User recipient);
}

