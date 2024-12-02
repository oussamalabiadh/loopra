package com.example.JuniorWebite.userRepository;

import com.example.JuniorWebite.Entity.Comment;
import com.example.JuniorWebite.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByPost(Optional<Post> post); // Récupérer tous les commentaires d'un post

}

