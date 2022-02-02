package com.example.springbootcrud.service;

import com.example.springbootcrud.exceptions.BookNotFoundExceptions;
import com.example.springbootcrud.model.Book;

import java.util.List;

public interface CrudService {
    List<Book> findAll();
    Book findById(Long id) throws BookNotFoundExceptions;
    void create(Book book);
    void delete(Long id);
    void update(Book book);
}
