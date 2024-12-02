package com.example.JuniorWebite.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@NonNull
@Entity
@Table(name="loginStat")
public class LoginStat {


    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;


    @Column(name = "userName")
    private String userName;


    @Column(name = "date-Of-Login")
    @CreationTimestamp
    private Date dateOfLogin;



}
