package com.backend.backend.config;

import com.backend.backend.auth.entities.Admin;
import com.backend.backend.auth.entities.Role;
import com.backend.backend.auth.repositories.UserDetailsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner seedAdmin(UserDetailsRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (!userRepository.existsByEmail("admin@loyalbridge.io")) {
                String encodedPassword = passwordEncoder.encode("Strong@Pass123");
                Admin user = Admin.builder()
                        .email("admin@loyalbridge.io")
                        .password(encodedPassword)
                        .role(Role.SUPERADMIN)
                        .build();
                userRepository.save(user);
                System.out.println("✅ Seeded admin: admin@loyalbridge.io / Strong@Pass123");
            }
        };
    }
}
