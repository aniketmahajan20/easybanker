package com.example.demo.easybanker.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.easybanker.entity.User;
import java.util.List;
import com.example.demo.easybanker.repository.UserRepository;

@Configuration
public class UserConfig {

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Bean
    CommandLineRunner commandLineRunne(
        UserRepository repository) {
        return args -> {
            User aniket = new User(
                "Aniket",
                "Mahajan",
                "aniketmahajan20@gmail.com",
                bCryptPasswordEncoder.encode("a"),
                List.of("ROLE_USER", "ROLE_ADMIN")
            );
            User abhishek = new User(
                "Abhishek",
                "Mahajan",
                "abhishekmahajan19@ymail.com",
                bCryptPasswordEncoder.encode("abhishek123"),
                List.of("ROLE_USER")
            );
            repository.saveAll(
                List.of(aniket, abhishek)
            );
            repository.enableUser("aniketmahajan20@gmail.com");
            repository.enableUser("abhishekmahajan19@ymail.com");
        };
    }
}
