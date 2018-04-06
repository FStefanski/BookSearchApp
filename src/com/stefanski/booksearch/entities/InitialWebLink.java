package com.stefanski.booksearch.entities;

import com.stefanski.booksearch.constants.SearchAndIO;

/**
 * <b>Used for:</b>
 * <li>Initialization of item search.</li>
 * <li>Storage search URL.</li>
 * <li>Storage item's title.</li>
 * <li>Storage search results web page.</li>
 * <li>Defines web page download file path and file name.</li>
 * 
 * <br>
 * Initial web link is a web link.
 * 
 * <br>
 * <br>
 * 
 * @author Frederik Stefanski
 *
 */
public class InitialWebLink extends WebLink {

	private String searchFor = SearchAndIO.getSearchFor();

	public InitialWebLink(String searchFor) {
		// super();
		this.searchFor = searchFor;
		setId(0);
		setUrl("https://www.amazon.com/s/ref=nb_sb_noss?url=search-alias%3Daps&field-keywords=" + searchFor);
		setTitle(searchFor);
		setWebPageFilePath(SearchAndIO.getSearchResultFilePath() + SearchAndIO.getSearchFor() + "\\");
		setWebPageFileName("InitialWebPage_" + String.valueOf(getId()) + ".html");
	}

	@Override
	public String toString() {
		return "InitialWebLink [id=" + getId() + ", url=\t" + getUrl() + ",\n\t\t\t\t\t title=\t\t" + getTitle()
				+ ",\n\t\t\t\t\t status=\t" + getDownloadStatus() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((searchFor == null) ? 0 : searchFor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		InitialWebLink other = (InitialWebLink) obj;
		if (searchFor == null) {
			if (other.searchFor != null)
				return false;
		} else if (!searchFor.equals(other.searchFor))
			return false;
		return true;
	}

	public String getSearchFor() {
		return searchFor;
	}

	public void setSearchFor(String searchFor) {
		this.searchFor = searchFor;
	}
}
