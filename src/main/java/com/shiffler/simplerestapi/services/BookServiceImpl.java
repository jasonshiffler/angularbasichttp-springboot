package com.shiffler.simplerestapi.services;

import com.shiffler.simplerestapi.entities.Book;
import com.shiffler.simplerestapi.exceptions.BadDataException;
import com.shiffler.simplerestapi.exceptions.ItemNotFoundException;
import com.shiffler.simplerestapi.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;


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

    @Override
    public Book findBookById(Long id) throws ItemNotFoundException {

        Optional<Book> book = bookRepository.findById(id);

        if (book.isPresent()) {
            return book.get();
        } else
            throw new ItemNotFoundException("A book with id " + id + " was not found");
    }

    @Override
    public void updateBook(Book book, Long id) throws ItemNotFoundException, BadDataException {

        if (book.getId() == null) {
           throw new BadDataException("book id can't be null");
        }

        if (id == null) {
            throw new BadDataException("id can't be null");
        }

        if(id.compareTo(book.getId()) != 0) {
            throw new BadDataException("the Path variable id " + id +
                    " doesn't equal the book id in the body " + book.getId());
        }

        Optional<Book> optionalBook = bookRepository.findById(id);

        if (optionalBook.isPresent()) {
            book.setId(id);
            bookRepository.save(book);
        } else {
            throw new ItemNotFoundException("A book with id " + book.getId() + " was not found");
        }
    }

    @Override
    public void deleteBook(Long id) throws ItemNotFoundException {

    }

}



