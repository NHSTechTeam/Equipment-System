package Interface.CheckInOut;

import DataRecorderAndRetriever.*;
import Interface.*;
import javafx.scene.input.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.layout.*;

/**
 * 
 * @author James Sonne & Devin Matte
 * @version v0.5-Alpha
 * @since 2016-02-11
 */
public class Checkin {

	/**
	 * Check-In Window
	 * 
	 * @param file
	 *            Links the main item File
	 */
	public static void checkin(String file) {
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
