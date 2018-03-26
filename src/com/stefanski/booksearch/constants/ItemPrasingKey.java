package com.stefanski.booksearch.constants;

/**
 * The order corresponds to the order in which the data appears on the web page.
 * 
 * @author Frederik Stefanski
 *
 */
public class ItemPrasingKey {

	private static final String COVER_START = "id=\"imgBlkFront\"";
	private static final String COVER_END = ".jpg";

	private static final String AUTHORS_START = "class=\"a-link-normal contributorNameID\" href=\"/";
	private static final String AUTHORS_END = "/";

	private static final String PUBLISHER_AND_PUBLICATION_YEAR_START = "<li><b>Publisher:</b> ";
	private static final String PUBLISHER_AND_PUBLICATION_YEAR_END = "(";

	private static final String ISBN_START = "<li><b>ISBN-13:</b> ";
	private static final String ISBN_END = "</li>";

	private static final String AMAZON_RATING_START = " out of 5 stars";
	private static final String AMAZON_RATING_END = " out of 5 stars"; // " out of 5 stars</span></i>";

	public static final String[] parsingKey = { COVER_START, COVER_END, AUTHORS_START, AUTHORS_END,
			PUBLISHER_AND_PUBLICATION_YEAR_START, PUBLISHER_AND_PUBLICATION_YEAR_END, ISBN_START, ISBN_END,
			AMAZON_RATING_START, AMAZON_RATING_END };

	private ItemPrasingKey() {
	}
}
