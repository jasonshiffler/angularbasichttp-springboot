package com.shiffler.simplerestapi.services;

import com.shiffler.simplerestapi.entities.Book;
import com.shiffler.simplerestapi.exceptions.BadDataException;
import com.shiffler.simplerestapi.exceptions.ItemNotFoundException;
import com.shiffler.simplerestapi.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Component
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    /**
     * A constructor that injects our bookRepository dependency
     * @param bookRepository
     */
    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * A list of all books
     * @return
     */
    @Override
    public Iterable<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    /**
     * Add a new book
     * @param book
     */
    @Override
    public void addBook(Book book) {
        bookRepository.save(book);
    }

    /**
     * Find a book based on the id passed to it
     * @param id - the id to match on
     * @return a Book with a matching id
     * @throws ItemNotFoundException
     */
    @Override
    public Book findBookById(Long id) throws ItemNotFoundException {

        Optional<Book> book = bookRepository.findById(id);

        if (book.isPresent()) {
            return book.get();
        } else
            throw new ItemNotFoundException("A book with id " + id + " was not found");
    }

    /**
     * Updates an existing Book with a specific id using the passed in book object
     * @param book - The new book data
     * @param id - The book data that will be updated
     * @throws ItemNotFoundException
     * @throws BadDataException
     */
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

    /**
     * Deletes a book with a specific id
     * @param id - The id of the book to be deleted
     * @throws ItemNotFoundException
     */
    @Override
    @Transactional
    public void deleteBookById(Long id) throws ItemNotFoundException {

        if (bookRepository.existsById(id) == false){
            throw new ItemNotFoundException("Book with id of " + id + " does not exist");
        }
        bookRepository.deleteById(id);

    }

}



