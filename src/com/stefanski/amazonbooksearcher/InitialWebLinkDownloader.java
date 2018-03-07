package com.stefanski.amazonbooksearcher;

import com.stefanski.amazonbooksearcher.entities.InitialWebLink;
import com.stefanski.amazonbooksearcher.entities.WebLink;
import com.stefanski.amazonbooksearcher.util.HttpConnect;
import com.stefanski.amazonbooksearcher.util.IOUtil;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

/**
 * 
 * @author Frederik Stefanski
 *
 */
public class InitialWebLinkDownloader {

	/**
	 * 
	 * @param initialWebLink
	 */
	public static void download(InitialWebLink initialWebLink) {

		System.out.println("\n>> Downloading initial web page ...");
		// download web page
		try {
			initialWebLink.setDownloadStatus(WebLink.DownloadStatus.FAILED);

			String webPage = HttpConnect.download(initialWebLink.getUrl());
			initialWebLink.setWebPage(webPage);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		String webPage = initialWebLink.getWebPage();
		if (webPage != null) {
			// save web page
			IOUtil.write(webPage, initialWebLink); // file name = WebPage_Id
			initialWebLink.setDownloadStatus(WebLink.DownloadStatus.SUCCESS);
			System.out.println("\t--Download Success: " + initialWebLink.toString());
		} else {

			initialWebLink.setDownloadStatus(WebLink.DownloadStatus.FAILED);
			System.out.println("\t--Webpage not downloaded " + initialWebLink.toString());
		}
	}
}
