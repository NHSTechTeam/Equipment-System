package us.nhstech.inventory.ui.executive;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import us.nhstech.inventory.ui.UIManager;
import us.nhstech.inventory.utils.ItemManager;
import us.nhstech.inventory.utils.Log;

/**
 * @author James Sonne & Devin Matte
 * @version v0.5-Alpha
 * @since 2016-02-11
 */
public class LogView {
    /**
     * View the Log of checkouts/checkins. One note is if an item was checked
     * in, it will read true, and checked out will read false
     *
     * @param log
     */
    public static void log(String log) {
        ItemManager manage = new ItemManager();
        Label errors = new Label();
        Stage window = new Stage();
        VBox logLayout = new VBox(10);
        Scene Log = new Scene(logLayout, 250, 250);
        TextField search = new TextField();
        search.setPromptText("Search");
        TableView<Log> table;
        Label label = new Label();

        UIManager.windowBasic(window, "Checkout Log", 700, label, errors, Log);

        manage.searchFor(log, search.getText());

        table = new TableView<>();
        table.setItems(UIManager.getLog(log, search.getText()));
        table.setMaxWidth(675);
        table.getColumns().addAll(UIManager.refColumn(), UIManager.nameColumnLog(), UIManager.inColumn(), UIManager.personColumn(), UIManager.timeColumn());

        search.setOnKeyPressed(e -> {
            table.setItems(UIManager.getLog(log, search.getText()));
        });

        Button logBack = new Button("Back");
        logBack.setOnAction(e -> {
            table.setItems(UIManager.getLog(log, search.getText()));
            errors.setText("");
            window.close();
        });

        logLayout.getChildren().addAll(search, table, logBack);
        logLayout.setAlignment(Pos.CENTER);
        window.setScene(Log);
        window.show();
    }
}
