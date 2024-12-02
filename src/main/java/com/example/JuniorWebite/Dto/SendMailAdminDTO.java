package com.example.JuniorWebite.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;


@AllArgsConstructor
@Data
@ToString
public class SendMailAdminDTO {

    private String message ;
    private String email ;
    private String phoneNumber ;
    private String lastName ;
    private String firstName ;
}
