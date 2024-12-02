package com.example.JuniorWebite.userRepository;
import com.example.JuniorWebite.Entity.Like;
import com.example.JuniorWebite.Entity.Post;
import com.example.JuniorWebite.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {
    long countByPost(Optional<Post> post); // Compter le nombre de likes d'un post

    List<User> findUsersByPost(Optional<Post> post); // Récupérer les utilisateurs ayant aimé un post}
    Optional<Like> findByUserAndPost(User user, Post post);

}
