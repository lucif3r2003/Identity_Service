package com.identity.Identity_Service.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.identity.Identity_Service.entity.User;

public interface UserRepository extends JpaRepository<User, String>{
    boolean existByUsername(String username);
}
