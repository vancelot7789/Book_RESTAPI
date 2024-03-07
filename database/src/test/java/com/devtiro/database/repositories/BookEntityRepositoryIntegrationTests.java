package com.devtiro.database.repositories;

import com.devtiro.database.TestDataUtil;
//import com.devtiro.database.dao.AuthorDAO;
import com.devtiro.database.domain.entities.AuthorEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.devtiro.database.domain.entities.BookEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
//make every test has its clean database
//clean down any changes
@DirtiesContext(classMode =  DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookEntityRepositoryIntegrationTests {


    private BookRepository underTest;

    @Autowired
    public BookEntityRepositoryIntegrationTests(BookRepository underTest){
        this.underTest = underTest;

    }

    @Test
    public void testThatBookCanBeCreatedAndRecalled(){
        // create author object
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        // put author in database
        // authorDAO.create(author);

        // create book object
        BookEntity bookEntity = TestDataUtil.createTestBookA(authorEntity);

        // put authorID into book object
        // book.setAuthorId(author.getId());
        // put book in database
        underTest.save(bookEntity);

        Optional<BookEntity> result = underTest.findById(bookEntity.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookEntity);

    }

    @Test
    public void testThatMultipleBooksCanBeCreatedAndRecalled(){
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();


        BookEntity bookEntity1 = TestDataUtil.createTestBookA(authorEntity);
        BookEntity bookEntity2 = TestDataUtil.createTestBookB(authorEntity);
        BookEntity bookEntity3 = TestDataUtil.createTestBookC(authorEntity);


        underTest.save(bookEntity1);
        underTest.save(bookEntity2);
        underTest.save(bookEntity3);

       Iterable<BookEntity> books = underTest.findAll();

        assertThat(books).hasSize(3).containsExactly(bookEntity1, bookEntity2, bookEntity3);
    }

    @Test
    public void testThatBookCanBeUpdated(){
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();

        BookEntity bookEntity = TestDataUtil.createTestBookA(authorEntity);
        underTest.save(bookEntity);

        bookEntity.setTitle("New Title");
        underTest.save(bookEntity);

        Optional<BookEntity> result = underTest.findById(bookEntity.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookEntity);
    }

    @Test
    public void testThatBookCanBeDeleted(){
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();

        BookEntity bookEntity = TestDataUtil.createTestBookA(authorEntity);

        underTest.save(bookEntity);

        underTest.deleteById(bookEntity.getIsbn());

        Optional<BookEntity> result = underTest.findById(bookEntity.getIsbn());

        assertThat(result).isEmpty();

    }

}
