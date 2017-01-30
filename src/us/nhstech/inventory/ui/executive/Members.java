package us.nhstech.inventory.ui.executive;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import us.nhstech.inventory.ui.UIManager;
import us.nhstech.inventory.utils.Item;
import us.nhstech.inventory.utils.ItemManager;

import java.util.Scanner;

/**
 * @author Devin Matte
 * @author James Sonne
 * @version v0.2-Beta
 * @since 2016-02-11
 */

class Members {
    @SuppressWarnings("unchecked")
	public static void memberManager(String file) {
        ItemManager manage = new ItemManager();
        Label errors = new Label();
        Stage window = new Stage();
        VBox membersLayout = new VBox(10);
        Scene members = new Scene(membersLayout, 600, 600);
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
        deleteButton.setOnAction(e -> UIManager.deleteButtonClicked(table, file));

        search.setOnKeyPressed(e -> table.setItems(UIManager.getItems(file, search.getText())));

        // Button
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            errors.setText("");
            if ((UIManager.isInt(RegitemID, RegitemID.getText())) && !(itemName.equals(""))) {
                manage.addMember(file, new Scanner(RegitemID.getText()).nextInt(), itemName.getText());
                RegitemID.clear();
                itemName.clear();
                table.setItems(UIManager.getItems(file, search.getText()));

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
