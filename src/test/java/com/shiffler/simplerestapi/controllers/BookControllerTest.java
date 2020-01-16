package com.shiffler.simplerestapi.controllers;

import com.shiffler.simplerestapi.entities.Book;
import com.shiffler.simplerestapi.exceptions.BadDataException;
import com.shiffler.simplerestapi.exceptions.ItemNotFoundException;
import com.shiffler.simplerestapi.services.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {


    @Mock
    BookService bookService;

    @Mock
    Book book;

    @InjectMocks
    BookController bookController;

    @Test
    void findAllBooks() {
        bookController.findAllBooks();
        verify(bookService).findAllBooks();
    }

    @Test
    void findBookById() throws ItemNotFoundException {
        bookController.findBookById(5L);
        verify(bookService).findBookById(5L);
    }

    @Test
    void addBook() {
        bookController.addBook(book);
        verify(bookService).addBook(book);
    }

    @Test
    void updateBook() throws BadDataException, ItemNotFoundException {
        bookController.updateBook(book,5L);
        verify(bookService).updateBook(book,5L);
    }

    @Test
    void deleteBookById() throws BadDataException, ItemNotFoundException {
        bookController.deleteBookById(5L);
        verify(bookService).deleteBookById(5L);

    }
}