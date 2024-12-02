package com.example.JuniorWebite.userService;

import io.jsonwebtoken.Claims;


public interface UserToken {



    Claims buildClaims(String userId, String userName, String email, String role   ) ;
    String generateToken(String userId,String userName,String email,String role ) ;
}
