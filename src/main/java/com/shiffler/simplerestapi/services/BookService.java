package com.shiffler.simplerestapi.services;

import com.shiffler.simplerestapi.entities.Book;
import com.shiffler.simplerestapi.exceptions.BookNotFoundException;
import org.springframework.stereotype.Component;


@Component
public interface BookService {

    Iterable<Book> findAllBooks();
    void addBook(Book book);
    Book findBookById(Long id) throws BookNotFoundException;
}
