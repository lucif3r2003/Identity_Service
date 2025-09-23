package com.identity.Identity_Service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.identity.Identity_Service.entity.User;
import com.identity.Identity_Service.repository.UserRepository;
import com.identity.Identity_Service.dto.request.UserCreationRequest;
import com.identity.Identity_Service.dto.request.UserUpdateRequest;

import java.util.*;


@Service
public class UserService{
    @Autowired
    private UserRepository uRepo;
    
    //create user
    public User createRequest(UserCreationRequest req){
        try{
            User user = new User();

            if(uRepo.existByUsername(req.getUsername())){
                throw new RuntimeException("user exist!");
            }
            user.setUsername(req.getUsername());
            user.setPassword(req.getPassword());
            user.setFirstName(req.getFirstName());
            user.setLastName(req.getLastName());
            user.setDob(req.getDob());
            return uRepo.save(user);
        } catch(Exception e){
            throw new RuntimeException("Fail to create user");
        }
    }

    public List<User> getUser(){
        return uRepo.findAll();
    }
    public User getUser(String id){
        return uRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("user not found!"));
    }
    
    public User updateUser(String id, UserUpdateRequest req){
        try {
            User user = getUser(id);
            user.setPassword(req.getPassword());
            user.setFirstName(req.getFirstName());
            user.setLastName(req.getLastName());
            user.setDob(req.getDob());
            return uRepo.save(user);

        } catch (Exception e) {
            throw new RuntimeException("cannot update user");
        }
    }

    public void deleteUser(String id){
        try {
            uRepo.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("fail to delete user");
        }
    }
    
}
