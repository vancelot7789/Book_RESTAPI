package com.devtiro.database.controllers;

import com.devtiro.database.domain.dto.AuthorDto;
import com.devtiro.database.services.AuthorService;
import com.devtiro.database.domain.entities.AuthorEntity;

import com.devtiro.database.mappers.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class AuthorController {

    private AuthorService authorService;

    private Mapper<AuthorEntity, AuthorDto> authorMapper;

    public AuthorController(AuthorService authorService, Mapper<AuthorEntity, AuthorDto> authorMapper){
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }
    // no exposing entity in presentation layer
    @PostMapping(path="/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author){
        AuthorEntity authorEntity = authorMapper.mapFrom(author);
        AuthorEntity savedAuthorEntity = authorService.save(authorEntity);
        return new ResponseEntity<>(authorMapper.mapTo(savedAuthorEntity), HttpStatus.CREATED);
    }

    @GetMapping(path="/authors")
    public List<AuthorDto> listAuthors(){
        List<AuthorEntity> authors = authorService.findAll();
        return authors.stream().map(authorMapper::mapTo).collect(Collectors.toList());
    }

    @GetMapping(path="/authors/{id}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable("id") Long id){
        Optional<AuthorEntity> foundAuthor = authorService.findOne(id);

        if (foundAuthor.isPresent()){
            AuthorDto authorDto = authorMapper.mapTo(foundAuthor.get());
            return new ResponseEntity<AuthorDto>(authorDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<AuthorDto>(HttpStatus.NOT_FOUND);
        }

//        return foundAuthor.map(authorEntity -> {
//            AuthorDto authorDto = authorMapper.mapTo(authorEntity);
//            return new ResponseEntity<AuthorDto>(authorDto, HttpStatus.OK);
//        }).orElse(
//            new ResponseEntity<AuthorDto>(HttpStatus.NOT_FOUND)
//        );
    }

    @PutMapping(path="/authors/{id}")
    public ResponseEntity<AuthorDto> fullUpdateAuthor(
            @PathVariable("id")Long id,
            @RequestBody AuthorDto authorDto){

        if(!authorService.isExist(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        authorDto.setId(id);
        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        AuthorEntity savedAuthorEntity = authorService.save(authorEntity);

        return new ResponseEntity<>(authorMapper.mapTo(savedAuthorEntity), HttpStatus.OK);

    }

    @PatchMapping(path="/authors/{id}")
    ResponseEntity<AuthorDto> partialUpdateAuthor(
            @PathVariable("id")Long id,
            @RequestBody AuthorDto authorDto){

        if(!authorService.isExist(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        AuthorEntity updatedAuthor = authorService.partialUpdate(id, authorEntity);

        AuthorDto updatedAuthorDto = authorMapper.mapTo(updatedAuthor);

        return new ResponseEntity<>(updatedAuthorDto, HttpStatus.OK);


    }

    @DeleteMapping(path="/authors/{id}")
    public ResponseEntity deleteAuthor(@PathVariable("id") Long id){
        authorService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
