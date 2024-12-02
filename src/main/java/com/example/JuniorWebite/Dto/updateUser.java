package com.example.JuniorWebite.Dto;

import com.example.JuniorWebite.Entity.Relationship;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;

import java.time.LocalDateTime;
import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class updateUser {
        private LocalDateTime dateOfRegister;

        private String role;
        private String position;
        private String habite;
        private String lien_facebook;
        private String lien_instagrame;
        private String lien_github;
        private String lien_linkedin;
        private String etude;
        private String travail;
        private String userName;
        private String email;
        private String bio;
        private String password;
        @Lob
        private byte[] UserPicture;
        @Lob
        private byte[] couvertirPicture;


    }
