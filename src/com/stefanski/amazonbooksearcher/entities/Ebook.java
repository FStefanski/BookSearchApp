package com.stefanski.amazonbooksearcher.entities;

import com.stefanski.amazonbooksearcher.constants.ItemType;

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
