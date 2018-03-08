package com.stefanski.booksearch.entities;

import com.stefanski.booksearch.constants.ItemType;

/**
 * TODO
 * 
 * @author Frederik Stefanski
 *
 */
public class AudioBook extends Item {

	private AudioBook() {
		// TODO
		super(ItemType.AUDIOBOOK);
	}

	@Override
	public boolean isRecommendable() {
		// TODO Auto-generated method stub
		return false;
	}
}
