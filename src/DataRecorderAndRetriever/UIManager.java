package DataRecorderAndRetriever;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * 
 * @author James & Devin
 *
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

	// Is-Integer Check
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

	// Gets items for Tableview
	public static ObservableList<Item> getItems(String file) {
		ObservableList<Item> items = FXCollections.observableArrayList();
		csvFileReader read = new csvFileReader();
		ArrayList<Item> list = read.getData(file);
		for (int i = 0; i < list.size(); i++) {
			items.add(list.get(i));
		}
		return items;
	}

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

}
