package com.example.JuniorWebite.Entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    private int postId;

    @Column(name = "content")
    private String content;

    @Column(name = "post_type")
    private String postType; // "Image", "Video", "PDF"

    @CreationTimestamp
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author; // L'auteur du post

    @Lob
    @Column(name = "image_data")
    private byte[] imageData; // Pour les images stockées dans la base de données

    @Column(name = "file_path")
    private String filePath; // Pour les vidéos et PDF
    @JsonManagedReference("comment")
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments; // Liste des commentaires associés au post

    @JsonManagedReference("postLikes") // Correspond au nom de la back-reference dans Like
    @OneToMany(mappedBy = "post")
    private List<Like> likes; // Liste des commentaires associés au post
    @Transient // Cet attribut ne sera pas persisté dans la base de données
    private boolean isLiked; // Indique si l'utilisateur actuel a aimé le post
}

