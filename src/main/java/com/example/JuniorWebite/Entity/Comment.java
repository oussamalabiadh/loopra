package com.example.JuniorWebite.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "comments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @JsonBackReference("comment")
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post; // Le post auquel le commentaire est associé

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // L'utilisateur qui a commenté

    @Column(name = "content")
    private String content; // Contenu du commentaire

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt; // Date de création du commentaire


}

