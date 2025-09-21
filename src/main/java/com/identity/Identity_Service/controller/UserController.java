package com.identity.Identity_Service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.identity.Identity_Service.entity.User;

import com.identity.Identity_Service.dto.request.UserCreationRequest;
import com.identity.Identity_Service.service.UserService;


@Controller

public class UserController{
    @Autowired
    private UserService service;
    @PostMapping("/users")
    User create(@RequestBody UserCreationRequest req){
        return service.createRequest(req);
    }
}
