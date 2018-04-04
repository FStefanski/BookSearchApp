package com.stefanski.booksearch.util;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class HttpConnect {

	/**
	 * 
	 * @param sourceUrl
	 * @return
	 * @throws MalformedURLException
	 * @throws URISyntaxException
	 */
	public static String download(String sourceUrl) throws MalformedURLException, URISyntaxException {

		System.out.println("\t>> HttpConnect -- START -> Downloading: " + sourceUrl.substring(0, 65));

		URL url = new URI(sourceUrl).toURL();

		int connectAttempsLimit = 11;
		int connectAttemps = 0;
		while (++connectAttemps != connectAttempsLimit) {
			try {
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();

				// setting HTTP headers
				// e.g. connection.setRequestProperty("CustomHeader", "someValue");
				// use Live HTTP Header to see the web browser HTTP Headers
				connection.setRequestProperty("User-Agent",
						"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3377.1 Safari/537.36");
				connection.setRequestProperty("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
				connection.setRequestProperty("Accept-Encoding", "identity");
				connection.setRequestProperty("Accept-Language", "en-US,en;q=0.9");
				// connection.setRequestProperty("Referer",
				// "https://www.amazon.com/ref=nav_logo");
				// connection.setRequestProperty("Upgrade-Insecure-Requests", "1");

				// Accept cookies - see more
				// https://stackoverflow.com/questions/16150089/how-to-handle-cookies-in-httpurlconnection-using-cookiemanager
				CookieManager cookieManager = new CookieManager();
				CookieHandler.setDefault(cookieManager);

				// get response code
				int responseCode = connection.getResponseCode();
				if (responseCode >= 200 && responseCode < 300) {

					// TimeUnit.SECONDS.sleep((long) 1.5);

					System.out.println("\t\t-- Reading URL success, response code: " + responseCode);
					System.out.println("\t>> HttpConnect -- END");
					return IOUtil.read(connection.getInputStream());
				} else {
					System.out.print((connectAttemps == 1)
							? ("\t\t-- Reading URL failed, response code: " + responseCode)
							: ((connectAttemps % 20 == 0) ? (", \n\t\t\t" + responseCode) : (", " + responseCode)));

					// TimeUnit.SECONDS.sleep((long) 1.5);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("\n\t>> HttpConnect -- END");
		return null;
	}
}
