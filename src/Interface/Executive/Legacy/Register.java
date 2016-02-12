package Interface.Executive.Legacy;

import DataRecorderAndRetriever.*;
import Interface.*;
import java.util.*;
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
public class Register {
	
	/**
	 * Register an Item to the Registry
	 * 
	 * @param file
	 *            Links the main item File
	 */
	public static void register(String file) {
		ItemManager manage = new ItemManager();
		Label errors = new Label();
		Stage window = new Stage();
		VBox registerLayout = new VBox(10);
		Scene register = new Scene(registerLayout, 250, 250);
		Label label = new Label();

		UIManager.windowBasic(window, "Register Item", 250, label, errors, register);

		// Register
		TextField itemName = new TextField();
		itemName.setPromptText("Name of Item");
		TextField RegitemID = new TextField();
		RegitemID.setPromptText("Item ID");
		Button RegisterSubmit = new Button("Submit");
		Button registerBack = new Button("Back");
		ComboBox<String> executiveAP;
		executiveAP = new ComboBox<>();
		executiveAP.getItems().addAll("True", "False");
		executiveAP.setPromptText("Executive Approval");
		registerBack.setOnAction(e -> {
			errors.setText("");
			window.close();
		});
		RegisterSubmit.setOnAction(e -> {
			errors.setText("");
			if ((UIManager.isInt(RegitemID, RegitemID.getText())) && !(itemName.equals(""))) {
				manage.register(new Scanner(RegitemID.getText()).nextInt(), itemName.getText(),
						Boolean.parseBoolean(executiveAP.getValue()), file);
				RegitemID.clear();
				itemName.clear();
				window.close();
			} else {
				errors.setText("Something Went Wrong. Try Again");
				RegitemID.clear();
				itemName.clear();
			}
		});

		registerLayout.getChildren().addAll(label, errors, RegitemID, itemName, executiveAP, RegisterSubmit,
				registerBack);
		registerLayout.setAlignment(Pos.CENTER);

		// Display window and wait for it to be closed before returning
		window.setScene(register);
		window.showAndWait();
	}
}
