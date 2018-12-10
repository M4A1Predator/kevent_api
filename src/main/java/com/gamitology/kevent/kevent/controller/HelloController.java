package com.gamitology.kevent.kevent.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/hello")
@RestController
public class HelloController {

    @GetMapping
    public String hello() {
        return "hello";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('admin')")
    public String helloAdmin(){return "hello admin";}
}
