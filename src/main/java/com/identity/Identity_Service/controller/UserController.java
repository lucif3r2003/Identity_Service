package com.identity.Identity_Service.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.identity.Identity_Service.service.UserService;

import jakarta.validation.*;


@RestController
@RequestMapping("/users")
public class UserController{
    @Autowired
    private UserService service;
    @PostMapping
    APIResponse<User> create(@RequestBody @Valid UserCreationRequest req){
        APIResponse<User> res = new APIResponse<User>();
        res.setResult(service.createRequest(req));

        return res;
    }

    @GetMapping
    List<User> getUser(){
        return service.getUser();
    }

    @GetMapping("/{userId}")
    User getUser(@PathVariable("userId") String userId){
       return service.getUser(userId); 
    }

    @PutMapping("/{userId}")
    User updateUser(@PathVariable("userId") String userId, @RequestBody UserUpdateRequest req){
        return service.updateUser(userId, req);
    }

    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable("userId") String userId){
        service.deleteUser(userId);
        return("user has been deleted!");
    }
}
