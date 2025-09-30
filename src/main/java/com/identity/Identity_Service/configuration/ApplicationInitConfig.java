package com.identity.Identity_Service.configuration;

import java.util.HashSet;

import com.identity.Identity_Service.entity.User;
import com.identity.Identity_Service.enums.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.identity.Identity_Service.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;
@Slf4j

@Configuration
public class ApplicationInitConfig{
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner runner(UserRepository repo){
        return args -> {
            if(repo.findByUsername("admin").isEmpty()){
                var roles = new HashSet<String>();
                roles.add(Role.ADMIN.name());

                User user = User.builder()
                            .username("admin")
                            .password(passwordEncoder.encode("admin"))
                            .roles(roles)
                            .build();
                repo.save(user);
                log.warn("admin user has been created with default password: admin, please change it!");
            }
        };
    }
}
