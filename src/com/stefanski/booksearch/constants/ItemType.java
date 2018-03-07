package com.stefanski.booksearch.constants;

/**
 * <b>Used to define item type.</b>
 * 
 * <br>
 * <br>
 * Example:
 * <li>Book.</li>
 * <li>eBook.</li><br>
 * 
 * 
 * @author Frederik Stefanski
 *
 */
public enum ItemType {
	BOOK("Book"), EBOOK("Ebook");

	private String name;

	private ItemType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
