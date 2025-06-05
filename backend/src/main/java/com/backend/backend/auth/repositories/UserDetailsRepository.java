package com.backend.backend.auth.repositories;

import com.backend.backend.auth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
