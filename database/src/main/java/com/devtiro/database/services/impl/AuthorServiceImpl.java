package com.devtiro.database.services.impl;

import com.devtiro.database.repositories.AuthorRepository;
import com.devtiro.database.services.AuthorService;
import com.devtiro.database.domain.entities.AuthorEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }
    @Override
    public AuthorEntity save(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }

    @Override
    public List<AuthorEntity> findAll() {
        List<AuthorEntity> authorList = new ArrayList<>();
        authorRepository.findAll().forEach(authorList::add);
        return authorList;

//        OPTION 2
//        return StreamSupport.stream(authorRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Optional<AuthorEntity> findOne(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public boolean isExist(Long id) {
        return authorRepository.existsById(id);
    }

    @Override
    public AuthorEntity partialUpdate(Long id, AuthorEntity authorEntity) {
        Optional<AuthorEntity> optionalExistAuthor = authorRepository.findById(id);
        if(!optionalExistAuthor.isPresent()){
            throw new RuntimeException("Author does not exist");
        }

        AuthorEntity existingAuthor = optionalExistAuthor.get();

        if(authorEntity.getName() != null){
            existingAuthor.setName(authorEntity.getName());
        }
        if(authorEntity.getAge() != null){
            existingAuthor.setAge(authorEntity.getAge());
        }

        return authorRepository.save(existingAuthor);
//        OPTION 2
//        return authorRepository.findById(id).map(existingAuthor -> {
//           Optional.ofNullable(authorEntity.getName()).ifPresent(existingAuthor::setName);
//           Optional.ofNullable(authorEntity.getAge()).ifPresent(existingAuthor::setAge);
//           return authorRepository.save(existingAuthor);
//        }).orElseThrow( () -> new RuntimeException("Author does not exist"));

    }

    @Override
    public void delete(Long id) {
        authorRepository.deleteById(id);
    }


}
