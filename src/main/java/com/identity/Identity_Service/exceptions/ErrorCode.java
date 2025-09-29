package com.identity.Identity_Service.exceptions;

public enum ErrorCode{
    USERNAME_EXISTS(1001, "Username already exists"),
    USER_NOT_EXIST(1002, "user not exist"),
    LOGIN_FAILED(1003, "login failed")
    ;

    private int code;
    private String messages;
	ErrorCode(int code, String messages) {
		this.code = code;
		this.messages = messages;
	}


	public int getCode() {
		return code;
	}
	public String getMessages() {
		return messages;
	}
	
} 
