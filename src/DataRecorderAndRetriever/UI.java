package DataRecorderAndRetriever;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import java.util.Scanner;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * 
 * @author James & Devin
 *
 */

public class UI {
	static Label errors = new Label();

	// Check Out
	public static void checkout(String file, String IDfile) {
		Stage window = new Stage();
		VBox checkoutLayout = new VBox(10);
		Scene checkout = new Scene(checkoutLayout);
		ItemManager manage = new ItemManager();

		UIManager.windowBasic(window, "Equipment Checkout", 250);

		Label label = new Label();
		label.setText("Equipment Checkout");
		errors.setText("");

		TextField itemInput = new TextField();
		itemInput.setPromptText("Item Barcode");
		TextField IDInput = new TextField();
		IDInput.setPromptText("Member ID");

		Button button = new Button("Check Out");
		button.setOnAction(e -> {
			errors.setText("");
			if (UIManager.isInt(itemInput, itemInput.getText()) && UIManager.isInt(IDInput, IDInput.getText())) {
				manage.checkOut(Integer.parseInt(itemInput.getText()), file, IDfile,
						Integer.parseInt(IDInput.getText()));
				itemInput.clear();
				IDInput.clear();
				window.close();
			} else {
				errors.setText("Something Went Wrong. Try Again");
				itemInput.clear();
				IDInput.clear();
			}
		});

		checkoutLayout.getChildren().addAll(label, errors, itemInput, IDInput, button);
		checkoutLayout.setAlignment(Pos.CENTER);

		// Display window and wait for it to be closed before returning
		window.setScene(checkout);
		window.showAndWait();
	}

	// Check In
	public static void checkin(String file) {
		Stage window = new Stage();
		VBox checkinLayout = new VBox(10);
		Scene checkin = new Scene(checkinLayout);
		ItemManager manage = new ItemManager();

		UIManager.windowBasic(window, "Equipment Checkin", 250);

		Label label = new Label();
		label.setText("Equipment Checkin");
		errors.setText("");

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

		checkinLayout.getChildren().addAll(label, errors, itemInput, button);
		checkinLayout.setAlignment(Pos.CENTER);

		// Display window and wait for it to be closed before returning
		window.setScene(checkin);
		window.showAndWait();
	}

	// Show Status
	public static void show(String file) {
		Stage window = new Stage();
		VBox showLayout = new VBox(10), show2Layout = new VBox(10);
		Scene show = new Scene(showLayout, 250, 250), show2 = new Scene(show2Layout, 500, 150);
		ItemManager manage = new ItemManager();

		UIManager.windowBasic(window, "Equipment Status", 250);

		Label label = new Label();
		label.setText("Equipment Status");
		errors.setText("");

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

	// Register Item
	public static void register(String file) {
		Stage window = new Stage();
		VBox registerLayout = new VBox(10);
		Scene register = new Scene(registerLayout, 250, 250);
		ItemManager manage = new ItemManager();

		UIManager.windowBasic(window, "Register Item", 250);

		Label label = new Label();
		label.setText("Register an Item");
		errors.setText("");

		// Register
		TextField itemName = new TextField();
		itemName.setPromptText("Name of Item");
		TextField RegitemID = new TextField();
		RegitemID.setPromptText("Item ID");
		Button RegisterSubmit = new Button("Submit");
		Button registerBack = new Button("Back");
		registerBack.setOnAction(e -> {
			errors.setText("");
			window.close();
		});
		RegisterSubmit.setOnAction(e -> {
			errors.setText("");
			if ((UIManager.isInt(RegitemID, RegitemID.getText())) && !(itemName.equals(""))) {
				manage.register(new Scanner(RegitemID.getText()).nextInt(), itemName.getText(), file);
				RegitemID.clear();
				itemName.clear();
				window.close();
			} else {
				errors.setText("Something Went Wrong. Try Again");
				RegitemID.clear();
				itemName.clear();
			}
		});

		registerLayout.getChildren().addAll(label, errors, RegitemID, itemName, RegisterSubmit, registerBack);
		registerLayout.setAlignment(Pos.CENTER);

		// Display window and wait for it to be closed before returning
		window.setScene(register);
		window.showAndWait();
	}

	// Remove Item
	public static void remove(String file) {
		Stage window = new Stage();
		VBox removeLayout = new VBox(10);
		Scene remove = new Scene(removeLayout, 250, 250);
		ItemManager manage = new ItemManager();

		UIManager.windowBasic(window, "Remove Item", 250);

		Label label = new Label();
		label.setText("Remove Item from Registry");
		errors.setText("");

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

	// Show Registry
	public static void registry(String file) {
		Stage window = new Stage();
		VBox registryLayout = new VBox(10);
		Scene registry = new Scene(registryLayout, 250, 250);
		ItemManager manage = new ItemManager();
		TableView<Item> table;

		UIManager.windowBasic(window, "Show Registry", 750);

		Label label = new Label();
		label.setText("Show Registry");
		errors.setText("");

		// reference column
		TableColumn<Item, String> referenceColumn = new TableColumn<>("Reference");
		referenceColumn.setMinWidth(50);
		referenceColumn.setCellValueFactory(new PropertyValueFactory<>("reference"));

		// name column
		TableColumn<Item, Double> nameColumn = new TableColumn<>("Item Name");
		nameColumn.setMinWidth(250);
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

		// available column
		TableColumn<Item, String> availableColumn = new TableColumn<>("Available");
		availableColumn.setMinWidth(50);
		availableColumn.setCellValueFactory(new PropertyValueFactory<>("available"));

		// ID column
		TableColumn<Item, String> IDColumn = new TableColumn<>("ID");
		IDColumn.setMinWidth(150);
		IDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
		
		// Name input
		TextField itemName = new TextField();
		itemName.setPromptText("Item Name");
		itemName.setMinWidth(200);

		// Name input
		TextField RegitemID = new TextField();
		RegitemID.setPromptText("ID Number");
		RegitemID.setMaxWidth(125);
		
		ComboBox<String> executiveAP;
		executiveAP = new ComboBox<>();
		executiveAP.getItems().addAll("True", "False");
		executiveAP.setPromptText("Executive Approval");
		
		//Button
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
			errors.setText("");
			if ((UIManager.isInt(RegitemID, RegitemID.getText())) && !(itemName.equals(""))) {
				manage.register(new Scanner(RegitemID.getText()).nextInt(), itemName.getText(), file);
				RegitemID.clear();
				itemName.clear();
				window.close();
			} else {
				errors.setText("Something Went Wrong. Try Again");
				RegitemID.clear();
				itemName.clear();
			}
		});
        
		table = new TableView<>();
		table.setItems(UIManager.getItems(file));
		table.getColumns().addAll(referenceColumn, nameColumn, availableColumn, IDColumn);
		
		Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> {
        	UIManager.deleteButtonClicked(table, file);
        });
		
