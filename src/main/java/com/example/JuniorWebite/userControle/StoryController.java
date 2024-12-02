package com.example.JuniorWebite.userControle;

import com.example.JuniorWebite.Dto.StoryResponse;
import com.example.JuniorWebite.Entity.Notification;
import com.example.JuniorWebite.Entity.Story;
import com.example.JuniorWebite.userRepository.UserRepo;
import com.example.JuniorWebite.userService.NotificationService;
import com.example.JuniorWebite.userService.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/stories")
public class StoryController {

    @Autowired
    private StoryService storyService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserRepo user;
    @PostMapping
    public ResponseEntity<StoryResponse> createStory(@RequestParam("file") MultipartFile file, @RequestParam("userId") int userId) {
        Story story = new Story();
        story.setUser(user.findByUserID(userId));
        story.setDuration(10);
        story.setCreatedAt(LocalDateTime.now());
        // Assurez-vous que l'utilisateur est configuré
        String notificationMessage = story.getUser().getUserName() + " a ajouté une nouvelle histoire.";
        notificationService.createNotificationForFriends(notificationMessage, story.getUser().getUserID(), Notification.NotificationType.STORY);
        return ResponseEntity.ok(storyService.createStory(story, file));
    }
    @GetMapping
    public ResponseEntity<StoryResponse> getAllStories() {
        return ResponseEntity.ok(storyService.getAllStories());
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<StoryResponse> getStoriesByUserId(@PathVariable int userId) {
        return ResponseEntity.ok(storyService.getStoriesByUserId(userId));
    }

    @GetMapping("/user/story/{storyId}")
    public ResponseEntity<StoryResponse> getStoriesById(@PathVariable int storyId) {
        return ResponseEntity.ok(storyService.getStoryById(storyId));
    }

    private static final String VIDEO_DIRECTORY = "C:/Users/oussa/Desktop/projets/Loopra/stories/";

    @GetMapping("/{filename}")
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
