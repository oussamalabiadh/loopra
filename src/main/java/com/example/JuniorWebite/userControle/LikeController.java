package com.example.JuniorWebite.userControle;
import com.example.JuniorWebite.Dto.AllUserDTO;
import com.example.JuniorWebite.Dto.LikeDTO;
import com.example.JuniorWebite.Dto.LikeResponse;
import com.example.JuniorWebite.Dto.PostResponse;
import com.example.JuniorWebite.Entity.Like;
import com.example.JuniorWebite.userService.LikeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping(value = "/like", consumes = "application/json", produces = "application/json")
    public ResponseEntity<LikeResponse> addLike(@Valid @RequestBody LikeDTO likeDTO) {
        try {
            LikeResponse likeResponse = likeService.addLike(likeDTO);
            return ResponseEntity.ok(likeResponse);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new LikeResponse("failed", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new LikeResponse("failed", "Une erreur est survenue."));
        }
    }




    @GetMapping("/post/{postId}/users")
    public ResponseEntity<AllUserDTO> getUsersWhoLikedPost(@PathVariable int postId) {
        return ResponseEntity.ok(likeService.getUsersWhoLikedPost(postId));
    }
}

