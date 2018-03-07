package com.stefanski.amazonbooksearcher;

import java.io.IOException;

import com.stefanski.amazonbooksearcher.view.ItemOverviewController;
import com.stefanski.amazonbooksearcher.view.RootLayoutController;

import javafx.application.Application;
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
public class View extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Amazon.comBookSearcher");

		// Set the application icon.
		this.primaryStage.getIcons().add(new Image("file:resources/images/Book_search.png"));

		initRootLayout();
		// showItemOverview();
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public BorderPane getRootLayout() {
		return rootLayout;
	}

	public void setRootLayout(BorderPane rootLayout) {
		this.rootLayout = rootLayout;
	}

	/**
	 * Initializes the root layout.
	 */
	public void initRootLayout() {
		try {
			// Load root layout form fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(View.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();

			// Give the controller access to the main view app - View.java.
			RootLayoutController controller = loader.getController();
			controller.setView(this);

		} catch (IOException e) {
			e.printStackTrace();
		}

		// // Try to load last opened person file.
		// File file = getPersonFilePath();
		// if (file != null) {
		// loadPersonDataFromFile(file);
		// }
	}

	// /**
	// * Shows the item overview inside the root layout.
	// */
	// public void showItemOverview() {
	// try {
	// // Load person overview.
	// FXMLLoader loader = new FXMLLoader();
	// loader.setLocation(View.class.getResource("view/PersonOverview.fxml")); //
	// TODO item
	// AnchorPane personOverview = (AnchorPane) loader.load();
	//
	// // Set person overview into the center of root layout.
	// rootLayout.setCenter(personOverview);
	//
	// // Give the controller access to the main app.
	// ItemOverviewController controller = loader.getController();
	// controller.setView(this);
	//
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }

	public static void main(String[] args) {
		launch(args);
	}
}
