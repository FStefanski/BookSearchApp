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
import java.util.ArrayList;
import java.util.List;

import com.stefanski.amazonbooksearcher.constants.SearchAndIO;
import com.stefanski.amazonbooksearcher.dao.ItemsDao;
import com.stefanski.amazonbooksearcher.entities.InitialWebLink;
import com.stefanski.amazonbooksearcher.entities.Item;
import com.stefanski.amazonbooksearcher.managers.WebLinkManager;

public class SerializationTest {

	private List<Item> ItemList = new ArrayList<>();

	public static void main(String[] args) {

		SerializationTest serializationTest = new SerializationTest();

		InitialWebLink initialWebLink = WebLinkManager.getInstance().createInitialWebLink(SearchAndIO.getSearchFor());

		serializationTest.doDeserialization(initialWebLink);

		// Print all collected items
		serializationTest.printAllItems();
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
			DataStore.getInstance().setItemList((ArrayList) in.readObject());

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Print all collected items.
	 */
	private void printAllItems() {

		ItemsDao itemsDao = new ItemsDao();
		System.out.println("\n>> Items saved: ");
		if (!itemsDao.getAllItems().isEmpty()) {
			for (Item item : itemsDao.getAllItems()) {
				System.out.println(item.toString() + ", item type: " + item.getItemType());

				System.out.println("\tWeb Link: " + item.getWebLink().toString());

			}
		} else {
			System.err.println("\tNo items found! Check initial web page.");
		}
	}
}
