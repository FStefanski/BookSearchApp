package com.stefanski.amazonbooksearcher;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.stefanski.amazonbooksearcher.constants.SearchAndIO;
import com.stefanski.amazonbooksearcher.entities.InitialWebLink;
import com.stefanski.amazonbooksearcher.entities.Item;
import com.stefanski.amazonbooksearcher.entities.WebLink;

/**
 * Class to only hold and populate data, e.g. URLs to parse.
 * 
 * @author Frederik Stefanski
 *
 */
public class DataStore implements Serializable {

	private final static Logger LOGGER = Logger.getLogger(Launcher.class.getName());

	private static DataStore instance = new DataStore(); // accessing Items data

	// Data to storage
	private transient List<WebLink> webLinkList = new ArrayList<>(); // marked as transient should not be serialized
	private List<Item> ItemList = new ArrayList<>();

	/**
	 * Private constructor - only one instance should be created.
	 */
	private DataStore() {
	}

	/**
	 * Global point of access to WebLinkManager.
	 */
	public static DataStore getInstance() {
		return instance;
	}

	/**
	 * 
	 */
	public void doSerialization(InitialWebLink initialWebLink) {
		System.out.println("\n>> Inside doSerialization ...");

		File file = new File(initialWebLink.getWebPageFilePath() + "ItemList.ser");

		try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
			out.writeObject(DataStore.getInstance().getItemList());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public void doDeserialization(InitialWebLink initialWebLink) {
		System.out.println("\n>> Inside doDeserialization ...");

		File file = new File(initialWebLink.getWebPageFilePath() + "ItemList.ser");

		try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {
			DataStore.getInstance().setItemList((List) in.readObject());

		} catch (FileNotFoundException e) {
			System.err.println("\tCuld not find results for: " + SearchAndIO.getSearchFor());
			LOGGER.log(Level.FINE, "Culd not find results for: " + SearchAndIO.getSearchFor());
			// e.printStackTrace();
		} catch (IOException e) {
			System.err.println("\tCuld not find read results for: " + SearchAndIO.getSearchFor());
			LOGGER.log(Level.FINE, "Culd not find read results for: " + SearchAndIO.getSearchFor());
			// e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create database for results...
	 */
	public static void createDataBase() {
		// TODO
	}

	/**
	 * Load items from database...
	 */
	public static void loadItems() {
		// TODO
	}

	public List<WebLink> getWebLinkList() {
		return webLinkList;
	}

	public void setWebLinkList(List<WebLink> webLinkList) {
		this.webLinkList = webLinkList;
	}

	public List<Item> getItemList() {
		return ItemList;
	}

	public void setItemList(List<Item> ItemList) {
		this.ItemList = ItemList;
	}

}