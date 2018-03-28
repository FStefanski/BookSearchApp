package com.stefanski.booksearch.view;

import com.stefanski.booksearch.Viewer;
import com.stefanski.booksearch.models.ItemModel;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * 
 * @author Frederik Stefanski
 *
 */
public class ItemOverviewController {
	@FXML // This is necessary for the fxml file to have access to private fields and
			// private methods.
	private TableView<ItemModel> itemTable;

	// One somewhat strange part of the property API is that IntegerProperty
	// implements ObservableValue<Number>, not ObservableValue<Integer>.
	// see:
	// https://stackoverflow.com/questions/24889638/javafx-properties-in-tableview
	@FXML
	private TableColumn<ItemModel, Number> idColumn;
	@FXML
	private TableColumn<ItemModel, String> titleColumn;

	@FXML
	private Label titleLabel;
	@FXML
	private Label authorsLabel;
	@FXML
	private Label publisherLabel;
	@FXML
	private Label publicationYearLabel;
	@FXML
	private Label isbnLabel;
	@FXML
	private Label amazonRatingLabel;
	@FXML
	private Hyperlink amazonUrlLabel;

	// cover view
	private String coverURL;

	@FXML
	private ImageView coverView = new ImageView();
	@FXML
	private Image coverImage = new Image("file:resources/images/Book_search.png"); // initial image
	@FXML
	private RadioButton showCoverButton = new RadioButton();

	// Reference to the main application.
	private Viewer viewer;

	/**
	 * The constructor is called before the initialize() method.
	 */
	public ItemOverviewController() {
	}

	/**
	 * Initializes the controller class. This method is automatically called after
	 * the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		System.out.println("\n>> Viewer initialization() ... ");

		// Initialize the item table with the two columns.
		titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
		idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());

		// Clear person details.
		showItemDetails(null);

		// Listen for selection changes and show the person details when changed.
		itemTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showItemDetails(newValue));

		// set initial image to coverViwe
		coverView.setImage(coverImage);
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param Viewer
	 *            viewer
	 */
	public void setViewer(Viewer viewer) {
		this.viewer = viewer;

		// Add observable list data to the table
		itemTable.setItems(viewer.getItemsData());
	}

	/**
	 * Fills all text fields to show details about the ItemModel. If the specified
	 * ItemModel is null, all text fields are cleared.
	 * 
	 * @param itemModel
	 *            the itemModel or null
	 */
	private void showItemDetails(ItemModel itemModel) {
		if (itemModel != null) {
			// Fill the labels with info from the itemModel object.
			titleLabel.setText(itemModel.getTitle());
			authorsLabel.setText(itemModel.getAuthors());
			publisherLabel.setText(itemModel.getPublisher());
			publicationYearLabel.setText(itemModel.getPublicationYear().toString());
			isbnLabel.setText(itemModel.getIsbn());
			amazonRatingLabel.setText(Double.toString(itemModel.getAmazonRating()));
			amazonUrlLabel.setText(itemModel.getAmazonUrl());

			setCoverURL(itemModel.getCoverURL());
		} else {
			// itemModel is null, remove all the text.
			titleLabel.setText("");
			authorsLabel.setText("");
			publisherLabel.setText("");
			publicationYearLabel.setText("");
			isbnLabel.setText("");
			amazonRatingLabel.setText("");
			amazonUrlLabel.setText("");

			setCoverURL("file:resources/images/noImageAvalible.jpg");
		}

		// change cover image if radial button pressed
		handleShowCover();
	}

	/**
	 * Called when the user press on the show cover button.
	 */
	@FXML
	private void handleShowCover() {

		if (showCoverButton.isSelected()) {
			// System.out.println("Button pressed!");
			coverView.setImage(new Image(getCoverURL()));
		} else {
			// System.out.println("Button released!");
			coverView.setImage(new Image("file:resources/images/Book_search.png"));
		}
	}

	public String getCoverURL() {
		return coverURL;
	}

	public void setCoverURL(String coverURL) {
		this.coverURL = coverURL;
	}

}
