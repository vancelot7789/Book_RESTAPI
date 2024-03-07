package com.devtiro.database.services.impl;

import com.devtiro.database.domain.entities.BookEntity;
import com.devtiro.database.repositories.BookRepository;
import com.devtiro.database.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    @Override
    public BookEntity createUpdateBook(String isbn, BookEntity book) {
        book.setIsbn(isbn);
        return bookRepository.save(book);
    }



    @Override
    public List<BookEntity> findAll() {
        List<BookEntity> bookList = new ArrayList<>();
        bookRepository.findAll().forEach(bookList::add);

        return bookList;
    }

    @Override
    public Page<BookEntity> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public Optional<BookEntity> findOne(String isbn) {
        return bookRepository.findById(isbn);
    }

    @Override
    public boolean isExist(String isbn) {
        return bookRepository.existsById(isbn);
    }

    @Override
    public BookEntity partialUpdate(String isbn, BookEntity bookEntity) {
        Optional<BookEntity> optionalExistBook = bookRepository.findById(isbn);
        if(!optionalExistBook.isPresent()){
            throw new RuntimeException("Book does not exist");
        }
        BookEntity existBook = optionalExistBook.get();
        existBook.setIsbn(isbn);

        if(bookEntity.getTitle() != null){
            existBook.setTitle(bookEntity.getTitle());
        }

        BookEntity updatedBook = bookRepository.save(existBook);
        return updatedBook;

//        OPTION 2
//        bookEntity.setIsbn(isbn);
//
//        return bookRepository.findById(isbn).map(existingBook -> {
//            Optional.ofNullable(bookEntity.getTitle()).ifPresent(existingBook::setTitle);
//            return bookRepository.save(existingBook);
//        }).orElseThrow(() -> new RuntimeException("Book does not exist"));

    }

    @Override
    public void delete(String isbn) {
        bookRepository.deleteById(isbn);
    }





}
