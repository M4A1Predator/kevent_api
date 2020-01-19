package com.gamitology.kevent.kevent.controller;

import com.gamitology.kevent.kevent.dto.UserRegisDto;
import com.gamitology.kevent.kevent.service.AdminService;
import com.gamitology.kevent.kevent.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/admin")
@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/regis")
    public ResponseEntity createAdmin(@RequestBody UserRegisDto userDto) throws Exception {
        adminService.registerAdmin(userDto);
        return ResponseEntity.ok().build();
    }

}
