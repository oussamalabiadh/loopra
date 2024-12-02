package com.example.JuniorWebite.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@NonNull
@Entity
@Table(name="user-info")
public class User {

    @Id
    @Column(name = "user-id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userID;

    @Column(name = "date-Of-Register")
    @CreationTimestamp
    private LocalDateTime dateOfRegister;

    @Column(name = "role")
    private String role;
    @Column(name = "position")
    private String position;
    @Column(name = "habite")
    private String habite;
    @Column(name = "lien_facebook")
    private String lien_facebook;
    @Column(name = "lien_instagrame")
    private String lien_instagrame;
    @Column(name = "lien_github")
    private String lien_github;
    @Column(name = "lien_linkedin")
    private String lien_linkedin;
    @Column(name = "etude")
    private String etude;
    @Column(name = "travail")
    private String travail;
    @Column(name = "user-name")
    private String userName;

    @Column(name = "email")
    @NaturalId(mutable = true)
    private String email;
    @Column(name = "bio")
    private String bio;

    @Column(name = "password")
    private String password;
    @Lob
    @Column(name = "User-Picture",columnDefinition = "LONGBLOB")
    private byte[] UserPicture;
    @Lob
    @Column(name = "couvertir-Picture",columnDefinition = "LONGBLOB")
    private byte[] couvertirPicture;
    @JsonManagedReference // Indicate this is the parent side of the relationship

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Relationship> relationships;
    @OneToMany(mappedBy = "friend", cascade = CascadeType.ALL)
    private Set<Relationship> receivedRelationships; // Relations reçues



    public String getBio(String bio) {
        return this.bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
    public User(int userId) {
    }

}
