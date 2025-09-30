package com.identity.Identity_Service.configuration;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import com.identity.Identity_Service.enums.Role;

import org.springframework.security.oauth2.jose.jws.MacAlgorithm;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final String[] PUBLIC_ENFPOINT ={"/users", "/auth/login", "auth/introspect"} ;

    @Value("${jwt.signerKey}")
    private String signerKey;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests(request -> 
                                    request.requestMatchers(HttpMethod.POST, PUBLIC_ENFPOINT).permitAll()
                                    .requestMatchers(HttpMethod.GET, "/users").hasRole(Role.ADMIN.name())
                                    .anyRequest().authenticated());

        httpSecurity.oauth2ResourceServer(oauth2 ->
                                            oauth2.jwt(jwtConfig -> 
                                            jwtConfig.decoder(jwtDecoders()))
        
        );

        httpSecurity.csrf(AbstractHttpConfigurer:: disable);
        return httpSecurity.build();
    }

    @Bean
    NimbusJwtDecoder jwtDecoders(){
        SecretKeySpec secrtKey = new SecretKeySpec(signerKey.getBytes(), "HS512");
        return NimbusJwtDecoder
                .withSecretKey(secrtKey)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    JwtAuthenticationConverter converter(){
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return converter;
    }
}
