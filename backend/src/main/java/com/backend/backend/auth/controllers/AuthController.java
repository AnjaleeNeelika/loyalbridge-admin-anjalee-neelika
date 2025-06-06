package com.backend.backend.auth.controllers;

import com.backend.backend.auth.config.JWTTokenHelper;
import com.backend.backend.auth.dto.LoginRequest;
import com.backend.backend.auth.dto.UserToken;
import com.backend.backend.auth.entities.Admin;
import com.backend.backend.auth.helper.PasswordCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JWTTokenHelper jwtTokenHelper;

    @PostMapping("/login")
    public ResponseEntity<UserToken> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Email domain check
            if (!loginRequest.getEmail().endsWith("@loyalbridge.io")) {
                return ResponseEntity.badRequest().body(new UserToken("Only @loyalbridge.io emails are allowed"));
            }

            // Checking password strength
//            if (!PasswordCheck.isPasswordStrong(loginRequest.getPassword())) {
//                return ResponseEntity.badRequest().body(new UserToken("Password is not strong enough"));
//            }

            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
            );

            // Generate JWT if authenticated
            if (authentication.isAuthenticated()) {
                Admin admin = (Admin) authentication.getPrincipal();
                String token = jwtTokenHelper.generateToken(admin.getEmail());

                return ResponseEntity.ok(new UserToken(token));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new UserToken("Authentication failed"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new UserToken("Invalid email or password"));
        }
    }
}
