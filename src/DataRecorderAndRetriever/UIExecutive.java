package DataRecorderAndRetriever;

import java.util.Scanner;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * 
 * @author James & Devin
 *
 */

public class UIExecutive {
	static ItemManager manage = new ItemManager();
	static Label errors = new Label();

	/**
	 * Register an Item to the Registry
	 * 
	 * @param file
	 *            Links the main item File
	 */
	public static void register(String file) {
		Stage window = new Stage();
		VBox registerLayout = new VBox(10);
		Scene register = new Scene(registerLayout, 250, 250);
		register.getStylesheets().add("style.css");
		Label label = new Label();

		UIManager.windowBasic(window, "Register Item", 250, label, errors);

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

	/**
	 * Remove an Item from the Registry
	 * 
	 * @param file
	 *            Links the main item File
	 */
	public static void remove(String file) {
		Stage window = new Stage();
		VBox removeLayout = new VBox(10);
		Scene remove = new Scene(removeLayout, 250, 250);
		remove.getStylesheets().add("style.css");
		Label label = new Label();

		UIManager.windowBasic(window, "Remove Item", 250, label, errors);

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

	/**
	 * Manage the Item Registry
	 * 
	 * @param file
	 *            Links the main item File
	 */
	public static void registry(String file) {
		Stage window = new Stage();
		VBox registryLayout = new VBox(10);
		Scene registry = new Scene(registryLayout, 250, 250);
		registry.getStylesheets().add("style.css");
		TableView<Item> table;
		Label label = new Label();
		TextField search = new TextField();
		search.setPromptText("Search");

		UIManager.windowBasic(window, "Item Manager", 750, label, errors);

		manage.searchFor(file, search.getText());
		
		// reference column
		TableColumn<Item, String> referenceColumn = new TableColumn<>("Reference");
		referenceColumn.setMinWidth(80);
		referenceColumn.setCellValueFactory(new PropertyValueFactory<>("reference"));

		// name column
		TableColumn<Item, Double> nameColumn = new TableColumn<>("Item Name");
		nameColumn.setMinWidth(250);
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

		// available column
		TableColumn<Item, String> availableColumn = new TableColumn<>("Available");
		availableColumn.setMinWidth(80);
		availableColumn.setCellValueFactory(new PropertyValueFactory<>("available"));

		// ID column
		TableColumn<Item, String> IDColumn = new TableColumn<>("ID");
		IDColumn.setMinWidth(150);
		IDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));

		// Permission column
		TableColumn<Item, String> permColumn = new TableColumn<>("Permission");
		permColumn.setMinWidth(150);
		permColumn.setCellValueFactory(new PropertyValueFactory<>("permission"));

		// Name input
		TextField itemName = new TextField();
		itemName.setPromptText("Item Name");
		itemName.setMinWidth(200);
		itemName.setMaxWidth(275);

		// ID input
		TextField RegitemID = new TextField();
		RegitemID.setPromptText("ID Number");
		RegitemID.setMinWidth(75);
		RegitemID.setMaxWidth(125);

		ComboBox<String> executiveAP;
		executiveAP = new ComboBox<>();
		executiveAP.getItems().addAll("True", "False");
		executiveAP.setPromptText("Executive Approval");

		table = new TableView<>();
		table.setItems(UIManager.getItems(file, search.getText()));
		table.getColumns().addAll(referenceColumn, nameColumn, availableColumn, IDColumn, permColumn);

		Button deleteButton = new Button("Delete");
		deleteButton.setOnAction(e -> {
			UIManager.deleteButtonClicked(table, file);
		});

		search.setOnKeyPressed(e -> {
			table.setItems(UIManager.getItems(file, search.getText()));
		});

		// Button
		Button addButton = new Button("Add");
		addButton.setOnAction(e -> {
			errors.setText("");
			if ((UIManager.isInt(RegitemID, RegitemID.getText())) && !(itemName.equals(""))) {
				manage.register(new Scanner(RegitemID.getText()).nextInt(), itemName.getText(),
						Boolean.parseBoolean(executiveAP.getValue()), file);
				RegitemID.clear();
				itemName.clear();
				table.setItems(UIManager.getItems(file,search.getText()));

			} else {
				errors.setText("Something Went Wrong. Try Again");
				RegitemID.clear();
				itemName.clear();
			}
		});

		HBox menu = new HBox();
		menu.setPadding(new Insets(10, 10, 10, 10));
		menu.setSpacing(10);
		menu.setAlignment(Pos.CENTER);
		menu.getChildren().addAll(RegitemID, itemName, executiveAP, addButton, deleteButton);

		// Back Button
		Button fileBack = new Button("Back");
		fileBack.setOnAction(e -> {
			table.setItems(UIManager.getItems(file, search.getText()));
			errors.setText("");
			window.close();
		});

		registryLayout.getChildren().addAll(label, search, table, menu, fileBack);
		registryLayout.setAlignment(Pos.CENTER);

		// Display window and wait for it to be closed before returning
		window.setScene(registry);
		window.showAndWait();
	}

