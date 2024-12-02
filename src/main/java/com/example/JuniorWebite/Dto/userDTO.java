package com.example.JuniorWebite.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.sql.Date;


@Data
@ToString
public class userDTO {

    public int userID;
    public Date dateOfRegister;
    public String role;
    public String userName;
    public String userEmail;
    public String userPassword;
    public String confirmPassword;

    public userDTO(String userName, String userEmail, String userPassword,String confirmPassword) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.confirmPassword=confirmPassword;


    }
}
