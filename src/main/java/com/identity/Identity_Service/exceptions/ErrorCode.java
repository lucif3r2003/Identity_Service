package com.identity.Identity_Service.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode{
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR),
    USERNAME_EXISTS(1001, "Username already exists", HttpStatus.BAD_REQUEST),
    USER_NOT_EXIST(1002, "user not exist" , HttpStatus.BAD_REQUEST),
    LOGIN_FAILED(1003, "login failed" , HttpStatus.UNAUTHORIZED),
    INVALID_USERNAME(1004, "Invalid username" , HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1005, "Invalid password" , HttpStatus.BAD_REQUEST),
    ACCESS_DENIED(1006, "Access denied", HttpStatus.FORBIDDEN)
    ;

    private int code;
    private String messages;
    private HttpStatus status;
	ErrorCode(int code, String messages, HttpStatus status) {
		this.code = code;
		this.messages = messages;
        this.status = status;
	}

	
} 
