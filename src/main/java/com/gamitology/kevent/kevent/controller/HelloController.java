package com.gamitology.kevent.kevent.controller;

import com.gamitology.kevent.kevent.annotation.TestAnnotation;
import com.gamitology.kevent.kevent.model.CustomPrincipal;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.security.Principal;

@RequestMapping("/hello")
@RestController
public class HelloController {

    @GetMapping
    @TestAnnotation
    public String hello(CustomPrincipal principal) {
        Object p = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "hello";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('admin')")
    public String helloAdmin(){return "hello admin";}

    @GetMapping("/flux")
    @PreAuthorize("hasAuthority('admin')")
    public Mono<Object> getMono() {
        return Mono.just(new String("test mono"));
    }
}
