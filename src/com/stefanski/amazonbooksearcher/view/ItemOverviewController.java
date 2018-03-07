package com.stefanski.amazonbooksearcher.view;

import com.stefanski.amazonbooksearcher.View;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * 
 * @author Frederik Stefanski
 *
 */
public class ItemOverviewController {
	// @FXML - Any method inside our controller that is annotated with @FXML (or is
	// public)
	// is accessible by the Scene Builder.

	@FXML
	private Label id;
	@FXML
	private Label title;
	@FXML
	private Label authors;
	@FXML
	private Label publisher;
	@FXML
	private Label publicationYear;
	@FXML
	private Label amazonRating;

	// Reference to the main application.
	private View view;

	/**
	 * The constructor. The constructor is called before the initialize() method.
	 */
	public ItemOverviewController() {
	}

}
