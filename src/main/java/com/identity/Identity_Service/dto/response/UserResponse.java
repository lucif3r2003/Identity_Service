package com.identity.Identity_Service.dto.response;

import java.time.LocalDate;
import java.util.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class UserResponse{
    
    String id;
    String username;
    String firstName;
    String lastName;
    LocalDate dob;
    Set<String> roles;  

}
