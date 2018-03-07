package com.stefanski.amazonbooksearcher.dao;

import java.util.ArrayList;
import java.util.List;

import com.stefanski.amazonbooksearcher.DataStore;
import com.stefanski.amazonbooksearcher.entities.WebLink;

/**
 * 
 * <b>Access point to web links data storage.</b>
 * 
 * <br>
 * 
 * @author Frederik Stefanski
 *
 */
public class WebLinksDao {

	/**
	 * Add web link to data store.
	 * 
	 * @param web
	 *            link to save
	 */
	public void saveWebLink(WebLink webLink) {
		DataStore.getInstance().getWebLinkList().add(webLink);
	}

	/**
	 * Fetch web link from data store.
	 * 
	 * @param index
	 * @return web link at index
	 */
	public WebLink getWebLink(int webLinkIndex) {
		return DataStore.getInstance().getWebLinkList().get(webLinkIndex);
	}

	/**
	 * Get all saved web links from data store.
	 * 
	 * @return web links list
	 */
	public List<WebLink> getAllWebLinks() {
		return DataStore.getInstance().getWebLinkList();
	}

	/**
	 * Get all saved web links from data store.
	 * 
	 * @return web links List
	 */
	public List<String> getAllTitels() {
		List<String> titleList = new ArrayList<>();
		for (WebLink weblink : DataStore.getInstance().getWebLinkList()) {

			titleList.add(weblink.getTitle());
		}
		return titleList;
	}

	/**
	 * Get the titles of all saved web links in the data store.
	 * 
	 * @return web links' title list.
	 */
	public List<String> getAllURLs() {
		List<String> URLList = new ArrayList<>();
		for (WebLink weblink : DataStore.getInstance().getWebLinkList()) {

			URLList.add(weblink.getUrl());
		}
		return URLList;
	}

	/**
	 * Method invoke from background concurrent job.
	 * 
	 * @return - fetch data from data store class.
	 */
	public List<WebLink> getWeblinks(WebLink.DownloadStatus downloadStatus) {
		List<WebLink> result = new ArrayList<>();

		List<WebLink> allWebLinks = getAllWebLinks();
		for (WebLink webLink : allWebLinks) {
			if (webLink.getDownloadStatus().equals(downloadStatus)) {
				result.add(webLink);
			}
		}
		return result;
	}
}
