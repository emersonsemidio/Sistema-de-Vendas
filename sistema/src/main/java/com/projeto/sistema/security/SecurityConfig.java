package com.projeto.sistema.security;

import org.springframework.security.config.Customizer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                // Permitir apenas endpoints públicos específicos
                .requestMatchers("/login", "/public/**", "/css/**", "/js/**").permitAll()
                
                // ✅ PROTEGER endpoints de API
                .requestMatchers(HttpMethod.POST, "/clientes").authenticated()
                .requestMatchers(HttpMethod.PUT, "/clientes/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/clientes/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/clientes/**").authenticated()
                
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .permitAll()
            )
            .httpBasic(Customizer.withDefaults()) // ← Para API retornar 401
            .logout(logout -> logout.permitAll());
        
        return http.build();
    }
}