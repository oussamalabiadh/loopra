package com.example.JuniorWebite.Entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "relationships")
public class Relationship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference // Indicate this is the back side of the relationship

    private User user; // Utilisateur qui a initié la relation

    @ManyToOne
    @JoinColumn(name = "friend_id")
    @JsonBackReference // Indicate this is the back side of the relationship
    private User friend; // Utilisateur avec qui la relation est établie

    private String status; // "EN_ATTENTE", "ACCEPTEE", "REFUSEE"

    // Getters et Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

