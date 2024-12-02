package com.example.JuniorWebite.AdminService;


import com.example.JuniorWebite.Dto.AllUserDTO;
import com.example.JuniorWebite.Dto.StatLoginDTO;
import com.example.JuniorWebite.Entity.User;
import com.example.JuniorWebite.response.responseGenerale;
import com.example.JuniorWebite.userRepository.UserRepo;
import com.example.JuniorWebite.userRepository.loginStatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private loginStatRepo repository;

    public List<Object[]> getCountByMonth() {return repository.getCountByMonth();}

    @Autowired
    private UserRepo userRepository;


    public List<User> getAllUsers() {
        List<User> userEntities = userRepository.findAll();
        return userEntities;
    }

    private List<AllUserDTO> convertToDTO(List<User> userEntities) {

        List<AllUserDTO> list=new ArrayList<AllUserDTO>();

        for (User user: userEntities) {

            AllUserDTO userDTO=new AllUserDTO(
                    user.getUserID(),
                    user.getDateOfRegister(),
                    user.getRole(),
                    user.getUserName(),
                    user.getEmail(),
                    user.getUserPicture(),
                    user.getCouvertirPicture()
            );

            list.add(userDTO);
        }

        return list;
    }

    public responseGenerale removeUser(Integer userId)
    {
        String userName=userRepository.findUserBy(userId);
        if(userName!=null)
        {
            userRepository.deleteById(userId);
            return new responseGenerale("User ' "+userName+" ' deleted successfully");
        }
        else
        {
            return new responseGenerale("User Not Found ");
        }


    }


}
