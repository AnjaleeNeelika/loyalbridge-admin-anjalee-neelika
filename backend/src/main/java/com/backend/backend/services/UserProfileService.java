package com.backend.backend.services;

import com.backend.backend.entities.PointTransaction;
import com.backend.backend.entities.User;
import com.backend.backend.repositories.PointTransactionRepository;
import com.backend.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserProfileService {
    private final UserRepository userRepository;
    private final PointTransactionRepository pointTransactionRepository;

    public UserProfileService(UserRepository userRepository, PointTransactionRepository pointTransactionRepository) {
        this.userRepository = userRepository;
        this.pointTransactionRepository = pointTransactionRepository;
    }

    public User getUserProfile(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<PointTransaction> getUserPointHistory(UUID userId) {
        return pointTransactionRepository.findAllByUserId(userId);
    }
}
