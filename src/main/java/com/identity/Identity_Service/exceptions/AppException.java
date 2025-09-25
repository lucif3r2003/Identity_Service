package com.identity.Identity_Service.exceptions;
public class AppException extends RuntimeException{

    

    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessages());
        this.errorCode = errorCode;
	}

	private ErrorCode errorCode;

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

}

