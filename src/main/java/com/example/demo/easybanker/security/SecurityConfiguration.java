package com.example.demo.easybanker.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.easybanker.filters.JwtRequestFilter;

// import com.example.demo.student.filters.JwtRequestFilter;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;

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

    // Data Source Bean
    // Default Data Source pointing to POSTGRESQL
    // @Autowired
    // DataSource datasource;

    // Authentication method
    // @Bean
    // public JdbcUserDetailsManager jdbcUserDetailsManager() {
    //     JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
    //     jdbcUserDetailsManager.setDataSource(datasource);
    //     jdbcUserDetailsManager.setUsersByUsernameQuery("select username, password, enabled "
    //         + "from users "
    //         + "where username = ?");
    //     jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select username, authority "
    //         + "from authorities "
    //         + "where username = ?");
    //     return jdbcUserDetailsManager;
    // }

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    public UserDetailsService userDetailsService() {
        return userDetailsService;
    }
    
    // Authroization method
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
        .csrf(
            csrf -> csrf
            .ignoringRequestMatchers("/authenticate")
            // .ignoringRequestMatchers("/registration")
        )
            .authorizeHttpRequests(authorizeRequests ->
            authorizeRequests
            .requestMatchers("/welcome").hasAuthority("ADMIM")
            .requestMatchers("/authenticate").permitAll()
            .requestMatchers("/registration").permitAll()
            .requestMatchers("/").permitAll()
            .anyRequest().authenticated()
        )
        .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // .formLogin(withDefaults());
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}