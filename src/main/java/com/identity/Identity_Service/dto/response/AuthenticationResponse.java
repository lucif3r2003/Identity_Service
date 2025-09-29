package com.identity.Identity_Service.dto.response;

import lombok.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class AuthenticationResponse{
    boolean result;
    String token;
}
