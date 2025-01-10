package com.bookmyflight.security.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/user")
public class HelloController {


@PostMapping("/testUser")
public ResponseEntity<String> seyHello() {
    return ResponseEntity.ok("Hello from HelloController!");
}
//    public String greet(HttpServletRequest request) {
//        return "Welcome to Telusko "+request.getSession().getId();
//    }


}
