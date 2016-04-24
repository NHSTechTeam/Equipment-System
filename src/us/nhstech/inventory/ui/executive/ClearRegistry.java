package us.nhstech.inventory.ui.executive;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import us.nhstech.inventory.ui.UIManager;
import us.nhstech.inventory.utils.ItemManager;

/**
 * @author James Sonne & Devin Matte
 * @version v0.5-Alpha
 * @since 2016-02-11
 */
public class ClearRegistry {
    /**
     * Clear the Item Registry
     *
     * @param file Links the main item File
     */
    public static void clear(String file) {
        ItemManager manage = new ItemManager();
        Label errors = new Label();
        Stage window = new Stage();
        VBox clearLayout = new VBox(10);
        Scene clear = new Scene(clearLayout, 250, 250);
        Label label = new Label();

        UIManager.windowBasic(window, "Clear Registry", 250, label, errors, clear);

        // Clear File
        Label clearLabel = new Label(), clearStatus = new Label();
        clearLabel.setText("Clear Item Registry. This CANNOT be Undone!");
        ComboBox<String> YN;
        YN = new ComboBox<>();
        YN.getItems().addAll("Yes I want to wipe the Registry", "No this was a mistake");
        YN.setPromptText("Wipe the Registry?");
        Button delete = new Button("Continue");
        Button clearBack = new Button("Back");
        clearBack.setOnAction(e -> {
            errors.setText("");
            window.close();
        });
        delete.setOnAction(e -> {
            errors.setText("");
            if ((YN.getValue()).equals("Yes I want to wipe the Registry")) {
                manage.clearFile(file);
                clearStatus.setText("Registry Cleared");
                window.close();
            } else {
                clearStatus.setText("Registry NOT Cleared");
            }
        });

        clearLayout.getChildren().addAll(clearLabel, clearBack, clearStatus, errors, YN, delete);
        clearLayout.setAlignment(Pos.CENTER);

        // Display window and wait for it to be closed before returning
        window.setScene(clear);
        window.showAndWait();
    }
}
