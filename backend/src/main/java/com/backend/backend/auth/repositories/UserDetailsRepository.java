package com.backend.backend.auth.repositories;

import com.backend.backend.auth.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepository extends JpaRepository<Admin, Long> {
    Admin findByEmail(String email);
    boolean existsByEmail(String email);
}
