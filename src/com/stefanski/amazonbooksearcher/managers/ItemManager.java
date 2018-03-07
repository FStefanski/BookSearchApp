package com.stefanski.amazonbooksearcher.managers;

import java.time.Year;

import com.stefanski.amazonbooksearcher.entities.Book;
import com.stefanski.amazonbooksearcher.entities.Ebook;
import com.stefanski.amazonbooksearcher.entities.WebLink;

/**
 * <b>Used to create new Item instances like book.</b>
 * 
 * <br>
 * <br>
 * ItemManager is following the Singleton Pattern:
 * <li>Only one object is created and no more.</li>
 * <li>Global point of access via <code>getInstance()</code>.</li>
 * 
 * <br>
 * 
 * @author Frederik Stefanski
 * 
 */
public class ItemManager {

	private static ItemManager instance = new ItemManager(); // accessing Items data

	/**
	 * Private constructor - only one instance should be created.
	 */
	private ItemManager() {
	}

	/**
	 * Global point of access to WebLinkManager.
	 */
	public static ItemManager getInstance() {
		return instance;
	}

	/**
	 * Create new book.
	 * 
	 * @param publicationYear
	 * @param pages
	 * @param publisher
	 * @param authors
	 * @param isbn
	 * @param amazonRating
	 * @param webLink
	 * @return Book
	 */
	public Book createBook(String authors, String publisher, String publicationYear, String isbn, String amazonRating,
			WebLink webLink) {

		Book book = new Book();

		book.setId(webLink.getId());
		book.setTitle(webLink.getTitle());

		book.setPublicationYear(Year.of(Integer.parseInt(publicationYear)));
		book.setPublisher(publisher);
		book.setAuthors(authors.split(","));
		book.setIsbn(isbn);
		book.setAmazonRating(Double.parseDouble(amazonRating));
		book.setWebLink(webLink);
		return book;
	}

	/**
	 * Create new ebook.
	 */
	public Ebook createEbook() {
		// TODO

		return null;
	}
}
