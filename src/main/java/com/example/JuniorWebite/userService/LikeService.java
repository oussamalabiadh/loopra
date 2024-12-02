package com.example.JuniorWebite.userService;
import com.example.JuniorWebite.Dto.AllUserDTO;
import com.example.JuniorWebite.Dto.LikeDTO;
import com.example.JuniorWebite.Dto.LikeResponse;
import com.example.JuniorWebite.Dto.PostResponse;
import com.example.JuniorWebite.Entity.Like;
import com.example.JuniorWebite.Entity.Notification;
import com.example.JuniorWebite.Entity.User;
import com.example.JuniorWebite.Entity.Post;

import com.example.JuniorWebite.userRepository.LikeRepository;
import com.example.JuniorWebite.userRepository.PostRepository;
import com.example.JuniorWebite.userRepository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private PostRepository postRepository ;
    @Autowired
    NotificationService notificationService;
    @Autowired
    UserRepo userRepo;

    public LikeResponse addLike(LikeDTO likeDTO) {
        LikeResponse likeResponse = new LikeResponse();
        User user = userRepo.findByUserID(likeDTO.getUserId());
        Post post =postRepository.findPostByPostId(likeDTO.getPostId());
        // Vérifier si l'utilisateur a déjà liké ce post
        Optional<Like> existingLike = likeRepository.findByUserAndPost(user, post);

        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get());
            likeResponse.setState("failed");
            likeResponse.setMessage("Vous avez déjà liké ce post.");
        } else {
            Like like = new Like();
            like.setUser(user);
            like.setPost(post);

            likeRepository.save(like);;
            likeResponse.setState("success");
            likeResponse.setMessage("Like ajouté avec succès.");

            // Envoyer une notification
            String notificationMessage =  "Une personne a aimé votre post récemment "+like.getUser().getUserName() ;
            notificationService.createNotification(notificationMessage, like.getPost().getAuthor().getUserName(), Notification.NotificationType.LIKE);
        }

        return likeResponse;
    }

    // Méthode pour vérifier si un post est liké par un utilisateur
    public boolean isPostLikedByUser(User user, Post post) {
        Optional<Like> existingLike = likeRepository.findByUserAndPost(user, post);
        return existingLike.isPresent();
    }



    // Méthode pour récupérer les utilisateurs ayant aimé un post
    public AllUserDTO getUsersWhoLikedPost(int postId) {
        Optional<Post> post = postRepository.findById(postId) ;
        AllUserDTO allUserDTO = new AllUserDTO();
        List<User> users = likeRepository.findUsersByPost(post);

        if (users.isEmpty()) {
              return null ;
        } else {
         return (AllUserDTO) users;
        }

    }
}

