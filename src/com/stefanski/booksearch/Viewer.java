package com.stefanski.booksearch;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("BookSearchApp");

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

	public static void main(String[] args) {
		launch(args);
	}
}
