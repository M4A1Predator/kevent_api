package com.gamitology.kevent.kevent.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AppAuthController {

    @PostMapping("/verify")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('user')")
    public ResponseEntity checkToken() {
        return ResponseEntity.ok().build();
    }

}
