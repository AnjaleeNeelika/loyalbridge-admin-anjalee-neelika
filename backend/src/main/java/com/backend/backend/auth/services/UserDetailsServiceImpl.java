package com.backend.backend.auth.services;

import com.backend.backend.auth.entities.Admin;
import com.backend.backend.auth.repositories.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = userDetailsRepository.findByEmail(username);

        if (admin == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return admin;
    }
}
