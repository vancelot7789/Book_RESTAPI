package com.devtiro.database.repositories;


import com.devtiro.database.TestDataUtil;
import com.devtiro.database.domain.entities.AuthorEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)

//make every test has its clean database
//clean down any changes
@DirtiesContext(classMode =  DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorEntityRepositoryIntegrationTest {

    private AuthorRepository underTest;


    @Autowired
    public AuthorEntityRepositoryIntegrationTest(AuthorRepository underTest){
        this.underTest = underTest;
    }
    @Test
    public void testThatAuthorCanBeCreatedAndRecalled(){
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();

        //underTest.create(author);
        // Optional<Author> result = underTest.findOne(author.getId());

        // jpa method
        underTest.save(authorEntity);
        // jpa method
        Optional<AuthorEntity> result = underTest.findById(authorEntity.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorEntity);

    }

    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndRecalled(){
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorA();
        underTest.save(authorEntityA);

        AuthorEntity authorEntityB = TestDataUtil.createTestAuthorB();
        underTest.save(authorEntityB);

        AuthorEntity authorEntityC = TestDataUtil.createTestAuthorC();
        underTest.save(authorEntityC);

        Iterable<AuthorEntity> results = underTest.findAll();
        assertThat(results).hasSize(3);
        assertThat(results).containsExactly(authorEntityA, authorEntityB, authorEntityC);

    }

    @Test
    public void testThatAuthorCanBeUpdate(){
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();

        underTest.save(authorEntity);
        authorEntity.setName("Updated");
        underTest.save(authorEntity);

        Optional<AuthorEntity> result = underTest.findById(authorEntity.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorEntity);
    }

    @Test
    public void testThatAuthorCanBeDeleted(){
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        underTest.save(authorEntity);
        underTest.deleteById(authorEntity.getId());
        Optional<AuthorEntity> result = underTest.findById(authorEntity.getId());
        assertThat(result).isEmpty();
    }

    @Test
    public void testThatAuthorsWithAgeLessThan(){
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorA();
        AuthorEntity authorEntityB = TestDataUtil.createTestAuthorB();
        AuthorEntity authorEntityC = TestDataUtil.createTestAuthorC();

        underTest.save(authorEntityA);
        underTest.save(authorEntityB);
        underTest.save(authorEntityC);

        Iterable<AuthorEntity> result = underTest.ageLessThan(50);

        assertThat(result).hasSize(2).containsExactly(authorEntityB, authorEntityC);

    }

    @Test
    public void testThatAuthorsWithAgeGreaterThan(){
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorA();
        AuthorEntity authorEntityB = TestDataUtil.createTestAuthorB();
        AuthorEntity authorEntityC = TestDataUtil.createTestAuthorC();

        underTest.save(authorEntityA);
        underTest.save(authorEntityB);
        underTest.save(authorEntityC);

        Iterable<AuthorEntity> result = underTest.findAuthorsWithAgeGreaterThan(50);

        assertThat(result).hasSize(1).containsExactly(authorEntityA);

    }
}
