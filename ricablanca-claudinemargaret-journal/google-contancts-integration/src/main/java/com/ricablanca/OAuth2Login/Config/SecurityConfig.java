package com.ricablanca.OAuth2Login.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                            .requestMatchers("/", "/error").permitAll()
                            .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                    .defaultSuccessUrl("/contacts", true)
                )
                .logout(logout -> logout
                    .logoutSuccessUrl("/")
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                )
                .formLogin(form -> form
                    .defaultSuccessUrl("/contacts", true)
                )
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }
}