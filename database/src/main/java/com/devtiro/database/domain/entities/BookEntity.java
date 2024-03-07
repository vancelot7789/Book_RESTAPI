package com.devtiro.database.domain.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder


// jpa stuff
@Entity
@Table(name="books")

public class BookEntity {

    @Id
    private String isbn;

    private String title;

//    private Long authorId;

    // since using jpa we can include another entity
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id")
    private AuthorEntity authorEntity;
}
