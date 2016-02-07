package DataRecorderAndRetriever;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * 
 * @author James Sonne & Devin Matte
 * @version v0.3-Alpha
 * @since 2016-02-06
 */

public class UIManager {

	/**
	 * Window Basic is what sets up all the basic parameters of a UI window, and
	 * off-loads the code to another class to save you space in the class you
	 * store your windows.
	 * 
	 * @param window
	 *            Your window Stage. To help off-load any basic functions
	 * @param title
	 *            The title of the window and main label
	 * @param size
	 *            The minimum width and height of the window
	 * @param label
	 *            The main label for your window
	 * @param errors
	 *            Your error label. Sets the initial value to "" for you
	 */
	public static void windowBasic(Stage window, String title, int size, Label label, Label errors) {
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(size);
		window.setMinHeight(size);
		window.setOnCloseRequest(e -> window.close());
		label.setText(title);
		errors.setText("");
	}

	/**
	 * Checks to see if the data input is a integer. If it is allows for it to
	 * be parsed for use.
	 * 
	 * @param input
	 *            Input data input as a TextField
	 * @param message
	 *            Input data as a String
	 * @return Returns either True or False based on whether or not the data is
	 *         an integer
	 */
	public static boolean isInt(TextField input, String message) {
		try {
			int ID = Integer.parseInt(input.getText());
			System.out.println(ID);
			return true;
		} catch (NumberFormatException e) {
			System.out.println("Error: " + message + " is not a number");
			return false;
		}
	}

	/**
	 * Converts the {@link ArrayList} from the {@link csvFileReader} class into
	 * an {@link ObservableList}
	 * 
	 * @param file
	 *            Links the main item File
	 * @param Search
	 *            The search query that will determine what data is returned to
	 *            the tables
	 * @return Returns the new ObservableList of Data
	 */
	public static ObservableList<Item> getItems(String file, String Search) {
		ObservableList<Item> items = FXCollections.observableArrayList();
		csvFileReader read = new csvFileReader();
		ItemManager manage = new ItemManager();
		if (Search.equals("")) {
			ArrayList<Item> list = read.getData(file);
			for (int i = 0; i < list.size(); i++) {
				items.add(list.get(i));
			}
		} else {
			ArrayList<Item> list = manage.searchFor(file, Search);
			for (int i = 0; i < list.size(); i++) {
				items.add(list.get(i));
			}
		}
		return items;
	}

	/**
	 * Deletes the selected file from the table. Takes the data in and removes
	 * it from both the table and the file itself.
	 * 
	 * @param table
	 *            Import the table in which you need to delete an item from.
	 * @param file
	 *            Links the main item File
	 */
	public static void deleteButtonClicked(TableView<Item> table, String file) {
		csvFileReader read = new csvFileReader();
		csvFileWriter write = new csvFileWriter();
		ObservableList<Item> productSelected, allProducts;
		allProducts = table.getItems();
		productSelected = table.getSelectionModel().getSelectedItems();
		productSelected.forEach(allProducts::remove);

		// create array
		ArrayList<Item> items = read.getData(file);
		items.clear();
		for (int i = 0; i < allProducts.size(); i++) {
			items.add(allProducts.get(i));
		}

		// rewrite file
		write.enterData(items);
		write.writeCsvFile(file);
	}

	public static TableColumn<Item, String> referenceColumn() {
		// reference column
		TableColumn<Item, String> referenceColumn = new TableColumn<>("Reference");
		referenceColumn.setMinWidth(80);
		referenceColumn.setCellValueFactory(new PropertyValueFactory<>("reference"));
		return referenceColumn;
	}
	
	public static TableColumn<Item, String> nameColumn() {
		// name column
		TableColumn<Item, String> nameColumn = new TableColumn<>("Item Name");
		nameColumn.setMinWidth(250);
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		return nameColumn;
	}
	
	public static TableColumn<Item, String> availableColumn() {
		// available column
		TableColumn<Item, String> availableColumn = new TableColumn<>("Available");
		availableColumn.setMinWidth(80);
		availableColumn.setCellValueFactory(new PropertyValueFactory<>("available"));
		return availableColumn;
	}
	
	public static TableColumn<Item, String> IDColumn() {
		// ID column
		TableColumn<Item, String> IDColumn = new TableColumn<>("ID");
		IDColumn.setMinWidth(150);
		IDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
		return IDColumn;
	}
	
	public static TableColumn<Item, String> permColumn() {
		// Permission column
		TableColumn<Item, String> permColumn = new TableColumn<>("Permission");
		permColumn.setMinWidth(100);
		permColumn.setCellValueFactory(new PropertyValueFactory<>("permission"));
		return permColumn;
	}
}
