package com.stefanski.booksearch.entities;

import java.io.Serializable;

import com.stefanski.booksearch.constants.SearchAndIO;

/**
 * <b>Used for:</b>
 * <li>Storage found item.</li>
 * <li>Storage item's URL.</li>
 * <li>Storage item's title.</li>
 * <li>Storage item's web page.</li>
 * <li>Define web page download file path and file name.</li>
 * 
 * <br>
 * 
 * @author Frederik Stefanski
 *
 */
public class WebLink implements Serializable {

	private long id;
	private String title;
	private String url;
	private String webPage;
	private DownloadStatus downloadStatus = DownloadStatus.NOT_ATTEMPTED;
	private String webPageFilePath;
	private String webPageFileName;

	public enum DownloadStatus {
		NOT_ATTEMPTED, // for web links that will be periodically added during program run
		SUCCESS, FAILED, NOT_ELIGIBLE; // not eligible for download
	}

	public WebLink() {
	}

	public WebLink(long id, String title, String url) {
		// this();
		setId(id);
		setTitle(title);
		setUrl(url);
		setWebPageFilePath(SearchAndIO.getSearchResultFilePath() + SearchAndIO.getSearchFor() + "\\");
		setWebPageFileName("WebPage_" + String.valueOf(id) + ".html");
	}

	/**
	 * @return "WebLink [id= " + getId() + ", title= " + getTitle() + ", status= " +
	 *         getDownloadStatus() + ", url= " + getUrl() + "]";
	 */
	@Override
	public String toString() {
		return "WebLink [id= " + getId() + ", title= " + getTitle() + ", status= " + getDownloadStatus() + ", url= "
				+ getUrl() + "]";
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWebPage() {
		return webPage;
	}

	public void setWebPage(String WebPage) {
		this.webPage = WebPage;
	}

	public DownloadStatus getDownloadStatus() {
		return downloadStatus;
	}

	public void setDownloadStatus(DownloadStatus downloadStatus) {
		this.downloadStatus = downloadStatus;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getWebPageFilePath() {
		return webPageFilePath;
	}

	public void setWebPageFilePath(String webPageFilePath) {
		this.webPageFilePath = webPageFilePath;
	}

	public String getWebPageFileName() {
		return webPageFileName;
	}

	public void setWebPageFileName(String webPageFileName) {
		this.webPageFileName = webPageFileName;
	}
}
