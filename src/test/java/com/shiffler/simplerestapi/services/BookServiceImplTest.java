package com.shiffler.simplerestapi.services;

import com.shiffler.simplerestapi.entities.Book;
import com.shiffler.simplerestapi.exceptions.BadDataException;
import com.shiffler.simplerestapi.exceptions.ItemNotFoundException;
import com.shiffler.simplerestapi.repositories.BookRepository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
     BookRepository bookRepository;

    @InjectMocks
    BookServiceImpl bookServiceImpl;

    @Mock
    Book book;

    static Book goodBook;

    static Book badBook;

    @BeforeAll
    static void init() {

        //Create a properly initialized book and one that is malformed
        goodBook = new Book("Pilgrim's Progress","John Bunyan",500);
        goodBook.setId(1L);

        badBook = new Book("Pilgrim's Progress","John Bunyan",500);
    }

    @Test
    void findAllBooks() {
        bookServiceImpl.findAllBooks();
        verify(bookRepository).findAll();
    }

    @Test
    void addBook() {
        bookServiceImpl.addBook(book);
        verify(bookRepository).save(book);
    }

    @Test
    void findBookById() throws ItemNotFoundException {

        //Setup the results of the retrieving books from the repository
        when(bookRepository.findById(1L)).thenReturn(Optional.of(goodBook));
        when(bookRepository.findById(2L)).thenReturn(Optional.empty());

        //Retrieve a book and verify the results
        Book fBook = bookServiceImpl.findBookById(1L);
        assertEquals("John Bunyan", fBook.getAuthor());
        assertThrows(ItemNotFoundException.class, () -> bookServiceImpl.findBookById(2L));
    }

    @Test
    void updateBook() throws BadDataException, ItemNotFoundException {

        //Test for a malformed book
        assertThrows(BadDataException.class, () -> bookServiceImpl.updateBook(badBook, 5L));

        //Test for a bad id value being passed in
        assertThrows(BadDataException.class, () -> bookServiceImpl.updateBook(goodBook, null));

        //Test for the book object id not matching the id
        assertThrows(BadDataException.class, () -> bookServiceImpl.updateBook(goodBook, 5L));

        //Test that saving the book is called
        when(bookRepository.findById(1L)).thenReturn(Optional.of(goodBook));
        bookServiceImpl.updateBook(goodBook,1L);
        verify(bookRepository).save(goodBook);

        //Test that an exception is thrown if the book with the id isn't found
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ItemNotFoundException.class, () -> bookServiceImpl.updateBook(goodBook, 1L));
    }

    @Test
    void deleteBookById() throws ItemNotFoundException {

        //If the book doesn't exist make sure an exception is thrown
        when(bookRepository.existsById(5L)).thenReturn(false);
        assertThrows(ItemNotFoundException.class, () -> bookServiceImpl.deleteBookById(5L));

        //Make sure the book is deleted when it exists
        when(bookRepository.existsById(5L)).thenReturn(true);
        bookServiceImpl.deleteBookById(5L);
        verify(bookRepository).deleteById(5L);

    }
}