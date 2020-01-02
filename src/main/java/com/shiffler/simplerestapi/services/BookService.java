package com.shiffler.simplerestapi.services;

import com.shiffler.simplerestapi.entities.Book;
import org.springframework.stereotype.Component;



@Component
public interface BookService {

    Iterable<Book> findAllBooks();
    void addBook(Book book);


}
