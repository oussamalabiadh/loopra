package com.example.JuniorWebite.userControle;

import com.example.JuniorWebite.Dto.CommentResponse;
import com.example.JuniorWebite.Dto.PostResponse;
import com.example.JuniorWebite.Entity.Comment;
import com.example.JuniorWebite.Entity.Notification;
import com.example.JuniorWebite.userService.CommentService;
import com.example.JuniorWebite.userService.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/{recipient}")
    public List<Notification> getNotificationsForUser(@PathVariable String recipient) {
        return notificationService.getNotificationsForUser(recipient);
    }
  @GetMapping("/markAsRead/{notificationId}")
    public void markAsRead(@PathVariable Long notificationId) {
      notificationService.markAsRead(notificationId);
  }

}

