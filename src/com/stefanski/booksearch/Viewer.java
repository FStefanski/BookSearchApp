package com.stefanski.booksearch;

import java.io.IOException;

import com.stefanski.booksearch.dao.ItemsDao;
import com.stefanski.booksearch.entities.Item;
import com.stefanski.booksearch.models.ItemModel;
import com.stefanski.booksearch.view.ItemOverviewController;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * <b>The View application is the main application for GUI.</b><br>
 * Responsible for:
 * <li>Initializes the Root Layout.</li>
 * <li>Shows Item Overview in the Root Layout.</li><br>
 * 
 * @author Frederik Stefanski
 */
public class Viewer extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;

	/**
	 * The data as an observable list of ItemModel.
	 */
	private ObservableList<ItemModel> itemsDataList = FXCollections.observableArrayList();

	/**
	 * Constructor
	 */
	public Viewer() {

		System.out.println("\n>> Launching Viewer ... ");
		ItemsDao itemsDao = new ItemsDao();

		// Add saved data to observable list of Items using ItemModel.
		for (Item item : itemsDao.getAllItems()) {
			itemsDataList.add(new ItemModel(item));
		}

		System.out.println("\t<> Viewer loaded " + itemsDataList.size() + " positions to present ...");
		System.out.println("\t\t--First Item: id: " + itemsDataList.get(0).idProperty() + ", title: "
				+ itemsDataList.get(0).titleProperty() + ", cover url: " + itemsDataList.get(0).coverURLProperty());
	}

	/**
	 * Returns the data as an observable list of Items using ItemModel.
	 * 
	 * @return ObservableList of ItemModel
	 */
	public ObservableList<ItemModel> getItemsData() {
		return itemsDataList;
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("BookSearchApp");
		// Set the application icon.
		this.primaryStage.getIcons().add(new Image("file:resources/images/Book_search.png"));

		initRootLayout();
		showItemOverview();
	}

	/**
	 * Initializes the root layout - background for UI Elements.
	 */
	private void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Viewer.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Shows the item overview inside the root layout.
	 */
	private void showItemOverview() {
		try {
			// Load Item overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Viewer.class.getResource("view/ItemOverview.fxml"));
			AnchorPane itemOverview = (AnchorPane) loader.load();

			// Set item overview into on the center of root layout.
			rootLayout.setCenter(itemOverview);

			// Give the controller access to the main app.
			ItemOverviewController controller = loader.getController();
			controller.setViewer(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the main stage.
	 * 
	 * @return primaryStage
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	/**
	 * Runs the GUI.
	 * 
	 * @param
	 */
	public static void runViewer() {

		launch(null);
	}

	public BorderPane getRootLayout() {
		return rootLayout;
	}

	public void setRootLayout(BorderPane rootLayout) {
		this.rootLayout = rootLayout;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public void setItemsData(ObservableList<ItemModel> itemsDataList) {
		this.itemsDataList = itemsDataList;
	}
}
