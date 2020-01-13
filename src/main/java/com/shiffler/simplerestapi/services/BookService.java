package com.shiffler.simplerestapi.services;

import com.shiffler.simplerestapi.entities.Book;
import com.shiffler.simplerestapi.exceptions.BadDataException;
import com.shiffler.simplerestapi.exceptions.ItemNotFoundException;
import org.springframework.stereotype.Component;


@Component
public interface BookService {

    Iterable<Book> findAllBooks();
    void addBook(Book book);
    Book findBookById(Long id) throws ItemNotFoundException;
    void updateBook(Book book, Long id) throws ItemNotFoundException, BadDataException;
    void deleteBook(Long id) throws ItemNotFoundException;;
}
