package com.example.JuniorWebite.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class resetPasswordResponse {
    private String email;
    private String discription;
    private Boolean passwordUpdated;
}
