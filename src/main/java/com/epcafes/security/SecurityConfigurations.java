package com.epcafes.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
    @Bean   
    public SecurityFilterChain SecurityFilterChain(HttpSecurity httpSecurity) throws Exception{
        //Configurações de role e autorização posteriormente vão aqui
        return httpSecurity
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(authorize->authorize
        .requestMatchers("/login").permitAll()
        .requestMatchers("/register").authenticated()
        .requestMatchers("/images/**").permitAll()
        .requestMatchers("/css/**").permitAll()
        .anyRequest().authenticated()
        )
        .formLogin(form->form
        .loginPage("/login").permitAll()
        .defaultSuccessUrl("/epcafes", true)
        )
        .build();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean

    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
