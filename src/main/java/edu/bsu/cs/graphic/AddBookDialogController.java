package edu.bsu.cs.graphic;

import edu.bsu.cs.model.Book;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddBookDialogController {
    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField isbnField;
    @FXML
    private TextField locationField;
    @FXML
    private Button addToLibraryButton;



    private Stage dialogStage;
    private Book newBook;

    public void setDialogStage(Stage stage){
        this.dialogStage = stage;
        addToLibraryButton.setOnAction(buttonClick -> handleAddBook());
    }

    @FXML
    private Book handleAddBook(){
        Book.Builder builder = new Book.Builder();
        newBook = builder
                .setTitle(titleField.getText())
                .setAuthor(authorField.getText())
                .setIsbn(isbnField.getText())
                .setLocation(locationField.getText())
                .build();
        dialogStage.close();
        return newBook;
    }

    public Book getNewBook(){
        return newBook;
    }
}
