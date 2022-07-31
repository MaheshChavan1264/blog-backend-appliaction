package com.blog.blogbackend.security;

import com.blog.blogbackend.exceptions.ResourceNotFoundException;
import com.blog.blogbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // loading user from database username
        return this.userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User", "Email: ", username));
    }
}
