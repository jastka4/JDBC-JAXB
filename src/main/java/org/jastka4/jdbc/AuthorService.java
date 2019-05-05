package org.jastka4.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AuthorService {
	private AuthorDAO authorDAO;

	public AuthorService(final Connection connection) {
		authorDAO = new AuthorDAO(connection);
	}

	public List<Author> getAuthors() {
		try {
			return authorDAO.getAuthors();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Author getAuthor(final int id) {
		try {
			return authorDAO.getAuthor(id);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Author insertAuthor(final Author author) {
		try {
			int authorID = authorDAO.insertAuthor(author);
			author.setId(authorID);
			return author;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void updateAuthor(final Author author) {
		try {
			authorDAO.updateAuthor(author);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
