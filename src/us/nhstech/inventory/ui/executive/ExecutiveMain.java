package us.nhstech.inventory.ui.executive;

import javafx.geometry.Pos;

import java.net.URI;
import java.net.URISyntaxException;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import us.nhstech.inventory.ui.UIManager;
import us.nhstech.inventory.ui.executive.legacy.Register;
import us.nhstech.inventory.ui.executive.legacy.Remove;
import us.nhstech.inventory.ui.executive.legacy.Status;
import us.nhstech.inventory.utils.csvFileReader;

/**
 * @author James Sonne & Devin Matte
 * @version v0.5-Alpha
 * @since 2016-02-11
 */

public class ExecutiveMain {
    /**
     * Window for all executive options. The basis for managing the system as a
     * whole.
     *
     * @param file     Links the main item File
     * @param passFile Links to the password file
     */
    public static void executive(String file, String passFile, String logFile, String IDFile) throws URISyntaxException {
        csvFileReader read = new csvFileReader();
        Label errors = new Label();
        Stage window = new Stage();
        VBox passCheckLayout = new VBox(10), optionsMenu = new VBox(10);
        BorderPane optionsLayout = new BorderPane();
        Scene passCheck = new Scene(passCheckLayout, 250, 250), options = new Scene(optionsLayout, 350, 350);
        passCheck.getStylesheets().add("style.css");
        Label label = new Label();

        UIManager.windowBasic(window, "Executive Options", 250, label, errors, options);

        // Legacy Options Menu
        Menu legacy = new Menu("_Legacy");
        MenuBar menu = new MenuBar();
        MenuItem register = new MenuItem("Register");
        MenuItem remove = new MenuItem("Remove");
        MenuItem show = new MenuItem("Show Status");
        Menu settings = new Menu("_Settings");
        MenuItem clear = new MenuItem("Clear Registry");
        MenuItem change = new MenuItem("Change Password");
        Menu help = new Menu("_Help");
        MenuItem code = new MenuItem("View Code");
        legacy.getItems().add(register);
        legacy.getItems().add(remove);
        legacy.getItems().add(show);
        settings.getItems().add(clear);
        settings.getItems().add(change);
        help.getItems().add(code);
        menu.getMenus().addAll(legacy, settings, help);

        // Options
        register.setOnAction(e -> Register.register(file));
        remove.setOnAction(e -> Remove.remove(file));
        show.setOnAction(e -> Status.show(file));
        Button fle = new Button("Item Manager");
        fle.setOnAction(e -> Registry.registry(file));
        Button mem = new Button("Member Manager");
        mem.setOnAction(e -> Members.memberManager(IDFile));
        Button log = new Button("Checkout Log");
        log.setOnAction(e -> LogView.log(logFile));
        clear.setOnAction(e -> ClearRegistry.clear(file));
        change.setOnAction(e -> ChangePassword.changePass(file, passFile));
        URI codelink = new URI("https://github.com/NHSTechTeam/Equipment-System");
        code.setOnAction(e -> UIManager.openWebpage(codelink));

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
        optionsMenu.getChildren().addAll(label, fle, mem, log);
        optionsMenu.setAlignment(Pos.CENTER);
        optionsLayout.setCenter(optionsMenu);

        // Display window and wait for it to be closed before returning
        window.setScene(passCheck);
        window.showAndWait();
    }
}
