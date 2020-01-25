package com.shiffler.simplerestapi.controllers;

import com.shiffler.simplerestapi.entities.Book;
import com.shiffler.simplerestapi.exceptions.BadDataException;
import com.shiffler.simplerestapi.exceptions.ItemNotFoundException;
import com.shiffler.simplerestapi.services.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
public class BookController {

    private BookService bookService;

    /**
     * Constructor using DI to inject the BookService that will retrieve the data we need
     * @param bookService
     */
    @Autowired
    BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Gets an Iterable containing all of the books in the the db
     * @return
     */
    @GetMapping("/books")
    public Iterable<Book> findAllBooks() {
        log.info("Finding all Books");
        return bookService.findAllBooks();
    }

    /**
     * Returns a Book with a specific id
     * @param id - the id of the book
     * @return - A Book object
     */
    @GetMapping("/books/{id}")
    public Book findBookById(@PathVariable Long id) throws ItemNotFoundException {
        log.info("Finding book with id: " + id);
        return bookService.findBookById(id);
    }

    /**
     * Method allows for a new book to be created. A JSON representation of a book needs to be passed in
     * The @Valid annotation will validate the fields passed in based on the annotations in the book entity
     * @param book
     */
    @PostMapping("/books")
    public void addBook(@Valid @RequestBody Book book) {
        log.info("Adding book " + book.toString());
        bookService.addBook(book);
        }

    /**
     *
     * @param book
     * @param id
     * @throws ItemNotFoundException
     * @throws BadDataException
     */
    @PutMapping("/books/{id}")
    public void updateBook(@Valid @RequestBody Book book,@PathVariable Long id)
            throws ItemNotFoundException, BadDataException {
        log.info("Updating book " + book.toString());
        bookService.updateBook(book, id);
    }

    /**
     * Deletes a book with a corresponding id
     * @param id
     * @throws ItemNotFoundException - if a book isn't found with the provided id this exception is thrown.
     * @throws BadDataException
     */
    @DeleteMapping("/books/{id}")
    public void deleteBookById(@PathVariable Long id)
            throws ItemNotFoundException, BadDataException {
        log.info("Deleting book with id of " +  id);
        bookService.deleteBookById(id);
    }

    }





