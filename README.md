# Book Api
This is a RestApi project created in Spring Boot. It implements the basic CRUD operations and pagination, with tests written. The database used is PostgreSQL, running in a Docker container.

## Tools
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/postgresql-4169e1?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)

## Key Features
- Create, Read, Update, Delete (CRUD) operations for books and authors.
- Read operations for multiple books and authors with pagination.
- Cascade deletion from authors to books.
- Pagination for book listings.

## API Examples

| Method   | URL                                      | Description                              |
| -------- | ---------------------------------------- | ---------------------------------------- |
| `GET`    | `/authors`                               | Retrieve all authors                     |
| `GET`    | `/authors/12`                            | Retrieve author with id #12              |
| `POST`   | `/authors`                               | Create a new author                      |
| `PUT`    | `/authors/12`                            | Full update for author with id #12       |
| `PATCH`  | `/authors/12`                            | Partial update for author with id #12    |
| `DELETE` | `/authors/12`                            | Delete author with id #12                |
| `GET`    | `/books`                                 | Retrieve all books                       |
| `GET`    | `/books/978-1-2345-6789-2`               | Retrieve book with ISBN #978-1-2345-6789-2 |
| `POST`   | `/books`                                 | Create a new book                        |
| `PUT`    | `/books/978-1-2345-6789-2`               | Full update for book with ISBN #978-1-2345-6789-2 |
| `PATCH`  | `/books/978-1-2345-6789-2`               | Partial update for book with ISBN #978-1-2345-6789-2 |
| `DELETE` | `/books/978-1-2345-6789-2`               | Delete book with ISBN #978-1-2345-6789-2 |

## Entities Overview

This project revolves around two primary entities: `Author` and `Book`. These entities are central to the application's functionality, including CRUD operations and relationships management.

### Author Entity

The `Author` entity represents an author with the following attributes:

- **Id**: A unique identifier for each author. It is automatically generated.
- **Name**: The name of the author.
- **Age**: The age of the author.

This entity is mapped to the `authors` table in the PostgreSQL database.

#### Attributes:

- `id`: Long - The primary key of the author.
- `name`: String - The name of the author.
- `age`: Integer - The age of the author.

### Book Entity

The `Book` entity represents a book with the following attributes and a relation to its author:

- **ISBN**: The International Standard Book Number, serving as a unique identifier.
- **Title**: The title of the book.
- **AuthorEntity**: A many-to-one relationship indicating the book's author.

This entity is mapped to the `books` table in the PostgreSQL database and establishes a relationship with the `Author` entity via the `author_id` foreign key.

#### Attributes:

- `isbn`: String - The unique ISBN of the book.
- `title`: String - The title of the book.
- `authorEntity`: AuthorEntity - A reference to the `Author` entity representing the book's author. This creates a direct link between each book and its author, supporting operations like cascading updates or deletions.

### Entity Relationship

The relationship between `Author` and `Book` is defined as many-to-one, where an author can have multiple books, but each book is associated with a single author. This relationship is crucial for managing the data cohesively and ensuring integrity across operations.
