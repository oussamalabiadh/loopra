package com.example.JuniorWebite.userService;

import com.example.JuniorWebite.Dto.PostResponse;
import com.example.JuniorWebite.Entity.Like;
import com.example.JuniorWebite.Entity.Post;
import com.example.JuniorWebite.Entity.User;
import com.example.JuniorWebite.userRepository.LikeRepository;
import com.example.JuniorWebite.userRepository.PostRepository;
import com.example.JuniorWebite.userRepository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private LikeRepository likeRepository;

    private final String uploadDir = "C:/Users/oussa/Desktop/projets/Loopra/posts/"; // Dossier local pour stocker les fichiers

    // Constructeur
    public PostService() {
        // Créer le dossier si cela n'existe pas
        try {
            Files.createDirectories(Paths.get(uploadDir));
        } catch (IOException e) {
            e.printStackTrace(); // Gérer l'erreur de création de dossier
        }
    }

    // Méthode pour créer un post
    public PostResponse createPost(Post post, MultipartFile file) {
        PostResponse postResponse = new PostResponse();

        try {
            if (file != null) {
                 if (post.getPostType().equals("Video") || post.getPostType().equals("PDF") || post.getPostType().equals("Image")) {
                    // Vérifier si le fichier existe déjà
                    String filePath = uploadDir + file.getOriginalFilename();
                    File localFile = new File(filePath);
                    if (localFile.exists()) {
                        // Fichier existe déjà
                        postResponse.setState("failed");
                        postResponse.setMessage("Le nom du fichier existe déjà. Veuillez changer le nom du fichier vidéo ou PDF.");
                        postResponse.setData(null); // Pas de données à retourner
                        return postResponse;
                    }
                    // Enregistrer le fichier sur le serveur
                    file.transferTo(localFile); // Enregistrer le fichier
                    post.setFilePath(filePath); // Stocker le chemin du fichier
                }
            }

            // Gérer la date de création
            post.setCreationDate(LocalDateTime.now());

            // Sauvegarder le post dans la base de données
            Post savedPost = postRepository.save(post);
            postResponse.setState("success");
            postResponse.setMessage("Post créé avec succès.");
            postResponse.setData(null); // Retourner le post créé

        } catch (IOException e) {
            postResponse.setState("failed");
            postResponse.setMessage("Erreur lors de la création du post : " + e.getMessage());
            postResponse.setData(null); // Pas de données à retourner
        }

        return postResponse;
    }


    @Transactional
    public PostResponse getAllPosts(User currentUser) {
        PostResponse postResponse = new PostResponse();
        List<Post> posts = postRepository.findAll();

        if (posts.isEmpty()) {
            postResponse.setState("failed");
            postResponse.setMessage("Aucun post trouvé.");
            postResponse.setData(null);
        } else {
            for (Post post : posts) {
                Optional<Like> like = likeRepository.findByUserAndPost(currentUser, post);
                post.setLiked(like.isPresent()); // Mettre à jour isLiked selon la présence d'un like
            }
            postResponse.setState("success");
            postResponse.setMessage("Posts récupérés avec succès.");
            postResponse.setData(posts); // Retourner tous les posts
        }
        return postResponse;
    }

    // Méthode pour obtenir les posts par ID d'utilisateur
    @Transactional
    public PostResponse getPostsByUserId(int userId, User currentUser) {
        PostResponse postResponse = new PostResponse();
        List<Post> posts = postRepository.findByAuthor_UserID(userId); // Assurez-vous d'avoir cette méthode dans le repository

        if (posts.isEmpty()) {
            postResponse.setState("failed");
            postResponse.setMessage("Aucun post trouvé pour cet utilisateur.");
            postResponse.setData(null); // Pas de données à retourner
        } else {
            for (Post post : posts) {
                Optional<Like> like = likeRepository.findByUserAndPost(currentUser, post);
                post.setLiked(like.isPresent()); // Mettre à jour isLiked selon la présence d'un like
            }
            postResponse.setState("success");
            postResponse.setMessage("Posts récupérés avec succès pour l'utilisateur ID " + userId + ".");
            postResponse.setData(posts); // Retourner les posts trouvés
        }

        return postResponse;
    }


    // Méthode pour supprimer un post
    public PostResponse deletePost(int postId) {
        PostResponse postResponse = new PostResponse();

        if (postRepository.existsById(postId)) {
            postRepository.deleteById(postId);
            postResponse.setState("success");
            postResponse.setMessage("Post supprimé avec succès.");
            postResponse.setData(null); // Pas de données à retourner
        } else {
            postResponse.setState("failed");
            postResponse.setMessage("Post avec ID " + postId + " n'existe pas.");
            postResponse.setData(null); // Pas de données à retourner
        }

        return postResponse;
    }
}
