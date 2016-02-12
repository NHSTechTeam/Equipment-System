package Interface.Executive;

import DataRecorderAndRetriever.*;
import Interface.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
/**
 * 
 * @author James Sonne & Devin Matte
 * @version v0.5-Alpha
 * @since 2016-02-11
 */
public class ChangePassword {
	/**
	 * Change the Executive Password
	 * 
	 * @param file
	 *            Links the main item File
	 * @param passFile
	 *            Links to the password file
	 */
	public static void changePass(String file, String passFile) {
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
