package com.devtiro.database.services;

import com.devtiro.database.domain.entities.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BookService {

    BookEntity createUpdateBook(String isbn, BookEntity bookEntity);

    Page<BookEntity> findAll(Pageable pageable);
    List<BookEntity> findAll();

    Optional<BookEntity> findOne(String isbn);

    boolean isExist(String isbn);

    BookEntity partialUpdate(String isbn, BookEntity bookEntity);

    void delete(String isbn);
}
