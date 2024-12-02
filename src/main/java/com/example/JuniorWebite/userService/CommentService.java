package com.example.JuniorWebite.userService;
import com.example.JuniorWebite.Dto.CommentDTO;
import com.example.JuniorWebite.Dto.CommentResponse;
import com.example.JuniorWebite.Dto.PostResponse;
import com.example.JuniorWebite.Entity.Comment;
import com.example.JuniorWebite.Entity.Notification;
import com.example.JuniorWebite.Entity.Post;
import com.example.JuniorWebite.Entity.User;
import com.example.JuniorWebite.userRepository.CommentRepository;
import com.example.JuniorWebite.userRepository.PostRepository;
import com.example.JuniorWebite.userRepository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository ;
    @Autowired
    NotificationService notificationService;
    @Autowired
    UserRepo userRepo;

    // Méthode pour ajouter un commentaire à un post
    public CommentResponse addComment(CommentDTO commentDTO) {
        CommentResponse commentResponse = new CommentResponse();
        Optional<Post> postOptional = postRepository.findById(commentDTO.getPostId());
        User user = userRepo.findByUserID(commentDTO.getUserId());

        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            Comment comment=new Comment();
            comment.setPost(post);  // Associer le post au commentaire
            comment.setCreatedAt(LocalDateTime.now());
            comment.setUser(user);
            comment.setContent(commentDTO.getContent());
            Comment savedComment = commentRepository.save(comment);
            commentResponse.setState("success");
            commentResponse.setMessage("Commentaire ajouté avec succès.");
            commentResponse.setData(List.of(savedComment)); // Retourner le commentaire ajouté

            String notificationMessage = "Une personne a commenter votre post récemment "+ user.getUserName();
            notificationService.createNotification(notificationMessage, post.getAuthor().getUserName(), Notification.NotificationType.COMMENT);

        } else {
            commentResponse.setState("failed");
            commentResponse.setMessage("Post non trouvé.");
        }

        return commentResponse;
    }


    // Méthode pour obtenir les commentaires d'un post
    public CommentResponse getCommentsByPostId(int postId) {
        CommentResponse commentResponse = new CommentResponse();
        Optional<Post> post = postRepository.findById(postId) ;
        List<Comment> comments = commentRepository.findByPost(post);

        if (comments.isEmpty()) {
            commentResponse.setState("failed");
            commentResponse.setMessage("Aucun commentaire trouvé pour ce post.");
            commentResponse.setData(null); // Pas de données à retourner
        } else {
            commentResponse.setState("success");
            commentResponse.setMessage("Commentaires récupérés avec succès.");
            commentResponse.setData(comments); // Retourner les commentaires trouvés
        }

        return commentResponse;
    }
}
