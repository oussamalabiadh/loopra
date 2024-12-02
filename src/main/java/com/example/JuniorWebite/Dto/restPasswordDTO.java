package com.example.JuniorWebite.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class restPasswordDTO {
    public String userEmail;
    public String userPassword;
}
