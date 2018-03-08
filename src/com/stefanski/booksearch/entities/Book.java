package com.stefanski.booksearch.entities;

import java.io.Serializable;
import java.time.Year;
import java.util.Arrays;

import com.stefanski.booksearch.constants.ItemType;
import com.stefanski.booksearch.partner.Shareable;

/**
 * <b>Used to create new Book instances.</b>
 * 
 * <br>
 * <br>
 * Book is an item.
 * 
 * @author Frederik Stefanski
 *
 */
public class Book extends Item implements Shareable, Serializable {

	private Year publicationYear;
	private String publisher;
	private String[] authors;
	private String isbn;
	private double amazonRating;

	private String coverURL;

	public Book() {
		super(ItemType.BOOK);
	}

	@Override
	public boolean isRecommendable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getItemData() {
		// TODO
		return null;
	}

	/**
	 * return "[id=" + super.getId() + ", \"" + super.getTitle() + "\" by " +
	 * Arrays.toString(authors) + ", " + publisher + " (" + publicationYear + "), "
	 * + ", " + amazonRating + " of of 5 stars]";
	 */
	@Override
	public String toString() {
		return "[id=" + super.getId() + ", \"" + super.getTitle() + "\" by " + Arrays.toString(authors) + ", "
				+ publisher + " (" + publicationYear + "), " + isbn + ", " + amazonRating + " of of 5 stars, "
				+ coverURL + "]";
	}

	public Year getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(Year publicationYear) {
		this.publicationYear = publicationYear;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String[] getAuthors() {
		return authors;
	}

	public void setAuthors(String[] authors) {
		this.authors = authors;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public double getAmazonRating() {
		return amazonRating;
	}

	public void setAmazonRating(double amazonRating) {
		this.amazonRating = amazonRating;
	}

	public String getCoverURL() {
		return coverURL;
	}

	public void setCoverURL(String coverURL) {
		this.coverURL = coverURL;
	}
}
