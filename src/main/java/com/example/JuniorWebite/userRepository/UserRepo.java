package com.example.JuniorWebite.userRepository;


import com.example.JuniorWebite.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



import java.util.*;

@EnableJpaRepositories
@Repository
public interface UserRepo extends JpaRepository<User,Integer> {

     Optional<User> findOneByEmailAndPassword(String email,String password);

    User findByEmail(String userEmail);
    User findByUserName(String userName);
    @Override
    void deleteById(Integer integer);

    @Query("SELECT u.userName FROM User u WHERE u.userID = :ID")
    String findUserBy(@Param("ID") Integer ID);

    User findByUserID ( Integer integer);

}
