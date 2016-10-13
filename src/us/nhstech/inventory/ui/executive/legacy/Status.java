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

public class Status {
    /**
     * Show Item Status Window
     *
     * @param file Links the main item File
     */
    public static void show(String file) {
        ItemManager manage = new ItemManager();
        Label errors = new Label();
        Stage window = new Stage();
        VBox showLayout = new VBox(10), show2Layout = new VBox(10);
        Scene show = new Scene(showLayout, 250, 250), show2 = new Scene(show2Layout, 500, 150);
        show2.getStylesheets().add("style.css");
        Label label = new Label();

        UIManager.windowBasic(window, "Equipment Status", 250, label, errors, show);

        // Show Item Status
        Label showStatus = new Label();
        TextField showitemID = new TextField();
        showitemID.setPromptText("Item ID");
        Button ShowSubmit = new Button("Submit");
        ShowSubmit.setOnAction(e -> {
            errors.setText("");
            if (UIManager.isInt(showitemID, showitemID.getText())) {
                manage.getItem(new Scanner(showitemID.getText()).nextInt(), file);
                showStatus.setText(manage.getItem(new Scanner(showitemID.getText()).nextInt(), file).toString());
                showitemID.clear();
                window.setScene(show2);
            } else {
                errors.setText("Something Went Wrong. Try Again");
            }
        });
        showitemID.setOnKeyPressed(e -> {
            errors.setText("");
            if (e.getCode().equals(KeyCode.ENTER)) {
                if (UIManager.isInt(showitemID, showitemID.getText())) {
                    manage.getItem(new Scanner(showitemID.getText()).nextInt(), file);
                    showStatus.setText(manage.getItem(new Scanner(showitemID.getText()).nextInt(), file).toString());
                    showitemID.clear();
                    window.setScene(show2);
                } else {
                    errors.setText("Something Went Wrong. Try Again");
                }
            }
        });

        Button Back = new Button("Back");
        Back.setOnAction(e -> {
            errors.setText("");
            window.close();
        });
        Button twoBack = new Button("Back");
        twoBack.setOnAction(e -> {
            errors.setText("");
            window.setScene(show);
        });

        showLayout.getChildren().addAll(label, errors, showitemID, ShowSubmit, Back);
        showLayout.setAlignment(Pos.CENTER);

        show2Layout.getChildren().addAll(showStatus, twoBack);
        show2Layout.setAlignment(Pos.CENTER);

        // Display window and wait for it to be closed before returning
        window.setScene(show);
        window.showAndWait();
    }
}
