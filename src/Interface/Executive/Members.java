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
public class Members {
	public static void memberManager(String file) {
		ItemManager manage = new ItemManager();
		Label errors = new Label();
		Stage window = new Stage();
		VBox membersLayout = new VBox(10);
		Scene members = new Scene(membersLayout, 250, 250);
		TableView<Item> table;
		Label label = new Label();
		TextField search = new TextField();
		search.setPromptText("Search");

		UIManager.windowBasic(window, "Member Manager", 600, label, errors, members);

		manage.searchFor(file, search.getText());

		// Name input
		TextField itemName = new TextField();
		itemName.setPromptText("Member Name");
		itemName.setMinWidth(150);
		itemName.setMaxWidth(300);

		// ID input
		TextField RegitemID = new TextField();
		RegitemID.setPromptText("ID Number");
		RegitemID.setMinWidth(25);
		RegitemID.setMaxWidth(50);
		
		table = new TableView<>();
		table.setItems(UIManager.getItems(file, search.getText()));
		table.setMaxSize(345, 300);
		table.getColumns().addAll(UIManager.referenceColumn(), UIManager.nameColumn());

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
				manage.addMember(file, new Scanner(RegitemID.getText()).nextInt(), itemName.getText());
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
		menu.getChildren().addAll(RegitemID, itemName, addButton, deleteButton);

		// Back Button
		Button fileBack = new Button("Back");
		fileBack.setOnAction(e -> {
			table.setItems(UIManager.getItems(file, search.getText()));
			errors.setText("");
			window.close();
		});

		membersLayout.getChildren().addAll(label, search, table, menu, fileBack);
		membersLayout.setAlignment(Pos.CENTER);

		// Display window and wait for it to be closed before returning
		window.setScene(members);
		window.showAndWait();
	}
}
