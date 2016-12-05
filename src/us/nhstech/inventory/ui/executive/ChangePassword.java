package us.nhstech.inventory.ui.executive;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import us.nhstech.inventory.ui.UIManager;
import us.nhstech.inventory.utils.csvFileWriter;

/**
 * @author Devin Matte
 * @author James Sonne
 * @version v0.2-Beta
 * @since 2016-02-11
 */

class ChangePassword {
    /**
     * Change the Executive Password
     *
     * @param passFile Links to the password file
     */
    public static void changePass(String passFile) {
        csvFileWriter write = new csvFileWriter();
        Label errors = new Label();
        Stage window = new Stage();
        VBox changeLayout = new VBox(10);
        Scene change = new Scene(changeLayout, 250, 250);
        Label label = new Label(), oldpass = new Label(), newpass = new Label(), confirmnewpass = new Label();

        UIManager.windowBasic(window, "Change Password", 250, label, errors, change);

        oldpass.setText("Current Password");
        newpass.setText("New Password");
        confirmnewpass.setText("Confirm New Password");
        PasswordField oldPass = new PasswordField();
        oldPass.setPromptText("Current Password");
        PasswordField newPass = new PasswordField();
        newPass.setPromptText("New Password");
        PasswordField confirmNewPass = new PasswordField();
        confirmNewPass.setPromptText("Confirm New Password");
        Button Submit = new Button("Submit");
        Button Back = new Button("Back");
        Back.setOnAction(e -> {
            errors.setText("");
            window.close();
        });
        Submit.setOnAction(e -> {
            errors.setText("");
            if ((newPass.getText()).equals(confirmNewPass.getText())) {
                write.newPass(newPass.getText(), passFile);
                window.close();
            } else {
                errors.setText("Passwords do not Match. Try Again");
            }
        });

        changeLayout.getChildren().addAll(label, errors, oldpass, oldPass, newpass, newPass, confirmnewpass, confirmNewPass, Submit, Back);
        changeLayout.setAlignment(Pos.CENTER);

        // Display window and wait for it to be closed before returning
        window.setScene(change);
        window.showAndWait();
    }
}
