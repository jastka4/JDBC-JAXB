package org.jastka4.jdbc;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {

	public static void main(String[] args) {
//		jaxb();
//		jdbc();
	}

	private static void jaxb() {
		try {
			JAXBContext ctx = JAXBContext.newInstance(Author.class);
			Marshaller marshaller = ctx.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			Author author = new Author(1, "Test JAXB", "Test JAXB");
			marshaller.marshal(author, new File("product.xml"));
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		try {
			File file = new File(System.getProperty("user.dir") + "/product.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Author.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Author author = (Author) jaxbUnmarshaller.unmarshal(file);
			System.out.println(author);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	private static void jdbc() {
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
