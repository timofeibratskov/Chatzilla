package com.example.message_service.config;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.AntPathMatcher;
import org.springframework.context.annotation.Bean;
import com.example.message_service.security.JwtAuthFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/ws/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/index.html").permitAll()
                        .requestMatchers("/app.js").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore((request, response, chain) -> {
                    if (!(request instanceof HttpServletRequest)) {
                        chain.doFilter(request, response);
                        return;
                    }
                    HttpServletRequest httpRequest = (HttpServletRequest) request;
                    if (!antPathMatcher.match("/ws/**", httpRequest.getServletPath())) {
                        jwtAuthFilter.doFilter(httpRequest, response, chain);
                    } else {
                        chain.doFilter(request, response);
                    }
                }, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}