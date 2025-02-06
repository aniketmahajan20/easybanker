// package com.example.demo.student;

// import java.time.LocalDate;
// import java.time.Month;
// import java.util.List;

// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// @Configuration
// public class StudentConfig {
    
//     @Bean
//     CommandLineRunner commandLineRunner(
//         StudentRepository repository) {
//         return args -> {
//             Student aniket = new Student(
//                 "Aniket Mahajan", 
//                 LocalDate.of(2000, Month.JANUARY, 5),
//                 "aniketmahajan@gmail.com"
//             );
//             Student alex = new Student( 
//                 "Alex", 
//                 LocalDate.of(2001, Month.JANUARY, 5),
//                 "alex@alex.com"
//             );

//             repository.saveAll(
//                 List.of(aniket, alex)
//             );
//         };
//     }
// }
