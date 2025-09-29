package com.identity.Identity_Service.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.identity.Identity_Service.dto.request.*;
import com.identity.Identity_Service.dto.response.*;
import com.identity.Identity_Service.service.*;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService service;
    @PostMapping("/login")
    APIResponse<AuthenticationResponse> auth(@RequestBody AuthenticationRequest req){
        var result = service.auth(req);
        return APIResponse.<AuthenticationResponse>builder()
                .result(result)
                    .build();    
    }    

    @PostMapping("/introspect")
    APIResponse<IntrospectResponse> introspec(@RequestBody IntrospectRequest req){
        var result = service.introspect(req);
        return APIResponse.<IntrospectResponse>builder().result(result).build();
    }

}
