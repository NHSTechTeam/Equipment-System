package DataRecorderAndRetriever;

import javafx.scene.input.KeyCode;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

/**
 * 
 * @author James & Devin
 *
 */

public class UI {
	static ItemManager manage = new ItemManager();
	static Label errors = new Label();
	static int checkoutItemInput;

	/**
	 * Checkout Window
	 * 
	 * @param file
	 *            Links the main item File
	 * @param IDfile
	 *            Links the Memberlist File
	 */
	public static void checkout(String file, String IDfile) {
		Stage window = new Stage();
		VBox checkoutLayout = new VBox(10);
		Scene checkout = new Scene(checkoutLayout);
		checkout.getStylesheets().add("style.css");
		Label label = new Label();

		UIManager.windowBasic(window, "Equipment Checkout", 250, label, errors);

		TextField itemInput = new TextField();
		itemInput.setPromptText("Item Barcode");

		Button button = new Button("Next Step");
		button.setOnAction(e -> {
			errors.setText("");
			if (UIManager.isInt(itemInput, itemInput.getText())) {
				checkoutItemInput = Integer.parseInt(itemInput.getText());
				itemInput.clear();
				checkoutID(file, IDfile);
				window.close();
			} else {
				errors.setText("Something Went Wrong. Try Again");
				itemInput.clear();
			}
		});
		itemInput.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.ENTER)) {
				errors.setText("");
				if (UIManager.isInt(itemInput, itemInput.getText())) {
					checkoutItemInput = Integer.parseInt(itemInput.getText());
					itemInput.clear();
					checkoutID(file, IDfile);
					window.close();
				} else {
					errors.setText("Something Went Wrong. Try Again");
					itemInput.clear();
				}
			}
		});

		checkoutLayout.getChildren().addAll(label, errors, itemInput, button);
		checkoutLayout.setAlignment(Pos.CENTER);

		// Display window and wait for it to be closed before returning
		window.setScene(checkout);
		window.showAndWait();
	}

	/**
	 * CheckoutID Window
	 * 
	 * @param file
	 *            Links the main item File
	 * @param IDfile
	 *            Links the Memberlist File
	 */
	public static void checkoutID(String file, String IDfile) {
		Stage window = new Stage();
		VBox checkoutLayout = new VBox(10);
		Scene checkout = new Scene(checkoutLayout);
		checkout.getStylesheets().add("style.css");
		Label label = new Label();

		UIManager.windowBasic(window, "Equipment Checkout", 250, label, errors);

		TextField IDInput = new TextField();
		IDInput.setPromptText("Member ID");

		Button button = new Button("Check Out");
		button.setOnAction(e -> {
			errors.setText("");
			if (UIManager.isInt(IDInput, IDInput.getText())) {
				manage.checkOut(checkoutItemInput, file, IDfile, Integer.parseInt(IDInput.getText()));
				IDInput.clear();
				window.close();
			} else {
				errors.setText("Something Went Wrong. Try Again");
				IDInput.clear();
			}
		});
		IDInput.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.ENTER)) {
				errors.setText("");
				if (UIManager.isInt(IDInput, IDInput.getText())) {
					manage.checkOut(checkoutItemInput, file, IDfile, Integer.parseInt(IDInput.getText()));
					IDInput.clear();
					window.close();
				} else {
					errors.setText("Something Went Wrong. Try Again");
					IDInput.clear();
				}
			}
		});

		checkoutLayout.getChildren().addAll(label, errors, IDInput, button);
		checkoutLayout.setAlignment(Pos.CENTER);

		// Display window and wait for it to be closed before returning
		window.setScene(checkout);
		window.showAndWait();
	}

	/**
	 * Check-In Window
	 * 
	 * @param file
	 *            Links the main item File
	 */
	public static void checkin(String file) {
		Stage window = new Stage();
		VBox checkinLayout = new VBox(10);
		Scene checkin = new Scene(checkinLayout);
		checkin.getStylesheets().add("style.css");
		Label label = new Label();

		UIManager.windowBasic(window, "Equipment Checkin", 250, label, errors);

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
