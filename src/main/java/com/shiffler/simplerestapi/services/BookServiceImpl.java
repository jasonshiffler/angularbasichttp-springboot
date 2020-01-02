package com.shiffler.simplerestapi.services;

import com.shiffler.simplerestapi.entities.Book;
import com.shiffler.simplerestapi.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Iterable<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public void addBook(Book book) {
        bookRepository.save(book);
    }
}
