package com.identity.Identity_Service.configuration;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.identity.Identity_Service.dto.response.APIResponse;
import com.identity.Identity_Service.exceptions.ErrorCode;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        ErrorCode code = ErrorCode.LOGIN_FAILED;
        response.setStatus(code.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        APIResponse api = APIResponse.builder()
                        .code(code.getCode())
                        .message(code.getMessages())
                        .build();


        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(api));
        response.flushBuffer();

    }    
}
