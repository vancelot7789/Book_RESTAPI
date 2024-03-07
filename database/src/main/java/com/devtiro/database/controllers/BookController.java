package com.devtiro.database.controllers;


import com.devtiro.database.services.BookService;
import com.devtiro.database.domain.dto.BookDto;
import com.devtiro.database.domain.entities.BookEntity;
import com.devtiro.database.mappers.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class BookController {

    private BookService bookService;
    private Mapper<BookEntity, BookDto> bookMapper;

    public BookController(BookService bookService, Mapper<BookEntity, BookDto> bookMapper){
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @PutMapping(path="/books/{isbn}")
    public ResponseEntity<BookDto> createBook(@PathVariable("isbn") String isbn, @RequestBody BookDto book){
        BookEntity bookEntity = bookMapper.mapFrom(book);

        boolean bookExists = bookService.isExist(isbn);
        BookEntity savedBookEntity = bookService.createUpdateBook(isbn, bookEntity);
        BookDto savedUpdateBookDto = bookMapper.mapTo(savedBookEntity);

        if(bookExists){
            return new ResponseEntity<>(savedUpdateBookDto, HttpStatus.OK); // status 200
        }else{
            return new ResponseEntity<>(savedUpdateBookDto, HttpStatus.CREATED); // status 201
        }

    }

    @GetMapping(path="/books")
    public Page<BookDto> listBooks(Pageable pageable){
        Page<BookEntity> books = bookService.findAll(pageable);

        return books.map(bookMapper::mapTo);
    }

    @GetMapping(path="/books/{isbn}")
    public ResponseEntity<BookDto> getBook(@PathVariable("isbn") String isbn){
        Optional<BookEntity> book = bookService.findOne(isbn);
        if(book.isPresent()){
            BookDto bookDto = bookMapper.mapTo(book.get());
            return new ResponseEntity<>(bookDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping(path="/books/{isbn}")
    public ResponseEntity<BookDto> partialUpdateBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto){


        if(!bookService.isExist(isbn)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity updatedBookEntity = bookService.partialUpdate(isbn, bookEntity);
        BookDto updatedBookDto = bookMapper.mapTo(updatedBookEntity);

        return new ResponseEntity<>(updatedBookDto, HttpStatus.OK);
    }

    @DeleteMapping(path="/books/{isbn}")
    public ResponseEntity deleteBook(@PathVariable("isbn") String isbn){
        bookService.delete(isbn);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
