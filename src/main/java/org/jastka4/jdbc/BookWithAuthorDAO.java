package org.jastka4.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

class BookWithAuthorDAO {

	private static final String QUERY_TEMPLATE = "SELECT BOOK.TITLE, AUTHOR.LAST_NAME, AUTHOR.FIRST_NAME FROM BOOK JOIN AUTHOR ON AUTHOR.id=BOOK.AUTHOR_ID";
	private final Connection connection;

	BookWithAuthorDAO(Connection connection) {
		this.connection = connection;
	}

	public List<BookWithAuthor> getBooksWithAuthors() {
		try (Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement.executeQuery(QUERY_TEMPLATE);
			return mapToList(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	private List<BookWithAuthor> mapToList(ResultSet resultSet) throws SQLException {
		List<BookWithAuthor> list = new ArrayList<>();
		while (resultSet.next()) {
			BookWithAuthor bookWithAuthor = new BookWithAuthor(
					resultSet.getString("TITLE"),
					resultSet.getString("FIRST_NAME"),
					resultSet.getString("LAST_NAME")
			);
			list.add(bookWithAuthor);
		}
		return list;
	}
}
