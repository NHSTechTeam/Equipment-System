package us.nhstech.inventory.logging;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import us.nhstech.inventory.ui.UIManager;
import us.nhstech.inventory.utils.ItemManager;
import us.nhstech.inventory.utils.csvFileReader;

/**
 * @author James Sonne & Devin Matte
 * @version v0.5-Alpha
 * @since 2016-02-11
 */
public class CheckOut {

    private static ItemManager manage = new ItemManager();
    private static Label errors = new Label();
    private static int checkoutItemInput, checkoutIDInput;

    /**
     * Checkout Window
     *
     * @param file   Links the main item File
     * @param IDfile Links the Memberlist File
     */
    public static void checkOut(String file, String IDfile, String passfile) {
        Stage window = new Stage();
        VBox checkoutLayout = new VBox(10);
        Scene checkout = new Scene(checkoutLayout);
        Label label = new Label();

        UIManager.windowBasic(window, "Equipment Checkout Item", 250, label, errors, checkout);

        TextField itemInput = new TextField();
        itemInput.setPromptText("Item Barcode");

        Button button = new Button("Next Step");
        button.setOnAction(e -> {
            errors.setText("");
            if (UIManager.isInt(itemInput, itemInput.getText())) {
                checkoutItemInput = Integer.parseInt(itemInput.getText());
                itemInput.clear();
                checkoutID(file, IDfile, passfile);
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
                    checkoutID(file, IDfile, passfile);
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
     * @param file   Links the main item File
     * @param IDfile Links the Memberlist File
     */
    public static void checkoutID(String file, String IDfile, String passfile) {
        Stage window = new Stage();
        VBox checkoutLayout = new VBox(10);
        Scene checkout = new Scene(checkoutLayout);
        Label label = new Label();

        UIManager.windowBasic(window, "Equipment Checkout Member ID", 250, label, errors, checkout);

        TextField IDInput = new TextField();
        IDInput.setPromptText("Member ID");

        Button button = new Button("Check Out");
        button.setOnAction(e -> {
            errors.setText("");
            if (UIManager.isInt(IDInput, IDInput.getText())) {
                checkoutIDInput = Integer.parseInt(IDInput.getText());
                if (Boolean.parseBoolean(manage.checkOut(checkoutItemInput, file, passfile, IDfile, checkoutIDInput, "")[0])) {
                    IDInput.clear();
                    window.close();
                } else {
                    checkoutConfirm(file, IDfile, passfile);
                    window.close();
                }
            } else {
                errors.setText("Something Went Wrong. Try Again");
                IDInput.clear();
            }
        });
        IDInput.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                errors.setText("");
                if (UIManager.isInt(IDInput, IDInput.getText())) {
                    checkoutIDInput = Integer.parseInt(IDInput.getText());
                    if (Boolean.parseBoolean(manage.checkOut(checkoutItemInput, file, passfile, IDfile, checkoutIDInput, "")[0])) {
                        IDInput.clear();
                        window.close();
                    } else {
                        checkoutConfirm(file, IDfile, passfile);
                        window.close();
                    }
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
     * Checkout Confirm. Confirms password on items that require it for checkout
     *
     * @param file
     * @param IDfile
     * @param passfile
     */
    public static void checkoutConfirm(String file, String IDfile, String passfile) {
        Stage window = new Stage();
        VBox checkoutLayout = new VBox(10);
        Scene checkout = new Scene(checkoutLayout);
        Label label = new Label();
        csvFileReader read = new csvFileReader();

        UIManager.windowBasic(window, "Equipment Checkout Confirmation", 250, label, errors, checkout);

        PasswordField passInput = new PasswordField();
        passInput.setPromptText("Password");
        String pass = (read.getItem(1000, passfile)).getName();

        Button button = new Button("Check Out");
        button.setOnAction(e -> {
            errors.setText("");
            if (pass.equals(passInput.getText())) {
                if (Boolean.parseBoolean(manage.checkOut(checkoutItemInput, file, passfile, IDfile, checkoutIDInput, passInput.getText())[0])) {
                    window.close();
                    passInput.clear();
                }
            } else {
                errors.setText("Something Went Wrong. Try Again");
                passInput.clear();
            }
        });
        passInput.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                errors.setText("");
                if (pass.equals(passInput.getText())) {
                    if (Boolean.parseBoolean(manage.checkOut(checkoutItemInput, file, passfile, IDfile, checkoutIDInput, passInput.getText())[0])) {
                        passInput.clear();
                        window.close();
                    }
                } else {
                    errors.setText("Something Went Wrong. Try Again");
                    passInput.clear();
                }
            }
        });

        checkoutLayout.getChildren().addAll(label, errors, passInput, button);
        checkoutLayout.setAlignment(Pos.CENTER);

        // Display window and wait for it to be closed before returning
        window.setScene(checkout);
        window.showAndWait();
    }

}
