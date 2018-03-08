package com.stefanski.booksearch.constants;

/**
 * All data in one line
 * 
 * @author Frederik Stefanski
 *
 */
public class SearchResultPrasingKey {

	public static final String DONT_PARSE_SPONSORED_URL = "href=\"/gp/slredirect/";

	private static final String RESULT_START = "<li id=\"result_";
	private static final String RESULT_END = "\"";

	private static final String TITLE_START = "title=\"";
	private static final String TITLE_END = "\"";

	private static final String URL_START = "href=\"https://www.amazon.com/";
	private static final String URL_END = "\"";

	public static final String[] parsingKey = { RESULT_START, RESULT_END, TITLE_START, TITLE_END, URL_START, URL_END };

	private SearchResultPrasingKey() {
	}
}
