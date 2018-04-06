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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((downloadStatus == null) ? 0 : downloadStatus.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result + ((webPage == null) ? 0 : webPage.hashCode());
		result = prime * result + ((webPageFileName == null) ? 0 : webPageFileName.hashCode());
		result = prime * result + ((webPageFilePath == null) ? 0 : webPageFilePath.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WebLink other = (WebLink) obj;
		if (downloadStatus != other.downloadStatus)
			return false;
		if (id != other.id)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		if (webPage == null) {
			if (other.webPage != null)
				return false;
		} else if (!webPage.equals(other.webPage))
			return false;
		if (webPageFileName == null) {
			if (other.webPageFileName != null)
				return false;
		} else if (!webPageFileName.equals(other.webPageFileName))
			return false;
		if (webPageFilePath == null) {
			if (other.webPageFilePath != null)
				return false;
		} else if (!webPageFilePath.equals(other.webPageFilePath))
			return false;
		return true;
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
