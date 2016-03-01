package us.nhstech.inventory;

import java.io.File;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import us.nhstech.inventory.logging.Checkin;
import us.nhstech.inventory.logging.Checkout;
import us.nhstech.inventory.ui.UIManager;
import us.nhstech.inventory.ui.executive.ExecutiveMain;
import us.nhstech.inventory.utils.Item;
import us.nhstech.inventory.utils.ItemManager;
import us.nhstech.inventory.utils.csvFileWriter;

/**
 * 
 * @author James Sonne & Devin Matte
 * @version v0.5-Alpha
 * @since 2016-02-11
 */

public class Main extends Application {

    // Added private modifier. Unless you specifically know why, always use
    // public or private on your variables.
    // Having no modifier means that it is package protected. In this instance
    // it doesn't matter as there are no other classes in this package, but it
    // is a really good practice to get into.
    private ItemManager manage = new ItemManager();
    private Stage window;

    // I removed the instance variables Buttons, and moved them to the method.
    // For three of them, this was already being done. Unless you need to use
    // the variables in every method, you should try to keep variables local.
    // This is because the variables will be cleaned up faster than if you leave
    // them local.

    public static void main(String[] args) {
        launch(args);
    }

    // In your method, the table.getColumns().addAll() contains unchecked
    // variable types. This can cause things to fail. I will allow you to poke
    // through that to figure it out. This @Suppress just tells your IDE not to
    // underline everything in yellow. It is a good idea to leave it like this
    // unless you have intentions to actually fix it.
    @SuppressWarnings("unchecked")
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setMaximized(true);
        window.setTitle("Equipment Manager");
        Button executive = new Button("Executive Options");
        TextField search = new TextField();
        search.setPromptText("Search");
        TableView<Item> table;

        // create filenames
        String fileName = System.getProperty("user.home") + "/item.csv";
        String passFileName = System.getProperty("user.home") + "/notoflookinghere.csv";
        String IDFileName = System.getProperty("user.home") + "/memberlist.csv";
        String logFileName = System.getProperty("user.home") + "/Log.csv";
        File x = new File(fileName);
        // if no file exists with given names, create default files
        if (!x.exists()) {
            csvFileWriter write = new csvFileWriter();
            write.writeCsvFile(logFileName);
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

        table = new TableView<>();
        table.setItems(UIManager.getItems(fileName, search.getText()));
        table.setMaxWidth(675);
        table.getColumns().addAll(UIManager.referenceColumn(), UIManager.nameColumn(), UIManager.availableColumn(), UIManager.IDColumn(), UIManager.permColumn());

        Button checkout = new Button("Checkout");
        checkout.setOnAction(e -> {
            Checkout.checkout(fileName, IDFileName, passFileName);
            table.setItems(UIManager.getItems(fileName, search.getText()));
        });

        Button checkin = new Button("Checkin");
        checkin.setOnAction(e -> {
            Checkin.checkin(fileName);
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

        executive.setOnAction(e -> ExecutiveMain.executive(fileName, passFileName, logFileName, IDFileName));

        VBox layout = new VBox(10);
        layout.getChildren().addAll(search, table, menu, executive);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 300, 250);
        scene.getStylesheets().add("style.css");
        window.setScene(scene);
        window.show();
    }
}
