package com.example.demo.student;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    // @Bean
    // public InMemoryUserDetailsManager userDetailsService() {
    //     UserDetails user1 = User.builder()
    //         .username("student")
    //         .password(passwordEncoder().encode("password"))
    //         .roles("STUDENT")
    //         .build();
    //     UserDetails user2 = User.builder()
    //         .username("teacher")
    //         .password(passwordEncoder().encode("password"))
    //         .roles("TEACHER")
    //         .build();
    //     return new InMemoryUserDetailsManager(user1, user2);
    // }

    // Authentication method
    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager() {
        return new JdbcUserDetailsManager();
    }

    // Authroization method
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests ->
            authorizeRequests
            .requestMatchers("studentList/**").hasRole("TEACHER")
            .requestMatchers("student/**").hasAnyRole("STUDENT", "TEACHER")
            .requestMatchers("/**").permitAll()
            .anyRequest().authenticated()
        )
        .formLogin(withDefaults());

    return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}