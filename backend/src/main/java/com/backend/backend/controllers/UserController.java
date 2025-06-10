package com.backend.backend.controllers;

import com.backend.backend.dto.UserFlagUpdateRequest;
import com.backend.backend.dto.UserStateUpdateRequest;
import com.backend.backend.entities.PointTransaction;
import com.backend.backend.entities.User;
import com.backend.backend.entities.UserStatus;
import com.backend.backend.services.UserProfileService;
import com.backend.backend.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/users")
public class UserController {
    private final UserService userService;
    private final UserProfileService userProfileService;

    public UserController(UserService userService, UserProfileService userProfileService) {
        this.userService = userService;
        this.userProfileService = userProfileService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) UserStatus status
    ) {
        List<User> users = userService.getUsers(name, phone, status);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserProfile(@PathVariable UUID userId) {
        User user = userProfileService.getUserProfile(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{userId}/points")
    public ResponseEntity<List<PointTransaction>> getPointHistory(@PathVariable UUID userId) {
        List<PointTransaction> history = userProfileService.getUserPointHistory(userId);
        return ResponseEntity.ok(history);
    }

    @PutMapping("/{userId}/status")
    public ResponseEntity<User> updateUserStatus(
            @PathVariable UUID userId,
            @RequestBody UserStateUpdateRequest request
    ) {
        User updatedUser = userService.updateUserStatus(userId, request.getStatus());

        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/{userId}/flag")
    public ResponseEntity<User> updateUserFlags(
            @PathVariable UUID userId,
            @RequestBody UserFlagUpdateRequest request
    ) {
        User updatedUser = userService.updateUserFlags(userId, request);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/export")
    public void ExportUsersToCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=users.csv");

        List<User> users = userService.getAllUsers();

        PrintWriter writer = response.getWriter();
        writeCsv(users, writer);
        writer.flush();
    }

    private void writeCsv(List<User> users, PrintWriter writer) {
        // Header
        writer.println("ID, Name, Email, Phone, Status, Verified, High Risk");

        // Data rows
        for (User user : users) {
            writer.printf("%s, %s %s, %s, %s, %b, %b%n",
                    user.getId().toString(),    // converted the id from uuid to string
                    user.getName(),
                    user.getEmail(),
                    user.getPhone(),
                    user.getStatus(),
                    user.isVerified(),
                    user.isHighRisk()
            );
        }
    }
}
