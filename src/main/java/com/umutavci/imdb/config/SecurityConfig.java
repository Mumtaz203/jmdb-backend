package com.umutavci.imdb.config;

import com.umutavci.imdb.application.services.UserService;
import com.umutavci.imdb.infrastructure.persistence.adapters.CustomUserDetailsAdapter;
import com.umutavci.imdb.infrastructure.persistence.adapters.UserAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private final CustomUserDetailsAdapter customUserDetailsAdapter;
    public SecurityConfig(CustomUserDetailsAdapter customUserDetailsAdapter) {
        this.customUserDetailsAdapter = customUserDetailsAdapter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(UserAdapter userAdapter) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsAdapter);
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize 
                        .requestMatchers("/graphql/**", "/graphiql/**").permitAll() 
                        .anyRequest().authenticated() 
                )
                .formLogin(Customizer.withDefaults()) 
                .httpBasic(Customizer.withDefaults()); 

        return http.build();
    }

}
