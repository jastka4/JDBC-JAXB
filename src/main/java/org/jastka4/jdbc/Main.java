package org.jastka4.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            Connection connection = DataSource.getConnection();
            AuthorService authorService = new AuthorService(connection);
            BookService bookService = new BookService(connection);

            {   // get all authors and books
                System.out.println("GET ALL\n====================================");
                List<Author> authors = authorService.getAuthors();
                authors.forEach(System.out::println);

                System.out.println();

                List<Book> books = bookService.getBooks();
                books.forEach(System.out::println);
                System.out.println();
            }

            {   // get one author and one book
                System.out.println("GET ONE\n====================================");
                System.out.println(authorService.getAuthor(1) + "\n");

                System.out.println(bookService.getBook(1) + "\n");
            }

            Author author = new Author("Test", "Test");
            Book book = new Book("Test", author);
            {   // insert a new book with an author
                System.out.println("INSERT\n====================================");
                book = bookService.insertBook(book);

                List<Author> authors = authorService.getAuthors();
                authors.forEach(System.out::println);

                System.out.println();

                List<Book> books = bookService.getBooks();
                books.forEach(System.out::println);
                System.out.println();
            }

            {   // update an author and book
                System.out.println("UPDATE\n====================================");
                book.setTitle("Test Updated");
                bookService.updateBook(book);

                author.setFirstName("Test Updated");
                author.setLastName("Test Updated");
                authorService.updateAuthor(author);

                List<Author> authors = authorService.getAuthors();
                authors.forEach(System.out::println);

                System.out.println();

                List<Book> books = bookService.getBooks();
                books.forEach(System.out::println);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
