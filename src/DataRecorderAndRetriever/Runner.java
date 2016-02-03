package DataRecorderAndRetriever;

import java.io.File;
import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//import java.util.ArrayList;

/**
 * 
 * @author James & Devin
 *
 */

public class Runner extends Application {

	Stage window;
	Button checkout, checkin, show, executive;

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setMaximized(true);
		window.setTitle("Equipment Manager");
		checkout = new Button("Checkout");
		checkin = new Button("CheckIn");
		show = new Button("Show Item Status");
		executive = new Button("Executive Options");

		// create filenames
		String fileName = System.getProperty("user.home") + "/item.csv";
		String passFileName = System.getProperty("user.home") + "/sneakingprecious.csv";
		String IDFileName = System.getProperty("user.home") + "/memberlist.csv";
		File x = new File(fileName);
		// if no file exists with given names, create default files
		if (!x.exists()) {
			csvFileWriter write = new csvFileWriter();
			write.writeCsvFile(fileName);
			write.writeCsvFile(IDFileName);
			ArrayList<Item> list = new ArrayList<Item>();
			list.add(new Item(1000, "password", true, "none"));
			write.enterData(list);
			write.writeCsvFile(passFileName);
		} else {
			System.out.println("Nope");
		}

		checkout.setOnAction(e -> UI.checkout(fileName, IDFileName));
		checkin.setOnAction(e -> UI.checkin(fileName));
		show.setOnAction(e -> UI.show(fileName));
		executive.setOnAction(e -> UI.executive(fileName, passFileName));

		VBox layout = new VBox(10);
		layout.getChildren().addAll(checkout, checkin, show, executive);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout, 300, 250);
		window.setScene(scene);
		window.show();
	}
}
