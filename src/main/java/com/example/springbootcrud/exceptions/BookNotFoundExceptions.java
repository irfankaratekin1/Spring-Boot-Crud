package com.example.springbootcrud.exceptions;

public class BookNotFoundExceptions extends RuntimeException{
    public BookNotFoundExceptions(String message){
        super(message);
    }
}