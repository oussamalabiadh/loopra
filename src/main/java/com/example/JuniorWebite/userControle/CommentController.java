package com.example.JuniorWebite.userControle;

import com.example.JuniorWebite.Dto.*;
import com.example.JuniorWebite.Entity.Comment;
import com.example.JuniorWebite.userService.CommentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping(value = "/comment", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommentResponse> addComment(@Valid @RequestBody CommentDTO commentDTO) {
        try {
            CommentResponse commentResponse = commentService.addComment(commentDTO);
            return ResponseEntity.ok(commentResponse);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CommentResponse("failed", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CommentResponse("failed", "Une erreur est survenue."));
        }
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<CommentResponse> getCommentsByPostId(@PathVariable Integer  postId) {
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
    }
}
