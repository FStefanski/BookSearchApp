package com.stefanski.booksearch.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.stefanski.booksearch.Launcher;
import com.stefanski.booksearch.entities.InitialWebLink;
import com.stefanski.booksearch.entities.WebLink;

/**
 * 
 * 
 * @author Frederik Stefanski
 *
 */
public class Parser {

	private final static Logger LOGGER = Logger.getLogger(Launcher.class.getName());

	/**
	 * Parse the search result web page to extract all results id, titles and URLs
	 * of the searched item - one item per web page line.
	 * 
	 * @param initialWebLink
	 *            - web link containing search result web page in String format
	 * @param parsingKeys
	 *            - Strings array of parsing keys, with a beginning and finishing
	 *            key per searched value
	 * @param dontParsKey
	 *            - values that should not be returned, e.g. sponsored links.
	 * @return Lists of String lists of results in {id, title, URL} sequence.
	 */
	public List<List<String>> parseSearchResutlts(InitialWebLink initialWebLink, String[] parsingKeys,
			String dontParsKey) {

		System.out.println("\n>> Parsing web page for >search results< ...");

		List<List<String>> parsingResultsList = new ArrayList<>();
		List<String> resultsList = new ArrayList<>();
		File file = new File(initialWebLink.getWebPageFilePath() + initialWebLink.getWebPageFileName());

		String line;
		try {
			Scanner scanner = new Scanner(IOUtil.read(new FileInputStream(file)));
			while (scanner.hasNextLine()) { // terminates when end-of-file found
				line = scanner.nextLine();

				// search results parsing - all data in one line
				for (int index = 0; index < (parsingKeys.length); index += 2) {
					if (line.contains(parsingKeys[0]) && (line.contains(dontParsKey) == false)) {

						int startIndex;
						if (parsingKeys[index].contains("https:")) {

							// for URLs only: choose the url after title + adding "https://www.amazon.com/"
							startIndex = line.indexOf(parsingKeys[0])
									+ line.substring(line.indexOf(parsingKeys[0])).indexOf(parsingKeys[index])
									+ parsingKeys[index].length()
									- (parsingKeys[index].length() - parsingKeys[index].indexOf("https:"));
						} else {
							startIndex = line.indexOf(parsingKeys[index]) + parsingKeys[index].length();
						}

						int endIndex = startIndex
								+ line.substring(startIndex, line.length()).indexOf(parsingKeys[index + 1]);

						resultsList.add(line.substring(startIndex, endIndex));
						// System.out.println("\t" + line.substring(startIndex, endIndex));
					}
				}
				// search results parsing - all data in one line
				if (!resultsList.isEmpty()) {
					parsingResultsList.add(resultsList);
					resultsList = new ArrayList<>();
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return parsingResultsList;
	}

	/**
	 * <b>Parse the saved web page to extract items' details specified in parsing
	 * key - one item per web page.</b><br>
	 * <br>
	 * Parsing process divided in five steps:
	 * <li>Step 1: extract cover url and all authors from web page --
	 * webPagePrasingKey [0,3].</li>
	 * <li>Step 2: find web page part with product details --
	 * productDescriptionPrasingKey.</li>
	 * <li>Step 3: extract publication year and publisher from publication details
	 * -- keys [4,5].</li>
	 * <li>Step 4: extract ISBN from product details -- webPagePrasingKey
	 * [6,7].</li>
	 * <li>Step 5: extract Average Customer Review from publication details --
	 * webPagePrasingKey [8,9], e.g. 4.3 from "4.3 out of 5."</li><br>
	 * 
	 * @param webLink
	 *            - contains the web page in String
	 * @param webPagePrasingKey
	 *            - contains parsing keys to find item details
	 * @param productDescriptionPrasingKey
	 *            - contains parsing key to find web page part with item product
	 *            description
	 * @return List of Strings with item's details in order defined by parsing key,
	 *         returns one item per method call
	 * @throws FileNotFoundException
	 *             parsing failure due to inaccessible web page file
	 */
	public List<String> parseItemDetails(WebLink webLink, String[] webPagePrasingKey,
			String[] productDescriptionPrasingKey) throws FileNotFoundException {

		// System.out.println("\n>> Parsing web page for >item details< ...");

		List<String> parsingResultsList = new ArrayList<>();

		File file = new File(webLink.getWebPageFilePath() + webLink.getWebPageFileName());
		LOGGER.log(Level.FINE, "\n>> Parsing web page for >item details< for: " + file);

		Scanner scanner = new Scanner(IOUtil.read(new FileInputStream(file)));
		String line;

		boolean productDescriptionFoundFlag = false;
		boolean amazonRatingFoundFlag = false;

		StringBuilder authorsBuilder = new StringBuilder();
		int startIndex, endIndex;

		// web page parsing - data in different lines
		while (scanner.hasNextLine()) { // terminates when end-of-file found
			line = scanner.nextLine();

			// step 1: extract cover URL and all authors from web page -- keys [0,3]
			if (!line.contains(productDescriptionPrasingKey[0]) && !productDescriptionFoundFlag) {

				// cover URL
				if (line.contains(webPagePrasingKey[0])) {

					startIndex = line.indexOf("https:");
					endIndex = startIndex + line.substring(startIndex, line.length()).indexOf(webPagePrasingKey[0 + 1])
							+ webPagePrasingKey[0 + 1].length();

					String coverURL = null;
					try {
						coverURL = line.substring(startIndex, endIndex);
						// System.out.println("\tcoverURL: " + coverURL);
					} catch (StringIndexOutOfBoundsException e) {
						System.err.println("\tCuld not parse >Cover URL<!");
						LOGGER.log(Level.FINER, "\tCuld not parse >Cover URL<!");
						// e.printStackTrace();
					}
					parsingResultsList.add(coverURL);
				}

				// authors
				if (line.contains(webPagePrasingKey[2])) {

					startIndex = line.indexOf(webPagePrasingKey[2]) + webPagePrasingKey[2].length();

					endIndex = startIndex + line.substring(startIndex, line.length()).indexOf(webPagePrasingKey[2 + 1]);

					try {
						authorsBuilder.append(line.substring(startIndex, endIndex)).append(" ");
					} catch (StringIndexOutOfBoundsException e) {
						System.err.println("\tCuld not parse >Authors<!");
						LOGGER.log(Level.FINER, "\tCuld not parse >Authors<!");
						// e.printStackTrace();
					}
				}
			} else if (line.contains(productDescriptionPrasingKey[0]) && !productDescriptionFoundFlag) {

				// list of authors is complied, process and add authors to parsing results list
				// e.g. "Kathy-Sierra Bert-Bates " --> "Kathy Sierra,Bert Bates"
				String authors = authorsBuilder.toString();
				if (!authors.isEmpty()) {
					authors = authors.replaceAll(" ", ",").replaceAll("-", " ").substring(0, authors.length() - 1);
					// System.out.println("\tauthors: " + authors);
				}
				parsingResultsList.add(authors);

				// Step 2: find web page part with product details
				productDescriptionFoundFlag = true;
			}
			if (productDescriptionFoundFlag) {

				if (line.contains(webPagePrasingKey[4])) {
					// Step 3: extract publication year and publisher from publication details
					// -- keys [4,5]

					startIndex = line.indexOf(webPagePrasingKey[4]) + webPagePrasingKey[4].length();

					endIndex = startIndex + line.substring(startIndex, line.length()).indexOf(webPagePrasingKey[4 + 1]);

					// publisher
					String publisher = null;
					try {
						publisher = line.substring(startIndex, endIndex);
						System.out.println("\tpublisher: " + publisher);
					} catch (StringIndexOutOfBoundsException e) {
						System.err.println("\tCuld not parse >Publisher<!");
						LOGGER.log(Level.FINER, "\tCuld not parse >Publisher<!");
						// e.printStackTrace();
					}
					parsingResultsList.add(publisher);

					// publication year, e.g. McGrw-Hill Education; 9 edition (April 1, 2014),
					Pattern pattern = Pattern.compile("(19\\d\\d|20\\d\\d)");
					Matcher matcher = pattern.matcher(line);
					String publicationYear = null;
					if (matcher.find()) {
						publicationYear = matcher.group(1);
						// System.out.println("\tpublicationYear: " + publicationYear);
					} else {
						System.err.println("\tCuld not parse >Publication year<!");
						LOGGER.log(Level.FINER, "\tCuld not parse >Publication year<!");
					}
					parsingResultsList.add(publicationYear);
				}
				if (line.contains(webPagePrasingKey[6])) {

					// Step 4: extract ISBN from product details -- keys [6,7]
					startIndex = line.indexOf(webPagePrasingKey[6]) + webPagePrasingKey[6].length();

					endIndex = startIndex + line.substring(startIndex, line.length()).indexOf(webPagePrasingKey[6 + 1]);

					String isbn = "N/D";
					try {
						isbn = line.substring(startIndex, endIndex);
						// System.out.println("\tisbn: " + isbn);
					} catch (StringIndexOutOfBoundsException e) {
						System.err.println("\tCuld not parse >ISBN<!");
						LOGGER.log(Level.FINER, "\tCuld not parse >ISBN<!");
						// e.printStackTrace();
					}
					parsingResultsList.add(isbn);
				}
				if (line.contains(webPagePrasingKey[8]) && (amazonRatingFoundFlag == false)) {
					// Step 5: extract Average Customer Review from publication details -- keys
					// [8,9], e.g. "4.3 out of 5"

					endIndex = line.indexOf(webPagePrasingKey[8]); // " out of 5"
					startIndex = endIndex - 3; // "4.3"

					String amazonRating = "0.0";
					try {
						amazonRating = line.substring(startIndex, endIndex);
						// System.out.println("\tamazonRating: " + amazonRating);
					} catch (StringIndexOutOfBoundsException e) {
						System.err.println("\tCuld not parse >Amazon Raiting<!");
						LOGGER.log(Level.FINER, "\tCuld not parse >Amazon Raiting<!");
						// e.printStackTrace();
					}
					parsingResultsList.add(amazonRating);
					amazonRatingFoundFlag = true;
				}
			}
		}
		return parsingResultsList; // returns one item per method call
	}
}
