package com.stefanski.booksearch.util;

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

		System.out.println("\t>> HttpConnect --START -> Downloading: " + sourceUrl);

		URL url = new URI(sourceUrl).toURL();

		int connectAttemps = 101;
		while (connectAttemps-- != 0) {
			try {
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestProperty("User-Agent",
						// "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko)
						// Chrome/53.0.2785.143 Safari/537.36");
						"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36 OPR/50.0.2762.67");

				int responseCode = con.getResponseCode();
				if (responseCode >= 200 && responseCode < 300) {
					System.out.println("\t\t--Reading URL success " + sourceUrl);
					System.out.println("\t>> HttpConnect --END");
					return IOUtil.read(con.getInputStream());
				} else {
					System.out.print((connectAttemps == 100)
							? ("\t\t--Reading URL failed, response code: " + responseCode)
							: ((connectAttemps % 20 == 0) ? (", \n\t\t\t" + responseCode) : (", " + responseCode)));
					TimeUnit.SECONDS.sleep((long) 0.5);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("\n\t>> HttpConnect --END");
		return null;
	}
}
