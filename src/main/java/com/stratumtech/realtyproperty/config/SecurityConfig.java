package com.stratumtech.realtyproperty.config;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;

import static jakarta.servlet.DispatcherType.ERROR;
import static jakarta.servlet.DispatcherType.FORWARD;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.Customizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.stratumtech.realtyproperty.config.filter.FindUserCredentialsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final FindUserCredentialsFilter findUserRoleFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(findUserRoleFilter, UsernamePasswordAuthenticationFilter.class)
                .cors(Customizer.withDefaults())
                .csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .dispatcherTypeMatchers(FORWARD, ERROR).permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/api/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/properties/{uuid}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/properties/**").hasRole("AGENT")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/properties/{uuid}").hasRole("AGENT")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/properties/{uuid}").hasRole("AGENT")
                        .anyRequest().denyAll()
                ).sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }
}
