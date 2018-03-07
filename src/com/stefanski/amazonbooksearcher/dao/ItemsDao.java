package com.stefanski.amazonbooksearcher.dao;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.stefanski.amazonbooksearcher.DataStore;
import com.stefanski.amazonbooksearcher.entities.Item;

/**
 * Access point to items data storage.
 * 
 * @author Frederik Stefanski
 *
 */
public class ItemsDao {

	/**
	 * Add item to data store.
	 * 
	 * @param item
	 *            to save
	 */
	public void saveItem(Item item) {

		DataStore.getInstance().getItemList().add(item);

		// try (Connection conn =
		// DriverManager.getConnection("jdbc:mysql://localhost:3306/jid_thrillio?useSSL=false",
		// "root", "root"); Statement stmt = conn.createStatement();) {
		//
		// if (item instanceof Book) {
		// saveBook(item, stmt);
		// }
		//
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
	}

	private void saveBook(Item item, Statement stmt) throws SQLException {
		// String query = "insert into User_Book (user_id, book_id) values (" +
		// book.getId() + ", "
		// + userBookmark.getBookmark().getId() + ")";
		//
		// // update MySQL DB
		// stmt.executeUpdate(query); // INSERT, UPDATE, or DELETE
	}

	/**
	 * Fetch item from data store.
	 * 
	 * @param index
	 * @return item at index
	 */
	public Item getItem(int ItemIndex) {
		return DataStore.getInstance().getItemList().get(ItemIndex);
	}

	/**
	 * Get all saved items from data store.
	 *
	 * @return items List
	 */
	public List<Item> getAllItems() {
		return DataStore.getInstance().getItemList();
	}

	/**
	 * Get the titles of all saved items in the data store.
	 * 
	 * @return Items' title list.
	 */
	public List<String> getAllTitels() {
		List<String> titleList = new ArrayList<>();
		for (Item item : DataStore.getInstance().getItemList()) {

			titleList.add(item.getTitle());
		}
		return titleList;
	}
}
