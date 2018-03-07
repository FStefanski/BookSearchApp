package com.stefanski.booksearch.constants;

/**
 * 
 * @author Frederik Stefanski
 *
 */
public class ProductDescriptionPrasingKey {

	private static final String PRODUCT_DETAILS_START = "<h2>Product details</h2>";
	private static final String PRODUCT_DETAILS_END = "\">See Top 100 in Books</a>)";

	public static final String[] parsingKey = { PRODUCT_DETAILS_START, PRODUCT_DETAILS_END };

	private ProductDescriptionPrasingKey() {
	}
}
