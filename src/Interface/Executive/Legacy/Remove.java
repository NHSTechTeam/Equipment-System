package Interface.Executive.Legacy;

import DataRecorderAndRetriever.*;
import Interface.*;
import java.util.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.*;
/**
 * 
 * @author James Sonne & Devin Matte
 * @version v0.5-Alpha
 * @since 2016-02-11
 */
public class Remove {

	/**
	 * Remove an Item from the Registry
	 * 
	 * @param file
	 *            Links the main item File
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
