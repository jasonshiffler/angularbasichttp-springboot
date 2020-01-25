package com.shiffler.simplerestapi.controllers;

import com.shiffler.simplerestapi.entities.Book;
import com.shiffler.simplerestapi.exceptions.BadDataException;
import com.shiffler.simplerestapi.exceptions.ItemNotFoundException;
import com.shiffler.simplerestapi.services.BookService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;


@WebMvcTest(BookController.class)
class BookControllerTest {

    @MockBean
    BookService bookService;

    @Autowired
    MockMvc mockMvc;

    List<Book> bookList;

    Book book1;
    Book book2;


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

    @AfterEach
    void tearDown() {
        //Make sure to reset the service so it is in the same state at the beginning of each test
        reset(bookService);
    }

    @Test
    @DisplayName("Find All Books")
    void findAllBooks() throws Exception {

        //Given
        given(bookService.findAllBooks()).willReturn(bookList);

        //When
        mockMvc.perform(get("/books"))

        //Then
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0]title", is("Moby Dick")))
        .andExpect(jsonPath("$[0]id", is(1)))
        .andExpect(jsonPath("$[0]author", is("Herman Melville")))
        .andExpect(jsonPath("$[0]numPages", is(650)))
        .andExpect(jsonPath("$[1]id", is(2)))
        .andExpect(jsonPath("$[1]title", is("Pilgrim's Progress")))
        .andExpect(jsonPath("$[1]author", is("John Bunyan")))
        .andExpect(jsonPath("$[1]numPages", is(400)))
        .andExpect(jsonPath("$", hasSize(2)));

        //.andDo(print()) Use this for troubleshooting tests
    }

    @Test
    @DisplayName("Successful Find Book by Id")
    void findBookByIdSuccessful() throws Exception {

        //Given
        given(bookService.findBookById(any())).willReturn(book1);

        //When
        mockMvc.perform(get("/books/"+ book1.getId()))

        //Then
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id", is(1)))
        .andExpect(jsonPath("$.title", is("Moby Dick")))
        .andExpect(jsonPath("$.author", is("Herman Melville")))
        .andExpect(jsonPath("$.numPages", is(650)));
    }

    @Test
    @DisplayName("Failed Find Book by Id")
    void findBookByIdFail() throws Exception {

        //Given
        given(bookService.findBookById(any())).willThrow(ItemNotFoundException.class);

        //When
        mockMvc.perform(get("/books/3" ))

        //Then
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }


    @Test
    @DisplayName("Successful Book Add")
    void addBookSuccess() throws Exception {

        //When
        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"hello\", \"author\":\"hello\", \"numPages\":50}"))
         //Then
        .andExpect(status().isOk());
         verify(bookService).addBook(new Book("hello","hello",50));
    }

    @Test
    @DisplayName("Failed Book Add Bad Request")
    void addBookFailBadRequest() throws Exception {

        //When
        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"author\":\"hello\", \"numPages\":50}"))

        //Then
        .andExpect(status().isBadRequest());
        verify(bookService,never()).addBook(any());

    }

    @Test
    @DisplayName("Successful Book Update")
    void updateBookSuccess() throws Exception {

        //When
        mockMvc.perform(put("/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"hello\", \"author\":\"hello\", \"numPages\":50}"))
        //Then
       .andExpect(status().isOk());
        verify(bookService).updateBook(new Book("hello","hello",50),1L);
    }

    @Test
    @DisplayName("Failed Book Update Item Not Found")
    void updateBookFailNotFound() throws Exception {

        //Given
        doThrow(ItemNotFoundException.class).when(bookService).updateBook(any(),any());

        //When
        mockMvc.perform(put("/books/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"hello\", \"author\":\"hello\", \"numPages\":50}"))
        //Then
        .andExpect(status().isNotFound());
        verify(bookService).updateBook(new Book("hello","hello",50),3L);
    }

    @Test
    @DisplayName("Failed Book Update Bad Request")
    void updateBookFailBadRequest() throws Exception {

        //When
        mockMvc.perform(put("/books/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"author\":\"hello\", \"numPages\":50}"))
        //Then
        .andExpect(status().isBadRequest()).andDo(print());
        verify(bookService,never()).updateBook(any(),any());
    }




    @Test
    @DisplayName("Successful Book Delete")
    void deleteBookByIdSuccess() throws Exception {

        //When
        mockMvc.perform(delete("/books/1")
                .contentType(MediaType.APPLICATION_JSON))
        //Then
        .andExpect(status().isOk());
        verify(bookService, Mockito.times(1)).deleteBookById(1L);
    }

    @Test
    @DisplayName("Failed Book Delete Item Not Found")
    void deleteBookByIdFailNotFound() throws Exception {

        //Given
        doThrow(ItemNotFoundException.class).when(bookService).deleteBookById(any());

        //When
        mockMvc.perform(delete("/books/3")
                .contentType(MediaType.APPLICATION_JSON))
        //Then
        .andExpect(status().isNotFound());
        verify(bookService, Mockito.times(1)).deleteBookById(3L);
    }



}