package com.shiffler.simplerestapi.controllers;

import com.shiffler.simplerestapi.entities.Book;
import com.shiffler.simplerestapi.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class BookController {

    private BookService bookService;

    @Autowired
    BookController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public Iterable<Book> findAllBooks(){
        return bookService.findAllBooks();
    }

    @PostMapping("/books")
    public void addBook(@RequestBody Book book){
        bookService.addBook(book);
    }

}
