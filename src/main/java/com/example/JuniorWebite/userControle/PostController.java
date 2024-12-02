package com.example.JuniorWebite.userControle;

import com.example.JuniorWebite.Dto.PostResponse; // Assurez-vous d'importer PostResponse
import com.example.JuniorWebite.Entity.Notification;
import com.example.JuniorWebite.Entity.Post;
import com.example.JuniorWebite.userRepository.UserRepo;
import com.example.JuniorWebite.userService.NotificationService;
import com.example.JuniorWebite.userService.PostService;
import com.example.JuniorWebite.Entity.User; // Assurez-vous d'importer User
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private NotificationService notificationService;

    // Méthode pour créer un post
    @PostMapping("/create")
    public ResponseEntity<PostResponse> createPost(@RequestParam("content") String content,
                                                   @RequestParam(value = "postType", required = false) String postType,
                                                   @RequestParam(value = "file", required = false) MultipartFile file,
                                                   @RequestParam("userId") int userId) {
        Post post = new Post();
        post.setContent(content);
        post.setPostType(postType);

        // Récupérer l'utilisateur par ID et le définir comme auteur du post
        User author = userRepo.findByUserID(userId);
        post.setAuthor(author);
        String notificationMessage = post.getAuthor() + " a publié un nouveau post.";
        notificationService.createNotificationForFriends(notificationMessage, post.getAuthor().getUserID(), Notification.NotificationType.POST);

        return ResponseEntity.ok(postService.createPost(post, file ));
    }

    // Méthode pour obtenir tous les posts
    @GetMapping("/{userId}")
    public ResponseEntity<PostResponse> getAllPosts(@PathVariable int userId) {
        User user = userRepo.findByUserID(userId);

        return ResponseEntity.ok(postService.getAllPosts(user));
    }

    // Méthode pour obtenir les posts par ID d'utilisateur
    @GetMapping("/user/{userId}")
    public ResponseEntity<PostResponse> getPostsByUserId(@PathVariable int userId) {
        User user = userRepo.findByUserID(userId);
        return ResponseEntity.ok(postService.getPostsByUserId(userId,user));
    }

    // Méthode pour supprimer un post
    @DeleteMapping("/{postId}")
    public ResponseEntity<PostResponse> deletePost(@PathVariable int postId) {
        return ResponseEntity.ok(postService.deletePost(postId));
    }
    private static final String IMAGE_DIRECTORY = "C:/Users/oussa/Desktop/projets/Loopra/posts/";
    private static final String PDF_DIRECTORY = "C:/Users/oussa/Desktop/projets/Loopra/posts/";

    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) throws IOException {
        Path filePath = Paths.get(IMAGE_DIRECTORY).resolve(filename).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        if (resource.exists() && resource.isReadable()) {
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE; // Par défaut si le type n'est pas déterminé
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Méthode pour obtenir les PDF
    @GetMapping("/pdfs/{filename:.+}")
    public ResponseEntity<Resource> getPdf(@PathVariable String filename) throws IOException {
        Path filePath = Paths.get(PDF_DIRECTORY).resolve(filename).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        if (resource.exists() && resource.isReadable()) {
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE; // Par défaut si le type n'est pas déterminé
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    private static final String VIDEO_DIRECTORY = "C:/Users/oussa/Desktop/projets/Loopra/posts/";

    @GetMapping("/video/{filename}")
    public ResponseEntity<Resource> getVideo(@PathVariable String filename) throws IOException {
        Path filePath = Paths.get(VIDEO_DIRECTORY).resolve(filename).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        if (resource.exists() && resource.isReadable()) {
            // Détecter le type MIME du fichier
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream"; // Par défaut si le type n'est pas déterminé
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
