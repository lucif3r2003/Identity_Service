package com.identity.Identity_Service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.identity.Identity_Service.entity.User;
import com.identity.Identity_Service.enums.Role;
import com.identity.Identity_Service.exceptions.AppException;
import com.identity.Identity_Service.exceptions.ErrorCode;
import com.identity.Identity_Service.mapper.UserMapper;
import com.identity.Identity_Service.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import com.identity.Identity_Service.dto.request.UserCreationRequest;
import com.identity.Identity_Service.dto.request.UserUpdateRequest;
import com.identity.Identity_Service.dto.response.APIResponse;
import com.identity.Identity_Service.dto.response.UserResponse;

import java.util.*;



@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService{
    UserRepository uRepo;

    UserMapper mapper;
    
    //create user
    public UserResponse createRequest(UserCreationRequest req){
        try{
            User user = new User();
            
            if(uRepo.existsByUsername(req.getUsername())){
                throw new AppException(ErrorCode.USERNAME_EXISTS);
            }

            user = mapper.toUser(req);
            PasswordEncoder pEncoder = new BCryptPasswordEncoder(10); 
            user.setPassword(pEncoder.encode(req.getPassword()));

            HashSet<String> roles = new HashSet<>();
            roles.add(Role.USER.name());
            user.setRoles(roles);

            return mapper.toUserResponse(uRepo.save(user));
        } catch(Exception e){
            throw new RuntimeException("Fail to create user");
        }
    }

    public List<UserResponse> getUser(){
        List<User> users = uRepo.findAll();
        List<UserResponse> userResponses = new ArrayList<>();
        for(User user: users){
            userResponses.add(mapper.toUserResponse(user));
        }
        return  userResponses;
    }
    public UserResponse getUser(String id){
        return mapper.toUserResponse(uRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("user not found!")));  
    }
    
    public UserResponse updateUser(String id, UserUpdateRequest req){
        try {
            User user = uRepo.findById(id)
                        .orElseThrow(()-> new RuntimeException("user not found"));
            mapper.updateUser(user, req);
            return mapper.toUserResponse( uRepo.save(user));

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
