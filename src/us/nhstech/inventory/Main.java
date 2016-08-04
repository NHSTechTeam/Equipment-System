package us.nhstech.inventory;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import us.nhstech.inventory.logging.CheckIn;
import us.nhstech.inventory.logging.CheckOut;
import us.nhstech.inventory.ui.UIManager;
import us.nhstech.inventory.ui.executive.ExecutiveMain;
import us.nhstech.inventory.utils.Item;
import us.nhstech.inventory.utils.ItemManager;
import us.nhstech.inventory.utils.csvFileWriter;
import us.nhstech.inventory.utils.databaseManager;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * @author James Sonne & Devin Matte
 * @version v0.5-Alpha
 * @since 2016-02-11
 */

public class Main extends Application {

    private ItemManager manage = new ItemManager();
    private Stage window;

    public static void main(String[] args) {
        launch(args);
        try {
            databaseManager.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
            CheckOut.checkOut(fileName, IDFileName, passFileName);
            table.setItems(UIManager.getItems(fileName, search.getText()));
        });

        Button checkin = new Button("Checkin");
        checkin.setOnAction(e -> {
            CheckIn.checkIn(fileName);
            table.setItems(UIManager.getItems(fileName, search.getText()));
        });

        search.setOnKeyPressed(e -> {
            table.setItems(UIManager.getItems(fileName, search.getText()));
        });

        MenuBar menuBar = new MenuBar();
        Menu help = new Menu("_Help");
        MenuItem code = new MenuItem("View Code");
        MenuItem report = new MenuItem("Report a Bug");
        help.getItems().addAll(code, report);
        menuBar.getMenus().addAll(help);
        URI codelink = new URI("https://github.com/NHSTechTeam/Equipment-System");
        URI reportlink = new URI("https://github.com/NHSTechTeam/Equipment-System/issues");
        code.setOnAction(e -> UIManager.openWebpage(codelink));
        report.setOnAction(e -> UIManager.openWebpage(reportlink));

        HBox menu = new HBox();
        menu.setPadding(new Insets(10, 10, 10, 10));
        menu.setSpacing(10);
        menu.setAlignment(Pos.CENTER);
        menu.getChildren().addAll(checkout, checkin);

        executive.setOnAction(e -> {
            try {
                ExecutiveMain.executive(fileName, passFileName, logFileName, IDFileName);
            } catch (URISyntaxException e1) {
                e1.printStackTrace();
            }
        });

        BorderPane borderLayout = new BorderPane();
        VBox layout = new VBox(10);
        borderLayout.setTop(menuBar);
        borderLayout.setCenter(layout);
        layout.getChildren().addAll(search, table, menu, executive);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(borderLayout, 300, 250);
        scene.getStylesheets().add("style.css");
        window.setScene(scene);
        window.show();
    }
}
