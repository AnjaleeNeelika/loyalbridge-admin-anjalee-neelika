package com.backend.backend.services;

import com.backend.backend.entities.User;
import com.backend.backend.entities.UserStatus;
import com.backend.backend.repositories.UserRepository;
import com.backend.backend.specifications.UserSpecifications;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
