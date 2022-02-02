package com.example.springbootcrud.service;

import com.example.springbootcrud.dao.BookRepository;
import com.example.springbootcrud.exceptions.BookNotFoundExceptions;
import com.example.springbootcrud.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrudServiceImpl implements CrudService{
    private BookRepository repository;

    @Autowired
    public void setRepository(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Book> findAll() {
        return repository.findAll();
    }

    @Override
    public Book findById(Long id) throws BookNotFoundExceptions {
        Book book = repository.findById(id).get();
        if(book == null)
            throw new BookNotFoundExceptions("Book not fount with this id:" + id);
        return book;
    }

    @Override
    public void create(Book book) {
        repository.save(book);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void update(Book book) {
        Book b = repository.findById(book.getId()).get();
        b.setName(book.getName());
        b.setAge(book.getAge());
        b.setGender(book.getGender());
    }
}
