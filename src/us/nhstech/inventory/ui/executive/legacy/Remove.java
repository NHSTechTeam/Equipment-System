package us.nhstech.inventory.ui.executive.legacy;

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

import java.util.Scanner;

/**
 * @author James Sonne & Devin Matte
 * @version v0.1-Beta
 * @since 2016-02-11
 */

public class Remove {

    /**
     * Remove an Item from the Registry
     *
     * @param file Links the main item File
     */
    public static void remove(String file) {
        ItemManager manage = new ItemManager();
        Label errors = new Label();
        Stage window = new Stage();
        VBox removeLayout = new VBox(10);
        Scene remove = new Scene(removeLayout, 250, 250);
        Label label = new Label();

        UIManager.windowBasic(window, "Remove Item", 250, label, errors, remove);

        // Remove
        TextField RemitemID = new TextField();
        RemitemID.setPromptText("Item ID");
        Button RemoveSubmit = new Button("Submit");
        Button removeBack = new Button("Back");
        removeBack.setOnAction(e -> {
            errors.setText("");
            window.close();
        });
        RemoveSubmit.setOnAction(e -> {
            errors.setText("");
            if (UIManager.isInt(RemitemID, RemitemID.getText())) {
                manage.remove(new Scanner(RemitemID.getText()).nextInt(), file);
                RemitemID.clear();
                window.close();
            } else {
                errors.setText("Something Went Wrong. Try Again");
            }
        });
        RemitemID.setOnKeyPressed(e -> {
            errors.setText("");
            if (e.getCode().equals(KeyCode.ENTER)) {
                if (UIManager.isInt(RemitemID, RemitemID.getText())) {
                    manage.remove(new Scanner(RemitemID.getText()).nextInt(), file);
                    RemitemID.clear();
                    window.close();
                } else {
                    errors.setText("Something Went Wrong. Try Again");
                }
            }
        });

        removeLayout.getChildren().addAll(label, errors, RemitemID, RemoveSubmit, removeBack);
        removeLayout.setAlignment(Pos.CENTER);

        // Display window and wait for it to be closed before returning
        window.setScene(remove);
        window.showAndWait();
    }
}
