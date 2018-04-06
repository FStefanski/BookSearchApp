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
				+ publisher + " (" + publicationYear + "), " + isbn + ", " + amazonRating + " of of 5 stars]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(amazonRating);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + Arrays.hashCode(authors);
		result = prime * result + ((coverURL == null) ? 0 : coverURL.hashCode());
		result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
		result = prime * result + ((publicationYear == null) ? 0 : publicationYear.hashCode());
		result = prime * result + ((publisher == null) ? 0 : publisher.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (Double.doubleToLongBits(amazonRating) != Double.doubleToLongBits(other.amazonRating))
			return false;
		if (!Arrays.equals(authors, other.authors))
			return false;
		if (coverURL == null) {
			if (other.coverURL != null)
				return false;
		} else if (!coverURL.equals(other.coverURL))
			return false;
		if (isbn == null) {
			if (other.isbn != null)
				return false;
		} else if (!isbn.equals(other.isbn))
			return false;
		if (publicationYear == null) {
			if (other.publicationYear != null)
				return false;
		} else if (!publicationYear.equals(other.publicationYear))
			return false;
		if (publisher == null) {
			if (other.publisher != null)
				return false;
		} else if (!publisher.equals(other.publisher))
			return false;
		return true;
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
