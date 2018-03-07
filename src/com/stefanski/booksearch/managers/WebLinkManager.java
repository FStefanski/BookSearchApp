package com.stefanski.booksearch.managers;

import com.stefanski.booksearch.entities.InitialWebLink;
import com.stefanski.booksearch.entities.WebLink;

/**
 * <b>Used to create new web link instances like initial web link or web
 * link.</b>
 * 
 * <br>
 * <br>
 * WebLinkManager is following the Singleton Pattern:
 * <li>Only one object is created and no more.</li>
 * <li>Global point of access via <code>getInstance()</code>.</li>
 * 
 * <br>
 * 
 * @author Frederik Stefanski
 * 
 */
public class WebLinkManager {

	private static WebLinkManager instance = new WebLinkManager();

	/**
	 * Private constructor - only one instance should be created.
	 */
	private WebLinkManager() {
	}

	/**
	 * Global point of access to WebLinkManager.
	 */
	public static WebLinkManager getInstance() {
		return instance;
	}

	/**
	 * Create new initial web link.
	 * 
	 * @param searchFor
	 * @return InitialWebLink
	 */
	public InitialWebLink createInitialWebLink(String searchFor) {
		InitialWebLink initialWebLink = new InitialWebLink(searchFor);
		return initialWebLink;
	}

	/**
	 * Create new web link.
	 * 
	 * @param id
	 * @param title
	 * @param url
	 * @return WebLink
	 */
	public WebLink createWebLink(long id, String title, String url) {
		WebLink webLink = new WebLink(id, title, url);
		return webLink;
	}
}
