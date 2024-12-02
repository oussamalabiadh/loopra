package com.example.JuniorWebite.userService.userIMPL;

import com.example.JuniorWebite.Dto.loginDTO;
import com.example.JuniorWebite.Dto.restPasswordDTO;
import com.example.JuniorWebite.Dto.updateUser;
import com.example.JuniorWebite.Dto.userDTO;
import com.example.JuniorWebite.Entity.LoginStat;
import com.example.JuniorWebite.Entity.User;
import com.example.JuniorWebite.response.loginResponse;
import com.example.JuniorWebite.response.resetPasswordResponse;
import com.example.JuniorWebite.userRepository.UserRepo;
import com.example.JuniorWebite.userRepository.loginStatRepo;

import com.example.JuniorWebite.userService.UserToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.*;


import io.jsonwebtoken.security.Keys;


@Service
public class UserIMPL implements UserToken {

    /**
     * GENERATE TOKEN
     **/
    private final long expirationTime = 86400000; // 1 day in milliseconds


    @Override
    public Claims buildClaims(String userId, String userName, String email, String role ) {
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("userName", userName);
        claims.put("email", email);
        claims.put("role", role);
        return claims;
    }

    @Override
    public String generateToken(String userId, String userName, String email, String role) {
        Claims claims = this.buildClaims(userId, userName, email, role);

        // Set the expiration time for the token
        long now = System.currentTimeMillis();
        Date expiration = new Date(now + this.expirationTime);

        // Build the JWT token
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(now))
                .setExpiration(expiration)
                .signWith(Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256), SignatureAlgorithm.HS256)
                .compact();

        return token;
    }


    /**********************************************************/

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private loginStatRepo repository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public String addUser(userDTO userDTO) {

        if( userRepo.findByEmail(userDTO.getUserEmail()) == null)
        {
            User user1= new User();
            user1.setUserName(userDTO.getUserName());
            user1.setRole("USER");
            user1.setEmail(userDTO.getUserEmail());
            user1.setPassword(this.passwordEncoder.encode(userDTO.getUserPassword()));
            user1.setDateOfRegister(LocalDateTime.now());
            userRepo.save(user1);

            return user1.getUserName();
        }

        else return "Failed, this email is already used";

    }
    public User FindUser(String email){
   return userRepo.findByEmail(email);
    }

    public User FindUserByname(String userName){
        return userRepo.findByUserName(userName);
    }


    public void updateUserLoginStat(String username) {
        LoginStat loginStat = new LoginStat();
        loginStat.setUserName(username);
        repository.save(loginStat);
    }

    public loginResponse loginUser(loginDTO loginDTO) {

            User user1 = userRepo.findByEmail(loginDTO.getUserEmail());

        if (user1 != null) {
                String password = loginDTO.getUserPassword();
                String encodedPassword = user1.getPassword();

                if (passwordEncoder.matches(password, encodedPassword)) {
                    Optional<User> user = userRepo.findOneByEmailAndPassword(loginDTO.getUserEmail(), encodedPassword);
                    if (user.isPresent()) {
                        String token = this.generateToken(String.valueOf(user1.getUserID()),user1.getUserName(), user1.getEmail(),user1.getRole() );
                        updateUserLoginStat(user1.getUserName());
                        return new loginResponse("Login Success", true,token);
                    } else {
                        return new loginResponse("Login Failed", false,"USER NOT FOUND");
                    }
                } else {
                    return new loginResponse("password Not Match", false,"USER NOT FOUND");
                }
                }
            else {
                return new loginResponse("Email not exits", false,"USER NOT FOUND");
            }
        }


    public resetPasswordResponse resetPassword(restPasswordDTO restPasswordDTO) {

        String email=restPasswordDTO.getUserEmail();
        User user = userRepo.findByEmail(restPasswordDTO.getUserEmail());

        if(user!=null)
        {
            user.setPassword(this.passwordEncoder.encode(restPasswordDTO.getUserPassword()));
            userRepo.save(user);
            return new resetPasswordResponse(email,"password updated",true);
        }
        else {
            return new resetPasswordResponse("No user found with this email","failed to update",false);

        }

    }

    public User updateUser(int userId , updateUser updateUser) {
        Optional<User> existingUserOptional = userRepo.findById(userId);

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();

            // Mettre à jour les champs de l'utilisateur existant avec ceux de updatedUser
            existingUser.setUserName(updateUser.getUserName());
            existingUser.setEmail(updateUser.getEmail());
            existingUser.setUserPicture(updateUser.getUserPicture());
            existingUser.setCouvertirPicture(updateUser.getCouvertirPicture());
            existingUser.setBio(updateUser.getBio());
            existingUser.setPosition(updateUser.getPosition());
            existingUser.setHabite(updateUser.getHabite());
            existingUser.setLien_facebook(updateUser.getLien_facebook());
            existingUser.setLien_github(updateUser.getLien_github());
            existingUser.setLien_instagrame(updateUser.getLien_instagrame());
            existingUser.setLien_linkedin(updateUser.getLien_linkedin());
            existingUser.setEtude(updateUser.getEtude());
            existingUser.setTravail(updateUser.getTravail());
            return userRepo.save(existingUser);
        } else {
            // Si l'utilisateur n'existe pas
            throw new RuntimeException("User not found with ID: " + userId);
        }
    }
    public User findUserByName(String userName){
        return userRepo.findByUserName(userName);
    }



}



