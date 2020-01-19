package com.gamitology.kevent.kevent.service;

import com.gamitology.kevent.kevent.constant.UserRole;
import com.gamitology.kevent.kevent.dto.UserRegisDto;
import com.gamitology.kevent.kevent.entity.Role;
import com.gamitology.kevent.kevent.entity.User;
import com.gamitology.kevent.kevent.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}
