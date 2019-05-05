package org.jastka4.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO {
	private static final String SELECT_ALL = "SELECT ID, FIRST_NAME, LAST_NAME FROM AUTHOR";
	private static final String SELECT_SINGLE = "SELECT ID, FIRST_NAME, LAST_NAME FROM AUTHOR WHERE id=?";
	private static final String INSERT = "INSERT INTO AUTHOR (FIRST_NAME, LAST_NAME) VALUES (?, ?)";
	private static final String UPDATE = "UPDATE AUTHOR SET FIRST_NAME = ?, LAST_NAME = ? WHERE ID = ?";
	private final Connection connection;

	AuthorDAO(Connection connection) {
		this.connection = connection;
	}

	public List<Author> getAuthors() throws SQLException {
		try (Statement statement = connection.createStatement();
		     ResultSet resultSet = statement.executeQuery(SELECT_ALL)) {
			return mapToList(resultSet);
		}
	}

	public Author getAuthor(final int id) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(SELECT_SINGLE)) {
			statement.setInt(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				return mapToList(resultSet).get(0);
			}
		}
	}

	public int insertAuthor(final Author author) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, author.getFirstName());
			statement.setString(2, author.getLastName());
			final int affectedRows = statement.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("Creating author failed, no rows affected.");
			}

			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					return generatedKeys.getInt(1);
				} else {
					throw new SQLException("Creating author failed, no ID obtained.");
				}
			}
		}
	}

	public void updateAuthor(final Author author) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(UPDATE, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, author.getFirstName());
			statement.setString(2, author.getLastName());
			statement.setInt(3, author.getId());

			final int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("Updating author failed, no rows affected.");
			}
		}
	}

	private List<Author> mapToList(final ResultSet resultSet) throws SQLException {
		List<Author> list = new ArrayList<>();
		while (resultSet.next()) {
			Author author = new Author(
					resultSet.getInt("ID"),
					resultSet.getString("FIRST_NAME"),
					resultSet.getString("LAST_NAME")
			);
			list.add(author);
		}
		return list;
	}
}
