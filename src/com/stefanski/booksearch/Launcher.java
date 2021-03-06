package com.stefanski.booksearch;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.stefanski.booksearch.bgjobs.WebpageDownloaderTask;
import com.stefanski.booksearch.constants.ItemPrasingKey;
import com.stefanski.booksearch.constants.ProductDescriptionPrasingKey;
import com.stefanski.booksearch.constants.SearchAndIO;
import com.stefanski.booksearch.constants.SearchResultPrasingKey;
import com.stefanski.booksearch.dao.ItemsDao;
import com.stefanski.booksearch.dao.WebLinksDao;
import com.stefanski.booksearch.entities.InitialWebLink;
import com.stefanski.booksearch.entities.Item;
import com.stefanski.booksearch.entities.WebLink;
import com.stefanski.booksearch.entities.WebLink.DownloadStatus;
import com.stefanski.booksearch.managers.ItemManager;
import com.stefanski.booksearch.managers.WebLinkManager;
import com.stefanski.booksearch.util.Parser;

/**
 * <br>
 * <b>Application Launcher</b>
 * <br>
 * <b>Allowed parameters format:</b>
 * <li>parameter: <b>title</b> - <code>search for this "title"</code></li>
 * <li>parameter: <b>multipart title</b> -
 * <code>search for this "multipart title"</code></li>
 * <li>parameter: <b>-t title</b> -
 * <code>use previous search results of "title"</code></li>
 * <li>parameter: <b>-t multipart title</b> -
 * <code>use previous search results of "multipart title"</code></li>
 * 
 * <br>
 * <b> Example: </b>
 * <li><code>java Launcher -t java</code> - use previous saved search for "java"
 * title</li>
 * <li><code>java Launcher sql for beginers</code> - search for "sql for
 * beginners" title</li>
 * 
 * <br>
 * 
 * @author Frederik Stefanski
 *
 */
public class Launcher {
	private final static Logger LOGGER = Logger.getLogger(Launcher.class.getName());
	private static FileHandler fh = null;

	private static InitialWebLink initialWebLink;
	private static boolean usePreviousResult = false;

	private Launcher() {
	}

	public static void main(String[] args) {

		// Logger initialization
		loggerInit();

		// Use the user defined args parameters to initialize a new search.
		initializeNewSearch(args);
		setInitialWebLink(WebLinkManager.getInstance().createInitialWebLink(SearchAndIO.getSearchFor()));

		if (isUsePreviousResult()) {

			// Load all collected items
			DataStore.getInstance().doDeserialization(initialWebLink);

			// Print all collected items
			printAllItems();

		} else {
			// Download the initial web page with search results or use previous results.
			downloadSearchResults(initialWebLink);
			// initialWebLink.setDownloadStatus(DownloadStatus.SUCCESS);

			// Find and save in DataStore all search results URLs in the initial webpage.
			runParseSearchResults(initialWebLink);

			// Background Jobs -> download all results web pages
			runDownloaderJob();

			// Parsing web pages for item details
			runParserItems();

			// Save all collected items to file/SQL
			DataStore.getInstance().doSerialization(initialWebLink);

			// Load all collected item
			DataStore.getInstance().doDeserialization(initialWebLink);

			// Print all collected items
			printAllItems();
		}

		// Launch JavaFX viewer
		Viewer.runViewer();
	}

	/**
	 * Use the user defined args parameters to initialize a new search. <br>
	 * <b>Allowed parameters format:</b>
	 * <li>parameter <b>title</b> -> <code>search for this title</code></li>
	 * <li>parameter <b>multipart title</b> ->
	 * <code>search for this title</code></li>
	 * <li>parameter <b>-t title</b> ->
	 * <code>use previous search results of title</code></li>
	 * <li>parameter <b>-t multipart title</b> ->
	 * <code>use previous search results of title</code></li>
	 * 
	 * <br>
	 * <b> Example: </b></br>
	 * <li><code>java Launcher -t java</code> -> use previous saved search for
	 * "java" title</li>
	 * <li><code>java Launcher sql for beginers</code> -> search for "sql for
	 * beginers" title</li>
	 * 
	 * <br>
	 * 
	 * @param args
	 */
	public static void initializeNewSearch(String[] args) {

		String title = "";
		if (args.length > 0 && args[0].equals("-t")) {

			setUsePreviousResult(true);

			System.out.print(">> Use previous saved search results for: \"");
			for (int index = 1; index < args.length; index++) {

				title += (args[index] + (((index + 1) == args.length) ? "" : " "));
			}
			title += " book";
			System.out.print(title + "\"\n");
			SearchAndIO.setSearchFor(title);
			LOGGER.log(Level.FINE, (">> Use previous saved search results for: \"" + title + "\""));
		} else if (args.length == 0) {

			System.out.print(">> Default search, searching for: \"" + SearchAndIO.getSearchFor() + "\"");

			LOGGER.log(Level.FINE, ">> Default search, searching for: \"" + SearchAndIO.getSearchFor() + "\"");

		} else {
			System.out.print(">> Searching for: \"");
			for (int index = 0; index < args.length; index++) {

				title += (args[index] + (((index + 1) == args.length) ? "" : " "));
			}
			title += " book";
			System.out.print(title + "\"\n");
			SearchAndIO.setSearchFor(title);

			LOGGER.log(Level.FINE, ">> Search results for: \"" + title + "\"");
		}
	}

