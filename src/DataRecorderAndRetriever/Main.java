package DataRecorderAndRetriever;

import java.io.File;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * 
 * @author James Sonne & Devin Matte
 * @version v0.3-Alpha
 * @since 2016-02-06
 */

public class Main extends Application {
	ItemManager manage = new ItemManager();
	Stage window;
	Button checkout, checkin, show, executive;

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setMaximized(true);
		window.setTitle("Equipment Manager");
		executive = new Button("Executive Options");
		TextField search = new TextField();
		search.setPromptText("Search");
		TableView<Item> table;

		// create filenames
		String fileName = System.getProperty("user.home") + "/item.csv";
		String passFileName = System.getProperty("user.home") + "/notoflookinghere.csv";
		String IDFileName = System.getProperty("user.home") + "/memberlist.csv";
		File x = new File(fileName);
		// if no file exists with given names, create default files
		if (!x.exists()) {
			csvFileWriter write = new csvFileWriter();
			write.writeCsvFile(fileName);
			write.writeCsvFile(IDFileName);
			ArrayList<Item> list = new ArrayList<Item>();
			list.add(new Item(1000, "password", true, "none", false));
			write.enterData(list);
			write.writeCsvFile(passFileName);
		} else {
			System.out.println("Files Read");
		}
		
		manage.searchFor(fileName, search.getText());
		
		// reference column
		TableColumn<Item, String> referenceColumn = new TableColumn<>("Reference");
		referenceColumn.setMinWidth(80);
		referenceColumn.setCellValueFactory(new PropertyValueFactory<>("reference"));

		// name column
		TableColumn<Item, Double> nameColumn = new TableColumn<>("Item Name");
		nameColumn.setMinWidth(250);
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

		// available column
		TableColumn<Item, String> availableColumn = new TableColumn<>("Available");
		availableColumn.setMinWidth(80);
		availableColumn.setCellValueFactory(new PropertyValueFactory<>("available"));

		// ID column
		TableColumn<Item, String> IDColumn = new TableColumn<>("ID");
		IDColumn.setMinWidth(150);
		IDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));

		// Permission column
		TableColumn<Item, String> permColumn = new TableColumn<>("Permission");
		permColumn.setMinWidth(100);
		permColumn.setCellValueFactory(new PropertyValueFactory<>("permission"));

		table = new TableView<>();
		table.setItems(UIManager.getItems(fileName, search.getText()));
		table.setMaxWidth(675);
		table.getColumns().addAll(referenceColumn, nameColumn, availableColumn, IDColumn, permColumn);

		Button checkout = new Button("Checkout");
		checkout.setOnAction(e -> {
			UI.checkout(fileName, IDFileName, passFileName);
			table.setItems(UIManager.getItems(fileName, search.getText()));
		});

		Button checkin = new Button("Checkin");
		checkin.setOnAction(e -> {
			UI.checkin(fileName);
			table.setItems(UIManager.getItems(fileName, search.getText()));
		});
		
		search.setOnKeyPressed(e -> {
				table.setItems(UIManager.getItems(fileName, search.getText()));
		});

		HBox menu = new HBox();
		menu.setPadding(new Insets(10, 10, 10, 10));
		menu.setSpacing(10);
		menu.setAlignment(Pos.CENTER);
		menu.getChildren().addAll(checkout, checkin);
		
		executive.setOnAction(e -> UIExecutive.executive(fileName, passFileName));

		VBox layout = new VBox(10);
		layout.getChildren().addAll(search, table, menu, executive);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout, 300, 250);
		scene.getStylesheets().add("style.css");
		window.setScene(scene);
		window.show();
	}
}
