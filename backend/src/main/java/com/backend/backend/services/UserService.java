package com.backend.backend.services;

import com.backend.backend.dto.UserFlagUpdateRequest;
import com.backend.backend.entities.User;
import com.backend.backend.entities.UserStatus;
import com.backend.backend.repositories.UserRepository;
import com.backend.backend.specifications.UserSpecifications;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers(String name, String phone, UserStatus status) {
        Specification<User> spec = null;

        if (name != null && !name.isBlank()) {
            spec = UserSpecifications.hasNameLike(name);
        }

        if (phone != null && !phone.isBlank()) {
            spec = (spec == null)
                    ? UserSpecifications.hasPhoneLike(phone)
                    : spec.and(UserSpecifications.hasPhoneLike(phone));
        }

        if (status != null) {
            spec = (spec == null)
                    ? UserSpecifications.hasStatus(status)
                    : spec.and(UserSpecifications.hasStatus(status));
        }

        return userRepository.findAll(spec);
    }

    public User updateUserStatus(UUID userId, UserStatus status) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setStatus(status);

        return userRepository.save(user);
    }

    public User updateUserFlags(UUID userId, UserFlagUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getHighRisk() != null) {
            user.setHighRisk(request.getHighRisk());
        }

        if (request.getVerified() != null) {
            user.setVerified(request.getVerified());
        }

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
