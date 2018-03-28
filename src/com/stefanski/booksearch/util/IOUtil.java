package com.stefanski.booksearch.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.stefanski.booksearch.Launcher;
import com.stefanski.booksearch.constants.SearchAndIO;
import com.stefanski.booksearch.entities.InitialWebLink;
import com.stefanski.booksearch.entities.WebLink;

/**
 * 
 * @author Frederik Stefanski
 *
 */
public class IOUtil {

	private final static Logger LOGGER = Logger.getLogger(Launcher.class.getName());

	public static final String UTF_ENCODING = SearchAndIO.getUTF_ENCODING();

	/**
	 * Read from web page. Save read lines in text.
	 * 
	 * @param InputStream
	 *            in
	 * @return String text
	 */
	public static String read(InputStream in) {

		StringBuilder text = new StringBuilder(); // for concatenation of Web page content

		try (BufferedReader br = new BufferedReader(new InputStreamReader(in, UTF_ENCODING))) {
			String line;
			while ((line = br.readLine()) != null) { // terminates when end-of-file found
				text.append(line).append("\n");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return text.toString();
	}

	/**
	 * 
	 * @param webPage
	 * @param webLink
	 */
	public static void write(String webPage, WebLink webLink) {

		if (webLink instanceof InitialWebLink) {
			System.out.print("\t>> Writing file...");
		}
		LOGGER.log(Level.FINER, "Writing file...");

		File file = new File(webLink.getWebPageFilePath());
		if (!file.exists()) {
			if (file.mkdirs()) {
				if (webLink instanceof InitialWebLink) {
					System.out.print("\t--Directory is created!");
				}
				LOGGER.log(Level.FINEST, "--Directory is created!");
			} else {
				if (webLink instanceof InitialWebLink) {
					System.out.print("\t--Failed to create directory!");
				}
				LOGGER.log(Level.FINEST, "--Failed to create directory!");
			}
		}
		file = new File(webLink.getWebPageFilePath() + webLink.getWebPageFileName());

		// try (BufferedWriter writer = new BufferedWriter(
		// new OutputStreamWriter(new FileOutputStream(file), UTF_ENCODING))) {
		try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), UTF_ENCODING))) {

			writer.write(webPage);
			if (webLink instanceof InitialWebLink) {
				System.out.println("\t--DONE!");
			}
			LOGGER.log(Level.FINER, "--DONE!");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
