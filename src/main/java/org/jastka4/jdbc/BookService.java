package org.jastka4.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class BookService {
	private BookDAO bookDAO;
	private AuthorService authorService;

	public BookService(final Connection connection) {
		bookDAO = new BookDAO(connection);
		authorService = new AuthorService(connection);
	}

	public List<Book> getBooks() {
		return bookDAO.getBooks();
	}

	public Book getBook(final int id) {
		return bookDAO.getBook(id);
	}

	public Book insertBook(final Book book) {
		final Author author;
		try {
			author = authorService.insertAuthor(book.getAuthor());
			if (Objects.nonNull(author)) {
				book.getAuthor().setId(author.getId());
				int bookId = bookDAO.insertBook(book);
				book.setId(bookId);
				return book;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void updateBook(final Book book) {
		try {
			bookDAO.updateBook(book);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
