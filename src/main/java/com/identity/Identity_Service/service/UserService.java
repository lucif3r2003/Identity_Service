package com.identity.Identity_Service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.identity.Identity_Service.entity.User;
import com.identity.Identity_Service.repository.UserRepository;
import com.identity.Identity_Service.dto.request.UserCreationRequest;

@Service
public class UserService{
    @Autowired
    private UserRepository uRepo;
    
    //create user
    public User createRequest(UserCreationRequest req){
        User user = new User();
        user.setUsername(req.getUsername());
        user.setPassword(req.getPassword());
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setDob(req.getDob());
        
        return uRepo.save(user);
    }
}
