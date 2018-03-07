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
