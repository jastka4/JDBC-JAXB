package org.jastka4.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class BookDAO {
	private static final String SELECT_ALL = "SELECT BOOK.ID, BOOK.TITLE, BOOK.AUTHOR_ID, AUTHOR.LAST_NAME, AUTHOR.FIRST_NAME FROM BOOK JOIN AUTHOR ON AUTHOR.ID=BOOK.AUTHOR_ID";
	private static final String SELECT_SINGLE = "SELECT BOOK.ID, BOOK.TITLE, BOOK.AUTHOR_ID, AUTHOR.LAST_NAME, AUTHOR.FIRST_NAME FROM BOOK JOIN AUTHOR ON AUTHOR.ID=BOOK.AUTHOR_ID WHERE BOOK.ID = ?";
	private static final String INSERT = "INSERT INTO BOOK (TITLE, AUTHOR_ID) VALUES (?, ?)";
	private static final String UPDATE = "UPDATE BOOK SET TITLE = ?, AUTHOR_ID = ? WHERE ID = ?";
	String query = " insert into users (first_name, last_name, date_created, is_admin, num_points)"
			+ " values (?, ?, ?, ?, ?)";
	private final Connection connection;

	BookDAO(Connection connection) {
		this.connection = connection;
	}

	public List<Book> getBooks() {
		try (Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement.executeQuery(SELECT_ALL);
			return mapToList(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	public Book getBook(final int id) {
		try (PreparedStatement statement = connection.prepareStatement(SELECT_SINGLE)) {
			statement.setInt(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				List<Book> books = mapToList(resultSet);
				return books.isEmpty() ? null : books.get(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int insertBook(final Book book) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, book.getTitle());
			statement.setInt(2, book.getAuthor().getId());
			final int affectedRows = statement.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("Creating book failed, no rows affected.");
			}

			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					return generatedKeys.getInt(1);
				} else {
					throw new SQLException("Creating book failed, no ID obtained.");
				}
			}
		}
	}

	public void updateBook(final Book book) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(UPDATE, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, book.getTitle());
			statement.setInt(2, book.getAuthor().getId());
			statement.setInt(3, book.getId());

			final int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("Updating author failed, no rows affected.");
			}
		}
	}

	private List<Book> mapToList(final ResultSet resultSet) throws SQLException {
		List<Book> list = new ArrayList<>();
		while (resultSet.next()) {
			Author author = new Author(
					resultSet.getInt("AUTHOR_ID"),
					resultSet.getString("FIRST_NAME"),
					resultSet.getString("LAST_NAME")
			);
			Book book = new Book(
					resultSet.getInt("AUTHOR_ID"),
					resultSet.getString("TITLE"),
					author
			);
			list.add(book);
		}
		return list;
	}
}
