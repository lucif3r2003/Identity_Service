package com.identity.Identity_Service.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.identity.Identity_Service.dto.response.APIResponse;

@ControllerAdvice
public class GlobalExceptionHandler {


   @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<APIResponse> handlingAccessDenied(AccessDeniedException e){
        ErrorCode code = ErrorCode.ACCESS_DENIED;
        APIResponse res = new APIResponse() ;
        res.setCode(code.getCode());
        res.setMessage(code.getMessages());
        return ResponseEntity.badRequest().body(res);
    }

   @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<APIResponse> handlingRuntimeException(RuntimeException e){
        APIResponse res = new APIResponse();
        res.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        res.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessages());
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
    
        return ResponseEntity.status(code.getStatus()).body(APIResponse.builder()
                            .code(code.getCode())
                            .message(code.getMessages())
                            .build()
            );
    }

}