	/**
	 * Download the initial web page with search results or use previous results.
	 * 
	 * @param initialWebLink
	 */
	public static void downloadSearchResults(InitialWebLink initialWebLink) {

		if (Launcher.isUsePreviousResult()) {
			initialWebLink.setDownloadStatus(DownloadStatus.SUCCESS);
		} else {
			InitialWebLinkDownloader.download(initialWebLink);
		}
	}

	/**
	 * Find and save in DataStore all search results URLs in the initial web page.
	 * 
	 * @param initialWebLink
	 */
	public static void runParseSearchResults(InitialWebLink initialWebLink) {

		WebLinksDao webLinksDao = new WebLinksDao();
		Parser newParser = new Parser();

		List<List<String>> parseSearchResutlts;

		if (initialWebLink.getDownloadStatus() == DownloadStatus.SUCCESS) {
			parseSearchResutlts = newParser.parseSearchResutlts(initialWebLink, SearchResultPrasingKey.parsingKey,
					SearchResultPrasingKey.DONT_PARSE_SPONSORED_URL);

			if (parseSearchResutlts.isEmpty()) {

				System.err.println("\tNo search results found! Check initial web page.");
				LOGGER.log(Level.FINE, "\tNo search results found! Check initial web page.");
				System.exit(0);
			} else {
				for (List<String> list : parseSearchResutlts) {
					webLinksDao.saveWebLink(WebLinkManager.getInstance().createWebLink(Long.parseLong(list.get(0)),
							list.get(1), list.get(2)));
				}
				for (WebLink webLink : webLinksDao.getAllWebLinks()) {
					System.out.println("\t--" + webLink.toString());
				}
			}
		} else {
			System.out.println(">> Initial web page not downloaded!");
			LOGGER.log(Level.FINE, ">> Initial web page not downloaded!");
		}
	}

	/**
	 * Run background multithread web page download.
	 * 
	 */
	public static void runDownloaderJob() {
		// If passing true - we want download all web links (web pages) in the system.
		WebpageDownloaderTask task = new WebpageDownloaderTask(true);

		// (new Thread(task)).start(); // start() -> WebpageDownloaderTask is Runnable
		Thread downloader = new Thread(task);
		downloader.start(); // start() -> WebpageDownloaderTask is Runnable

		try {
			downloader.join(); // waiting until downloader dies
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// System.out.println("\n>> Back to main thread ... ");
	}

	/**
	 * Find and save in DataStore details about searched item.
	 * 
	 * @param initialWebLink
	 */
	public static void runParserItems() {
		Parser newParser = new Parser();
		WebLinksDao webLinksDao = new WebLinksDao();
		ItemsDao itemsDao = new ItemsDao();

		System.out.println("\n>> Parsing web page for >item details< ...");
		List<String> parseSearchResutlts;
		for (WebLink webLink : webLinksDao.getAllWebLinks()) {
			try {
				parseSearchResutlts = newParser.parseItemDetails(webLink, ItemPrasingKey.parsingKey,
						ProductDescriptionPrasingKey.parsingKey);
				try {
					itemsDao.saveItem(ItemManager.getInstance().createBook(parseSearchResutlts.get(0),
							parseSearchResutlts.get(1), parseSearchResutlts.get(2), parseSearchResutlts.get(3),
							parseSearchResutlts.get(4), parseSearchResutlts.get(5), webLink));

				} catch (IndexOutOfBoundsException e) {
					System.err.println("\tCuld not parse web page: " + webLink.getWebPageFilePath()
							+ webLink.getWebPageFileName());
					LOGGER.log(Level.FINE, "\tCuld not parse web page: " + webLink.getWebPageFilePath()
							+ webLink.getWebPageFileName());
					// e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				System.err.println(
						"\tCuld not read web page: " + webLink.getWebPageFilePath() + webLink.getWebPageFileName());
				LOGGER.log(Level.FINE,
						"\tCuld not read web page: " + webLink.getWebPageFilePath() + webLink.getWebPageFileName());
				// e.printStackTrace();
			}
		}
	}

	/**
	 * Print all collected items.
	 */
	public static void printAllItems() {

		ItemsDao itemsDao = new ItemsDao();
		System.out.println("\n>> Items saved: ");
		if (!itemsDao.getAllItems().isEmpty()) {
			for (Item item : itemsDao.getAllItems()) {
				System.out.println("\t" + item.toString());
			}
			LOGGER.log(Level.FINE, itemsDao.getAllItems().size() + " items found!");
		} else {
			System.err.println("\tNo items found! Check initial web page.");
			LOGGER.log(Level.FINE, "\tNo items found! Check initial web page.");
			System.exit(0);
		}
	}

	/**
	 * System logger initialization.
	 */
	public static void loggerInit() {
		try {
			fh = new FileHandler("logger.log", false);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
		Logger logger = Logger.getLogger("");
		fh.setFormatter(new SimpleFormatter());
		logger.addHandler(fh);
		logger.setLevel(Level.FINEST);
	}

	public static FileHandler getFh() {
		return fh;
	}

	public static void setFh(FileHandler fh) {
		Launcher.fh = fh;
	}

	public static InitialWebLink getInitialWebLink() {
		return initialWebLink;
	}

	public static void setInitialWebLink(InitialWebLink initialWebLink) {
		Launcher.initialWebLink = initialWebLink;
	}

	public static boolean isUsePreviousResult() {
		return usePreviousResult;
	}

	public static void setUsePreviousResult(boolean usePreviousResult) {
		Launcher.usePreviousResult = usePreviousResult;
	}

	public static Logger getLogger() {
		return LOGGER;
	}
}
