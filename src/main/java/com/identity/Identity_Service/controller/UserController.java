package com.identity.Identity_Service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import com.identity.Identity_Service.entity.User;

import com.identity.Identity_Service.dto.request.UserCreationRequest;
import com.identity.Identity_Service.dto.request.UserUpdateRequest;
import com.identity.Identity_Service.dto.response.APIResponse;
import com.identity.Identity_Service.dto.response.UserResponse;
import com.identity.Identity_Service.service.UserService;

import jakarta.validation.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class UserController{
    UserService service;
    @PostMapping
    APIResponse<UserResponse> create(@RequestBody @Valid UserCreationRequest req){
        APIResponse<UserResponse> res = new APIResponse<UserResponse>();
        res.setResult(service.createRequest(req));

        return res;
    }

    @GetMapping
    APIResponse<List<UserResponse>> getUser(){

        var auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("Username: {}", auth.getName());
        auth.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        
        return APIResponse.<List<UserResponse>>builder()
                      .result(service.getUser())
                      .build();  
    }

    @GetMapping("/{userId}")
    UserResponse getUser(@PathVariable("userId") String userId){
       return service.getUser(userId); 
    }

    @PutMapping("/{userId}")
    UserResponse updateUser(@PathVariable("userId") String userId, @RequestBody UserUpdateRequest req){
        return service.updateUser(userId, req);
    }

    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable("userId") String userId){
        service.deleteUser(userId);
        return("user has been deleted!");
    }
}
