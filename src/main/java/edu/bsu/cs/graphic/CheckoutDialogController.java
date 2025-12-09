package edu.bsu.cs.graphic;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CheckoutDialogController {

    @FXML
    private TextField borrowerField;
    @FXML
    private Button cancelCheckoutButton;
    @FXML
    private Button confirmCheckoutButton;

    private String borrowerName;
    private Stage dialogStage;

    public void setDialogStage(Stage stage){
        this.dialogStage = stage;
        cancelCheckoutButton.setOnAction(buttonClick -> handleCancel());
        confirmCheckoutButton.setOnAction(buttonClick -> handleConfirm());
    }

    public String getBorrowerName(){
        return borrowerName;
    }

    @FXML
    private void handleConfirm(){
        borrowerName = borrowerField.getText();
        dialogStage.close();
    }

    @FXML
    private void handleCancel(){
        borrowerName = null;
        dialogStage.close();
    }
}
