package com.stefanski.booksearch.view;

import java.io.File;

import com.stefanski.booksearch.View;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;

/**
 * <b>The controller for the root layout.</b><br>
 * The root layout provides the basic application layout containing:
 * <li>A menu bar.</li>
 * <li>Space where other JavaFX elements can be placed.</li><br>
 * 
 * @author Frederik Stefanski
 */
public class RootLayoutController {

	// Reference to the main application
	private View view;

	/**
	 * Is called by the main view application to give a reference back to itself.
	 * 
	 * @param view
	 */
	public void setView(View view) {
		this.view = view;
	}

	/**
	 * Creates an empty address book.
	 */
	@FXML
	private void handleNew() {
		// view.getPersonData().clear();
		// view.setPersonFilePath(null);
	}

	/**
	 * Opens a FileChooser to let the user select an address book to load.
	 */
	@FXML
	private void handleOpen() {
		FileChooser fileChooser = new FileChooser();

		// Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);

		// Show open file dialog
		File file = fileChooser.showOpenDialog(view.getPrimaryStage());

		if (file != null) {
			// view.loadPersonDataFromFile(file);
		}
	}

	/**
	 * Saves the file to the person file that is currently open. If there is no open
	 * file, the "save as" dialog is shown.
	 */
	@FXML
	private void handleSave() {
		// File personFile = view.getPersonFilePath();
		// if (personFile != null) {
		// view.savePersonDataToFile(personFile);
		// } else {
		// handleSaveAs();
		// }
	}

	/**
	 * Opens a FileChooser to let the user select a file to save to.
	 */
	@FXML
	private void handleSaveAs() {
		FileChooser fileChooser = new FileChooser();

		// Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);

		// Show save file dialog
		File file = fileChooser.showSaveDialog(view.getPrimaryStage());

		if (file != null) {
			// Make sure it has the correct extension
			if (!file.getPath().endsWith(".xml")) {
				file = new File(file.getPath() + ".xml");
			}
			// view.savePersonDataToFile(file);
		}
	}

	/**
	 * Opens an about dialog.
	 */
	@FXML
	private void handleAbout() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("AddressApp");
		alert.setHeaderText("About");
		alert.setContentText("Author: Marco Jakob\nWebsite: http://code.makery.ch");

		alert.showAndWait();
	}

	/**
	 * Closes the application.
	 */
	@FXML
	private void handleExit() {
		System.exit(0);
	}
}
