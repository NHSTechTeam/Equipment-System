package us.nhstech.inventory.logging;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import us.nhstech.inventory.ui.UIManager;
import us.nhstech.inventory.utils.ItemManager;

/**
 * 
 * @author James Sonne & Devin Matte
 * @version v0.5-Alpha
 * @since 2016-02-11
 */
public class CheckIn {

    /**
     * Check-In Window
     * 
     * @param file
     *            Links the main item File
     */
    public static void checkIn(String file) {
        ItemManager manage = new ItemManager();
        Label errors = new Label();
        Stage window = new Stage();
        VBox checkinLayout = new VBox(10);
        Scene checkin = new Scene(checkinLayout);
        Label label = new Label();

        UIManager.windowBasic(window, "Equipment Checkin", 250, label, errors, checkin);

        TextField itemInput = new TextField();
        itemInput.setPromptText("Item Barcode");

        Button button = new Button("Check In");
        button.setOnAction(e -> {
            errors.setText("");
            if (UIManager.isInt(itemInput, itemInput.getText())) {
                manage.checkIn(Integer.parseInt(itemInput.getText()), file);
                itemInput.clear();
                window.close();
            } else {
                errors.setText("Something Went Wrong. Try Again");
                itemInput.clear();
            }
        });
        itemInput.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                if (UIManager.isInt(itemInput, itemInput.getText())) {
                    manage.checkIn(Integer.parseInt(itemInput.getText()), file);
                    itemInput.clear();
                    window.close();
                } else {
                    errors.setText("Something Went Wrong. Try Again");
                    itemInput.clear();
                }
            }
        });

        checkinLayout.getChildren().addAll(label, errors, itemInput, button);
        checkinLayout.setAlignment(Pos.CENTER);

        // Display window and wait for it to be closed before returning
        window.setScene(checkin);
        window.showAndWait();
    }
}
