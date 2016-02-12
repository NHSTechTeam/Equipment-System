package Interface.Executive;

import DataRecorderAndRetriever.*;
import Interface.*;
import java.util.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
/**
 * 
 * @author James Sonne & Devin Matte
 * @version v0.5-Alpha
 * @since 2016-02-11
 */
public class Registry {
	/**
	 * Manage the Item Registry
	 * 
	 * @param file
	 *            Links the main item File
	 */
	public static void registry(String file) {
		ItemManager manage = new ItemManager();
		Label errors = new Label();
		Stage window = new Stage();
		VBox registryLayout = new VBox(10);
		Scene registry = new Scene(registryLayout, 250, 250);
		TableView<Item> table;
		Label label = new Label();
		TextField search = new TextField();
		search.setPromptText("Search");

		UIManager.windowBasic(window, "Item Manager", 700, label, errors, registry);

		manage.searchFor(file, search.getText());

		// Name input
		TextField itemName = new TextField();
		itemName.setPromptText("Item Name");
		itemName.setMinWidth(200);
		itemName.setMaxWidth(275);

		// ID input
		TextField RegitemID = new TextField();
		RegitemID.setPromptText("ID Number");
		RegitemID.setMinWidth(75);
		RegitemID.setMaxWidth(125);

		ComboBox<String> executiveAP;
		executiveAP = new ComboBox<>();
		executiveAP.getItems().addAll("True", "False");
		executiveAP.setValue("False");
		
		table = new TableView<>();
		table.setItems(UIManager.getItems(file, search.getText()));
		table.setMaxWidth(700);
		table.getColumns().addAll(UIManager.referenceColumn(), UIManager.nameColumn(), UIManager.availableColumn(), UIManager.IDColumn(), UIManager.permColumn());

		Button deleteButton = new Button("Delete");
		deleteButton.setOnAction(e -> {
			UIManager.deleteButtonClicked(table, file);
		});

		search.setOnKeyPressed(e -> {
			table.setItems(UIManager.getItems(file, search.getText()));
		});

		// Button
		Button addButton = new Button("Add");
		addButton.setOnAction(e -> {
			errors.setText("");
			if ((UIManager.isInt(RegitemID, RegitemID.getText())) && !(itemName.equals(""))) {
				manage.register(new Scanner(RegitemID.getText()).nextInt(), itemName.getText(),
						Boolean.parseBoolean(executiveAP.getValue()), file);
				RegitemID.clear();
				itemName.clear();
				table.setItems(UIManager.getItems(file,search.getText()));

			} else {
				errors.setText("Something Went Wrong. Try Again");
				RegitemID.clear();
				itemName.clear();
			}
		});

		HBox menu = new HBox();
		menu.setPadding(new Insets(10, 10, 10, 10));
		menu.setSpacing(10);
		menu.setAlignment(Pos.CENTER);
		menu.getChildren().addAll(RegitemID, itemName, executiveAP, addButton, deleteButton);

		// Back Button
		Button fileBack = new Button("Back");
		fileBack.setOnAction(e -> {
			table.setItems(UIManager.getItems(file, search.getText()));
			errors.setText("");
			window.close();
		});

		registryLayout.getChildren().addAll(label, search, table, menu, fileBack);
		registryLayout.setAlignment(Pos.CENTER);

		// Display window and wait for it to be closed before returning
		window.setScene(registry);
		window.showAndWait();
	}
}
