package com.example.JuniorWebite.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.sql.Date;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@ToString
public class AllUserDTO {

    public int userID;
    public Date dateOfRegister;
    public String role;
    public String userName;
    public String userEmail;
   public String userPicture;


    public AllUserDTO(int userID, Date dateOfRegister, String role, String userName, String email, byte[] userPicture, byte[] couvertirPicture) {
    }

    public AllUserDTO() {

    }

    public AllUserDTO(int userID, LocalDateTime dateOfRegister, String role, String userName, String email, byte[] userPicture, byte[] couvertirPicture) {
    }
}
