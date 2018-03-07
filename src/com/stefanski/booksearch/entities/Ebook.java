package com.stefanski.booksearch.entities;

import com.stefanski.booksearch.constants.ItemType;

/**
 * TODO
 * 
 * @author Frederik Stefanski
 *
 */
public class Ebook extends Item {

	private Ebook() {
		// TODO
		super(ItemType.EBOOK);
	}

	@Override
	public boolean isRecommendable() {
		// TODO Auto-generated method stub
		return false;
	}
}
