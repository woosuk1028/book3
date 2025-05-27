package com.example.controller;

import com.example.domain.entity.Book;
import com.example.dto.BookDto;
import com.example.service.BookService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final RestTemplate restTemplate;

    @Value("${user-service.base-url}")
    private String userServiceBaseUrl;

    @PostMapping
    public ResponseEntity<?> createBook(@RequestBody @Valid BookDto dto, BindingResult bindingResult, HttpServletRequest request) {
        if(bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            return ResponseEntity.badRequest().body(errorMessage);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

    // JWT 토큰을 Authorization 헤더로 추출
        String token = request.getHeader("Authorization");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Long> response = restTemplate.exchange(
            userServiceBaseUrl + "/api/user/username/" + username,
            HttpMethod.GET,
            entity,
            Long.class
        );

        Long userId = response.getBody();
        Book saved = bookService.save(dto, userId);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Book>> getList() {
        return ResponseEntity.ok(bookService.findAll());
    }

}