		HBox menu = new HBox();
		menu.setPadding(new Insets(10, 10, 10, 10));
		menu.setSpacing(10);
		menu.getChildren().addAll(RegitemID, itemName, executiveAP, addButton, deleteButton);
    
		// Back Button
		Button fileBack = new Button("Back");
		fileBack.setOnAction(e -> {
			table.setItems(UIManager.getItems(file));
			errors.setText("");
			window.close();
		});

		registryLayout.getChildren().addAll(label, table, menu, fileBack);
		registryLayout.setAlignment(Pos.CENTER);

		// Display window and wait for it to be closed before returning
		window.setScene(registry);
		window.showAndWait();
	}

	// Change Password
	public static void changePass(String file, String passFile) {
		Stage window = new Stage();
		VBox changeLayout = new VBox(10);
		Scene change = new Scene(changeLayout, 250, 250);
		csvFileWriter write = new csvFileWriter();

		UIManager.windowBasic(window, "Change Password", 250);

		Label label = new Label();
		label.setText("Change Password");
		errors.setText("");
		TextField oldPass = new TextField();
		oldPass.setPromptText("Current Password");
		TextField newPass = new TextField();
		newPass.setPromptText("New Password");
		TextField confirmNewPass = new TextField();
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

		changeLayout.getChildren().addAll(label, errors, oldPass, newPass, confirmNewPass, Submit, Back);
		changeLayout.setAlignment(Pos.CENTER);

		// Display window and wait for it to be closed before returning
		window.setScene(change);
		window.showAndWait();
	}

	// Clear Registry
	public static void clear(String file) {
		Stage window = new Stage();
		VBox clearLayout = new VBox(10);
		Scene clear = new Scene(clearLayout, 250, 250);
		ItemManager manage = new ItemManager();

		UIManager.windowBasic(window, "Clear Registry", 250);

		Label label = new Label();
		label.setText("Clear Registry");
		errors.setText("");

		// Clear File
		Label clearLabel = new Label(), clearStatus = new Label();
		clearLabel.setText("Clear Item Registry. This CANNOT be Undone!");
		ChoiceBox<String> YN = new ChoiceBox<>();
		YN.getItems().addAll("Yes I want to wipe the Registry", "No this was a mistake");
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

	// Executive Options
	public static void executive(String file, String passFile) {
		Stage window = new Stage();
		VBox passCheckLayout = new VBox(10), optionsLayout = new VBox(10);
		Scene passCheck = new Scene(passCheckLayout, 250, 250), options = new Scene(optionsLayout, 350, 350);
		csvFileReader read = new csvFileReader();

		UIManager.windowBasic(window, "Executive Options", 250);

		// Universal Items
		Label label = new Label();
		label.setText("Executive Options");
		errors.setText("");

		// Change Password
		Label changeLabel = new Label();
		changeLabel.setText("Change the Executive Options Password");

		// Options
		Button reg = new Button("Register Item");
		reg.setOnAction(e -> register(file));
		Button rem = new Button("Remove Item");
		rem.setOnAction(e -> remove(file));
		Button clr = new Button("Clear Item Registry");
		clr.setOnAction(e -> clear(file));
		Button chn = new Button("Change Executive Password");
		chn.setOnAction(e -> changePass(file, passFile));
		Button fle = new Button("Check File Registry");
		fle.setOnAction(e -> registry(file));

		// Password
		String pass = (read.getItem(1000, passFile)).getName();

		TextField password = new TextField();
		password.setPromptText("Password");
		Button login = new Button("Login");
		password.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.ENTER)) {
				errors.setText("");
				if (pass.equals(password.getText())) {
					System.out.println("Password Correct");
					password.clear();
					window.setScene(options);
				} else {
					System.out.println("Password Incorrect");
					password.clear();
					errors.setText("Password is Incorrect. Try Again");
				}
			}
		});
		login.setOnAction(e -> {
			errors.setText("");
			if (pass.equals(password.getText())) {
				System.out.println("Password Correct");
				password.clear();
				window.setScene(options);
			} else {
				System.out.println("Password Incorrect");
				password.clear();
				window.close();
			}
		});

		passCheckLayout.getChildren().addAll(label, errors, password, login);
		passCheckLayout.setAlignment(Pos.CENTER);

		optionsLayout.getChildren().addAll(label, reg, rem, clr, chn, fle);
		optionsLayout.setAlignment(Pos.CENTER);

		// Display window and wait for it to be closed before returning
		window.setScene(passCheck);
		window.showAndWait();
	}
}