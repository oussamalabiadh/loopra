package com.example.JuniorWebite.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentDTO {
    private int postId;
    private int userId;
    private String content ;

    // Getters et setters

}
