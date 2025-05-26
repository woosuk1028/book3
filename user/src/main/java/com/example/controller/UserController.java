package com.example.controller;

import com.example.domain.entity.User;
import com.example.dto.LoginDto;
import com.example.dto.UserDto;
import com.example.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> signUser(@RequestBody @Valid UserDto dto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            return ResponseEntity.badRequest().body(errorMessage);
        }

        User saved = userService.save(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMyInfo(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok("Hello, " + username + "!");
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> getUserIdByUsername(@PathVariable String username) {
        Long userId = userService.getUserIdByUsername(username);
        return ResponseEntity.ok(userId);
    }
}
