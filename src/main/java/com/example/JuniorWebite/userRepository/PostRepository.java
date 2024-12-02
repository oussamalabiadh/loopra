package com.example.JuniorWebite.userRepository;

import com.example.JuniorWebite.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByAuthor_UserID(int userId); // Récupérer tous les posts d'un utilisateur
    Post findPostByPostId(int postID);
}
