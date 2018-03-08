package com.stefanski.booksearch.models;

import java.time.Year;
import java.util.Arrays;

import com.stefanski.booksearch.entities.Book;
import com.stefanski.booksearch.entities.Item;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a Item.
 *
 * @author Frederik Stefanski
 */
public class ItemModel {

	private final LongProperty id;
	private final StringProperty title;

	private final StringProperty authors;
	private final StringProperty publisher;
	private final ObjectProperty<Year> publicationYear;
	private final StringProperty isbn;
	private final DoubleProperty amazonRating;

	/**
	 * Default constructor.
	 */
	public ItemModel() {
		this(null);
	}

	/**
	 * Constructor with data initialization.
	 * 
	 * @param Item
	 *            item
	 */
	public ItemModel(Item item) {
		this.id = new SimpleLongProperty(item.getId());
		this.title = new SimpleStringProperty(item.getTitle());

		if (item instanceof Book) {

			this.authors = new SimpleStringProperty((Arrays.toString(((Book) item).getAuthors())));
			this.publisher = new SimpleStringProperty(((Book) item).getPublisher());
			this.publicationYear = new SimpleObjectProperty(((Book) item).getPublicationYear());
			this.isbn = new SimpleStringProperty(((Book) item).getIsbn());
			this.amazonRating = new SimpleDoubleProperty(((Book) item).getAmazonRating());
		} else {
			// else if(item instanceof Ebook) { // TODO }
			this.authors = new SimpleStringProperty("N/D");
			this.publisher = new SimpleStringProperty("N/D");
			this.publicationYear = new SimpleObjectProperty("N/D");
			this.isbn = new SimpleStringProperty("N/D");
			this.amazonRating = new SimpleDoubleProperty(0.0);
		}
	}

	public long getId() {
		return id.get();
	}

	public void setId(long id) {
		this.id.set(id);
	}

	public LongProperty idProperty() {
		return id;
	}

	public String getTitle() {
		return title.get();
	}

	public void setTitle(String title) {
		this.title.set(title);
	}

	public StringProperty titleProperty() {
		return title;
	}

	public String getAuthors() {
		return authors.get();
	}

	public void setAuthors(String authors) {
		this.authors.set(authors);
	}

	public StringProperty authorsProperty() {
		return authors;
	}

	public String getPublisher() {
		return publisher.get();
	}

	public void setPublisher(String publisher) {
		this.publisher.set(publisher);
	}

	public StringProperty publisherProperty() {
		return publisher;
	}

	public Year getPublicationYear() {
		return publicationYear.get();
	}

	public void setPublicationYear(Year publicationYear) {
		this.publicationYear.set(publicationYear);
	}

	public ObjectProperty<Year> publicationYearProperty() {
		return publicationYear;
	}

	public String getIsbn() {
		return isbn.get();
	}

	public void setIsbn(String isbn) {
		this.isbn.set(isbn);
	}

	public StringProperty isbnProperty() {
		return isbn;
	}

	public double getAmazonRating() {
		return amazonRating.get();
	}

	public void setAmazonRating(double amazonRating) {
		this.amazonRating.set(amazonRating);
	}

	public DoubleProperty amazonRatingProperty() {
		return amazonRating;
	}
}