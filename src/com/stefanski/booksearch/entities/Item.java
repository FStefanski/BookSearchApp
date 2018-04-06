package com.stefanski.booksearch.entities;

import java.io.Serializable;

import com.stefanski.booksearch.constants.ItemType;

/**
 * <b>Used to define Item common fields.</b>
 * 
 * @author Frederik Stefanski
 *
 */
public abstract class Item implements Serializable {

	private ItemType itemType;
	private long id;
	private WebLink webLink;
	private String title;

	public Item(ItemType itemType) {
		this.itemType = itemType;
	}

	/**
	 * Assess if the item can be recommended. Item can be recommended if the rating
	 * is above a certain level.
	 */
	public abstract boolean isRecommendable();


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((itemType == null) ? 0 : itemType.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((webLink == null) ? 0 : webLink.hashCode());
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
		Item other = (Item) obj;
		if (id != other.id)
			return false;
		if (itemType != other.itemType)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (webLink == null) {
			if (other.webLink != null)
				return false;
		} else if (!webLink.equals(other.webLink))
			return false;
		return true;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public WebLink getWebLink() {
		return webLink;
	}

	public void setWebLink(WebLink webLink) {
		this.webLink = webLink;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ItemType getItemType() {
		return itemType;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

}