	/**
	 * Show Item Status Window
	 * 
	 * @param file
	 *            Links the main item File
	 */
	public static void show(String file) {
		Stage window = new Stage();
		VBox showLayout = new VBox(10), show2Layout = new VBox(10);
		Scene show = new Scene(showLayout, 250, 250), show2 = new Scene(show2Layout, 500, 150);
		show.getStylesheets().add("style.css");
		show2.getStylesheets().add("style.css");
		Label label = new Label();

		UIManager.windowBasic(window, "Equipment Status", 250, label, errors);

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

	/**
	 * Change the Executive Password
	 * 
	 * @param file
	 *            Links the main item File
	 * @param passFile
	 *            Links to the password file
	 */
	public static void changePass(String file, String passFile) {
		Stage window = new Stage();
		VBox changeLayout = new VBox(10);
		Scene change = new Scene(changeLayout, 250, 250);
		change.getStylesheets().add("style.css");
		csvFileWriter write = new csvFileWriter();
		Label label = new Label();

		UIManager.windowBasic(window, "Change Password", 250, label, errors);

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

	/**
	 * Clear the Item Registry
	 * 
	 * @param file
	 *            Links the main item File
	 */
	public static void clear(String file) {
		Stage window = new Stage();
		VBox clearLayout = new VBox(10);
		Scene clear = new Scene(clearLayout, 250, 250);
		clear.getStylesheets().add("style.css");
		Label label = new Label();

		UIManager.windowBasic(window, "Clear Registry", 250, label, errors);

		// Clear File
		Label clearLabel = new Label(), clearStatus = new Label();
		clearLabel.setText("Clear Item Registry. This CANNOT be Undone!");
		ComboBox<String> YN;
		YN = new ComboBox<>();
		YN.getItems().addAll("Yes I want to wipe the Registry", "No this was a mistake");
		YN.setPromptText("Wipe the Registry?");
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

	/**
	 * Window for all executive options. The basis for managing the system as a
	 * whole.
	 * 
	 * @param file
	 *            Links the main item File
	 * @param passFile
	 *            Links to the password file
	 */
	public static void executive(String file, String passFile) {
		Stage window = new Stage();
		VBox passCheckLayout = new VBox(10), optionsMenu = new VBox(10);
		BorderPane optionsLayout = new BorderPane();
		Scene passCheck = new Scene(passCheckLayout, 250, 250), options = new Scene(optionsLayout, 350, 350);
		options.getStylesheets().add("style.css");
		passCheck.getStylesheets().add("style.css");
		csvFileReader read = new csvFileReader();
		Label label = new Label();

		UIManager.windowBasic(window, "Executive Options", 250, label, errors);

		// Other Options Menu
		Menu legacy = new Menu("_Legacy");
		MenuBar menu = new MenuBar();
		MenuItem register = new MenuItem("Register");
		MenuItem remove = new MenuItem("Remove");
		MenuItem show = new MenuItem("Show Status");
		legacy.getItems().add(register);
		legacy.getItems().add(remove);
		legacy.getItems().add(show);
		menu.getMenus().addAll(legacy);

		// Options
		register.setOnAction(e -> register(file));
		remove.setOnAction(e -> remove(file));
		show.setOnAction(e -> show(file));
		Button fle = new Button("Item Manager");
		fle.setOnAction(e -> registry(file));
		Button clr = new Button("Clear Item Registry");
		clr.setOnAction(e -> clear(file));
		Button chn = new Button("Change Executive Password");
		chn.setOnAction(e -> changePass(file, passFile));

		// Password
		String pass = (read.getItem(1000, passFile)).getName();

		PasswordField password = new PasswordField();
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

		optionsLayout.setTop(menu);
		optionsMenu.getChildren().addAll(label, fle, clr, chn);
		optionsMenu.setAlignment(Pos.CENTER);
		optionsLayout.setCenter(optionsMenu);

		// Display window and wait for it to be closed before returning
		window.setScene(passCheck);
		window.showAndWait();
	}
}
