package com.identity.Identity_Service.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.identity.Identity_Service.dto.response.APIResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
   @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<APIResponse> handlingRuntimeException(RuntimeException e){
        APIResponse res = new APIResponse();
        res.setCode(1001);
        res.setMessage(e.getMessage());
        return ResponseEntity.badRequest().body(res);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<APIResponse> handlingValidation(MethodArgumentNotValidException e){
        String enumKey = e.getFieldError().getDefaultMessage();
        ErrorCode code = ErrorCode.valueOf(enumKey);
        APIResponse res = new APIResponse();
        res.setCode(code.getCode());
        res.setMessage(code.getMessages());

        return ResponseEntity.badRequest().body(res);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<APIResponse> handlingAppException(AppException e){
        ErrorCode code = e.getErrorCode();
        APIResponse res = new APIResponse();
        res.setCode(code.getCode());
        res.setMessage(code.getMessages());
        return ResponseEntity.badRequest().body(res);
    }
}


