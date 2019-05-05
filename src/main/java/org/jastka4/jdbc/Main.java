package org.jastka4.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            Connection connection = DataSource.getConnection();
            BookWithAuthorDAO bookWithAuthorDAO = new BookWithAuthorDAO(connection);
            List<BookWithAuthor> booksWithAuthors = bookWithAuthorDAO.getBooksWithAuthors();
            booksWithAuthors.forEach(b -> System.out.println(b));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
