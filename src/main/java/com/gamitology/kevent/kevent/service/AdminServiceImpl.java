package com.gamitology.kevent.kevent.service;

import com.gamitology.kevent.kevent.constant.UserRole;
import com.gamitology.kevent.kevent.dto.UserRegisDto;
import com.gamitology.kevent.kevent.entity.Role;
import com.gamitology.kevent.kevent.entity.User;
import com.gamitology.kevent.kevent.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User registerAdmin(UserRegisDto userDto) throws Exception {
        User user = new User();

        // Check duplicate name
        if (usernameExist(userDto.getUserName())) {
            throw new Exception("username is already used");
        }

        // Set username and password
        user.setUsername(userDto.getUserName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // Assign role
        List<Role> roles = new ArrayList<>();
        Role adminRole = new Role();
        adminRole.setId(UserRole.ADMIN.getId());
        roles.add(adminRole);
        user.setRoles(roles);

        // Save
        User savedUser = userRepository.save(user);

        return savedUser;
    }

    private boolean usernameExist(String username) {
        User user = userRepository.findByUsername(username);
        return user != null;
    }
}
