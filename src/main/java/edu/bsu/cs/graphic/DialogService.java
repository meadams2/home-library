package edu.bsu.cs.graphic;

import edu.bsu.cs.model.Book;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DialogService {
    public void displayErrorMessage(String message) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Something went wrong");
        errorAlert.setContentText(message);
        Button confirmationButton = new Button("OK");
        confirmationButton.setOnAction(buttonClick -> errorAlert.close());

        errorAlert.showAndWait();
    }

    public Book showAddDialog(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/addBookDialog.fxml"));
            Parent root = loader.load();
            AddBookDialogController controller = loader.getController();

            Stage stage = new Stage();
            stage.setTitle("Add Book");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            controller.setDialogStage(stage);

            stage.showAndWait();
            return controller.getNewBook();

        } catch (Exception e) {
            displayErrorMessage("Error loading Add dialog: " + e.getMessage());
            return null;
        }
    }

    public String showCheckoutDialog(Book book) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/checkoutDialog.fxml"));
            Parent root = loader.load();
            CheckoutDialogController controller = loader.getController();

            Stage stage = new Stage();
            stage.setTitle("Checkout " + book.getTitle());
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            controller.setDialogStage(stage);

            stage.showAndWait();
            return controller.getBorrowerName();

        } catch (Exception e) {
            displayErrorMessage("Error loading Checkout dialog: " + e.getMessage());
            return null;
        }
    }
}
