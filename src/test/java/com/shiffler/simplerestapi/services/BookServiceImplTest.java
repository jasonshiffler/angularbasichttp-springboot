package com.shiffler.simplerestapi.services;

import com.shiffler.simplerestapi.entities.Book;
import com.shiffler.simplerestapi.exceptions.BadDataException;
import com.shiffler.simplerestapi.exceptions.ItemNotFoundException;
import com.shiffler.simplerestapi.repositories.BookRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
     BookRepository bookRepository;

    @InjectMocks
    BookServiceImpl bookServiceImpl;


    Book book1,book2;

    List<Book> bookList;

    @BeforeEach
    void init() {
        book1 = new Book("Moby Dick","Herman Melville",650);
        book1.setId(1L);

        book2 = new Book("Pilgrim's Progress","John Bunyan",400);
        book2.setId(2L);

        bookList = new ArrayList<>();
        bookList.add(book1);
        bookList.add(book2);
    }

    @Test
    @DisplayName("Find All Books")
    void findAllBooks() {

        List<Book> results = new ArrayList<>();

        //Given
        given(bookRepository.findAll()).willReturn(bookList);

        //When
        Iterable<Book> bookIt = bookServiceImpl.findAllBooks();
        for (Book book : bookIt){
            results.add(book);
        }

        //Then
        then(bookRepository).should().findAll();
        assertEquals(results.size(),2);
    }

    @Test
    @DisplayName("Add a Book")
    void addBook() {

        //When
        bookServiceImpl.addBook(book1);

        //Then
        then(bookRepository).should().save(any(Book.class));
    }

    @Test
    @DisplayName("Successful Find Book By Id")
    void findBookByIdSuccess() throws ItemNotFoundException {

        //Given
        given(bookRepository.findById(any())).willReturn(Optional.of(book1));

        //When
        Book foundBook = bookServiceImpl.findBookById(1L);

        //Then
        then(bookRepository).should().findById(any(Long.class));
        assertEquals(foundBook,book1);
    }

    @Test
    @DisplayName("Failed Find Book By Id")
    void findBookByIdFail() throws ItemNotFoundException {

        //Given
        given(bookRepository.findById(any())).willReturn(Optional.empty());

        // When/Then
        assertThrows(ItemNotFoundException.class,()->bookServiceImpl.findBookById(1L));

        //Then
        then(bookRepository).should().findById(any(Long.class));
    }


    @Test
    @DisplayName("Failed Update Book Null Id")
    void updateBookNullId(){
        // When/Then
        assertThrows(BadDataException.class, () -> bookServiceImpl.updateBook(book1, null));
    }

    @Test
    @DisplayName("Failed Update Book Book Id - Id Mismatch")
    void updateBookBookIdMismatch(){
        // When/Then
        assertThrows(BadDataException.class, () -> bookServiceImpl.updateBook(book1, 2L));
    }

    @Test
    @DisplayName("Failed Update Book Id Not Found")
    void updateBookBookIdNotFound(){

        // Given
        given(bookRepository.findById(any())).willReturn(Optional.empty());

        // When/Then
        assertThrows(ItemNotFoundException.class, () -> bookServiceImpl.updateBook(book1, 1L));
    }

    @Test
    @DisplayName("Failed Update Malformed Book")
    void updateBookMalformedBook(){

        // Given
        book1.setId(null);

        // When/Then
        assertThrows(BadDataException.class, () -> bookServiceImpl.updateBook(book1, 1L));
    }


    @Test
    @DisplayName("Successful Book Update")
    void updateBookSuccess() throws BadDataException, ItemNotFoundException {

       //Given
       when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));

       //When
       bookServiceImpl.updateBook(book1,1L);

       //Then
       verify(bookRepository).save(book1);
    }

    @Test
    @DisplayName("Delete Book By Id Success")
    void deleteBookByIdSuccess() throws ItemNotFoundException {

        //Given
        given(bookRepository.existsById(any(Long.class))).willReturn(true);

        //When
        bookServiceImpl.deleteBookById(1L);

        //Then
        then(bookRepository).should().deleteById((any(Long.class)));
    }

    @Test
    @DisplayName("Failed Delete Book By Id")
    void deleteBookByIdFail() throws ItemNotFoundException {

        //Given
        given(bookRepository.existsById(any(Long.class))).willReturn(false);

        // When/Then
        assertThrows(ItemNotFoundException.class,()->bookServiceImpl.deleteBookById(1L));

        //Then
        then(bookRepository).shouldHaveNoMoreInteractions();
    }

}