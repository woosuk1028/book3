package com.example.service;

import com.example.domain.entity.Book;
import com.example.dto.BookDto;
import com.example.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public Book save(BookDto dto, Long userId) {
        Book book = new Book();
        book.setUserId(userId);
        book.setTitle(dto.getTitle());
        book.setContent(dto.getContent());
        book.setAuthor(dto.getAuthor());
        book.setCreatedAt(LocalDateTime.now());
        return bookRepository.save(book);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book getDetail(Long id) {
        return bookRepository.findById(id)
                .orElse(new Book());
    }

    public boolean delete(Long id) {
        try {
            bookRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
