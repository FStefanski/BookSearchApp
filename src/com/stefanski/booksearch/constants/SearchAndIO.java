package com.stefanski.booksearch.constants;

/**
 * <b>Storage for search and input/output operations constants:</b>
 * <li>Default search title.</li>
 * <li>Download path.</li>
 * <li>Chart encoding standard.</li>
 * 
 * <br>
 * 
 * @author Frederik Stefanski
 *
 */
public class SearchAndIO {

	private static String searchFor = "java%20book"; // lord%20of%20the%20rings%20book
	private static final String SEARCH_RESULTS_FILE_PATH = System.getProperty("user.dir")
			+ "\\downloads\\results_for\\";
	private static final String UTF_ENCODING = "utf-8";

	private SearchAndIO() {
	}

	public static String getSearchFor() {
		return searchFor;
	}

	public static void setSearchFor(String searchFor) {
		SearchAndIO.searchFor = searchFor.replaceAll(" ", "%20");
	}

	public static String getSearchResultFilePath() {
		return SEARCH_RESULTS_FILE_PATH;
	}

	public static String getUTF_ENCODING() {
		return UTF_ENCODING;
	}
}
