package com.example.springbootcrud.web;

import com.example.springbootcrud.exceptions.BookNotFoundExceptions;
import com.example.springbootcrud.model.Book;
import com.example.springbootcrud.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class CrudRestController {
    private CrudService service;

    @Autowired
    public void setService(CrudService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/book")
    public ResponseEntity<List<Book>> getBooks(){
        try {
            List<Book> books = service.findAll();
            return ResponseEntity.ok(books);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Long id){
        try {
            Book book = service.findById(id);
            return ResponseEntity.ok(book);
        } catch (BookNotFoundExceptions e){
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/book")
    public ResponseEntity<URI> createBook(@RequestBody Book book) {
        try {
            service.create(book);
            Long id = book.getId();
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
            return ResponseEntity.created(location).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/book/{id}")
    public ResponseEntity<?> updateBook(@PathVariable("id") Long id, @RequestBody Book book) {
        try {
            Book b = service.findById(id);
            b.setName(book.getName());
            b.setAge(book.getAge());
            b.setGender(book.getGender());

            service.update(b);
            return ResponseEntity.ok(b);
        } catch (BookNotFoundExceptions e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/book/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable("id") Long id){
        try {
            service.delete(id);
            return ResponseEntity.ok(id);
        } catch (BookNotFoundExceptions e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
