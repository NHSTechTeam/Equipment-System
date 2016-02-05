package DataRecorderAndRetriever;

import java.util.ArrayList;

/**
 * 
 * @author James & Devin
 *
 */

public class ItemManager {

	// check out an item
	public void checkOut(int ref, String fileName, String member, int ID) {

		// create reader and writer
		csvFileReader read = new csvFileReader();
		csvFileWriter write = new csvFileWriter();

		// get member name
		Item item1 = read.getItem(ID, member);
		String name = item1.getName();

		// find items position in list
		int pos = read.checkPosition(ref, fileName);
		System.out.println(pos);

		// create array
		ArrayList<Item> items = read.getData(fileName);

		// checkout item
		(items.get(pos)).checkOut(name);

		// rewrite file
		write.enterData(items);
		write.writeCsvFile(fileName);

	}

	// erase a file
	public void clearFile(String fileName) {
		csvFileWriter write = new csvFileWriter();
		ArrayList<Item> list = new ArrayList<Item>();

		// enter empy data
		write.enterData(list);

		// rewrite file with no data
		write.writeCsvFile(fileName);
	}

	// check in an item
	public void checkIn(int ref, String fileName) {
		// create reader and writer
		csvFileReader read = new csvFileReader();
		csvFileWriter write = new csvFileWriter();

		// find items position in list
		int pos = read.checkPosition(ref, fileName);

		// create array
		ArrayList<Item> items = read.getData(fileName);

		// checkin item
		(items.get(pos)).checkIn();

		// rewrite file
		write.enterData(items);
		write.writeCsvFile(fileName);
	}

	// register an Item
	public void register(int ref, String name, boolean permission, String fileName) {
		// create reader and writer
		Item item = new Item(ref, name, true, "none", permission);
		csvFileReader read = new csvFileReader();
		csvFileWriter write = new csvFileWriter();

		// create array
		ArrayList<Item> items = read.getData(fileName);

		// check if ref is unused
		if (read.checkPosition(item.getReference(), fileName) == -1) {
			// add item
			items.add(item);
		} else {
			// output error
			System.out.println("Reference already used.");
		}

		// rewrite file
		write.enterData(items);
		write.writeCsvFile(fileName);
	}

	// remove an Item
	public void remove(int ref, String fileName) {
		// create reader and writer
		csvFileReader read = new csvFileReader();
		csvFileWriter write = new csvFileWriter();

		// create array
		ArrayList<Item> items = read.getData(fileName);

		// get position
		int pos = read.checkPosition((read.getItem(ref, fileName)).getReference(), fileName);

		// remove item
		items.remove(pos);

		// rewrite file
		write.enterData(items);
		write.writeCsvFile(fileName);
	}

	// return an item
	public Item getItem(int ref, String fileName) {
		// get item through reader
		csvFileReader read = new csvFileReader();
		return read.getItem(ref, fileName);
	}

	// print csv file
	public String printFile(String fileName) {
		csvFileReader read = new csvFileReader();
		return read.printFile(fileName);
	}

	// print an arraylist nicely
	public void printList(ArrayList<Item> list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.println("\n" + list.get(i).toString());
		}
	}

	// search for an item
	public ArrayList<Item> searchFor(String fileName, String search) {
		// create reader
		csvFileReader read = new csvFileReader();

		// create arraylists
		ArrayList<String> keyword = new ArrayList<String>();
		ArrayList<Item> info = read.getData(fileName);
		ArrayList<Item> returned = new ArrayList<Item>();

		// add the whole string as a keyword, covers for one word searches
		keyword.add(search);

		// add each word entered into the search as a separate keyword
		while (search.indexOf(' ') != -1) {
			keyword.add(search.substring(0, search.indexOf(' ')));
			search = search.substring(search.indexOf(' ') + 1);
		}

		// add the remaining word
		keyword.add(search);

		// check if each item name contains each keyword
		for (int i = 0; i < info.size(); i++) {
			for (int e = 0; e < keyword.size(); e++) {
				if (info.get(i).getName().toLowerCase().contains(keyword.get(e).toLowerCase())) {
					returned.add(info.get(i));
					// prevent items from being added twice for containing two
					// keywords
					break;
				}
			}
		}
		// return list of items
		return returned;

	}
}