package com.example.JuniorWebite.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "stories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // Utilisateur qui a créé la Story


    @Column(name = "video_path") // Pour stocker le chemin du fichier vidéo
    private String videoPath;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt; // Date de création de la Story
private int duration = 10;
}

