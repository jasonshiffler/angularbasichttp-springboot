package com.shiffler.simplerestapi.controllers;

import com.shiffler.simplerestapi.entities.Book;
import com.shiffler.simplerestapi.exceptions.BadDataException;
import com.shiffler.simplerestapi.exceptions.ItemNotFoundException;
import com.shiffler.simplerestapi.services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {


    @Mock
    BookService bookService;

    @Mock
    Book mockBook;

    @InjectMocks
    BookController bookController;

    MockMvc mockMvc;

    @BeforeEach
    void init() {

        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    void testControllerFindAllBooks() throws Exception {
        mockMvc.perform(get("/books"))
        .andExpect(status().isOk());
    }

    @Test
    void findAllBooks() {

        Book book = new Book("Moby Dick","Herman Melville",650);
        List<Book> bookList = new ArrayList<>();
        bookList.add(book);

        //Given
        given(bookService.findAllBooks()).willReturn(bookList);

        //When
        Iterable<Book> results = bookController.findAllBooks();

        //Then
        then(bookService).should().findAllBooks();
        assertThat(results).hasSize(1);
    }

    @Test
    void findBookById() throws ItemNotFoundException {
        bookController.findBookById(5L);
        verify(bookService).findBookById(5L);
    }

    @Test
    void addBook() {
        bookController.addBook(mockBook);
        verify(bookService).addBook(mockBook);
    }

    @Test
    void updateBook() throws BadDataException, ItemNotFoundException {
        bookController.updateBook(mockBook,5L);
        verify(bookService).updateBook(mockBook,5L);
    }

    @Test
    void deleteBookById() throws BadDataException, ItemNotFoundException {
        bookController.deleteBookById(5L);
        verify(bookService).deleteBookById(5L);

    }
}