package org.jastka4.jdbc;

class Book {
	private int id;
	private String title;
	private Author author;

	public Book(String title, Author author) {
		this.title = title;
		this.author = author;
	}

	public Book(int id, String title, Author author) {
		this.id = id;
		this.title = title;
		this.author = author;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(final Author author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return "Book{" +
				"id=" + id +
				", title='" + title + '\'' +
				", author=" + author +
				'}';
	}
}
