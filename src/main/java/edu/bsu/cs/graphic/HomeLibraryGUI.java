package edu.bsu.cs.graphic;

import edu.bsu.cs.model.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class HomeLibraryGUI extends Application{
    public MenuButton searchMethodMenuButton;
    public MenuItem searchTitleChoice;
    public MenuItem searchAuthorChoice;
    public TextField searchTerm;
    public TableView<Book> bookEntryTable;
    public TableColumn<Book, String> bookDataColumn;
    public TableColumn<Book, Button> checkoutBookColumn;
    public TableColumn<Book, Void> deleteBookColumn;
    public Button addBook;

    private final BookParser parser = new BookParser();
    private final LibraryLogic logic = new LibraryLogic();
    private final SearchLibrary searchLibrary = new SearchLibrary();
    private final CheckoutLogic checkout = new CheckoutLogic();

    private DialogService dialog;

    private final ObservableList<Book> books = FXCollections.observableArrayList();
    private final FilteredList<Book> filteredBookList = new FilteredList<>(books, book -> true);
    private static final String SAVE_PATH = "src/main/resources/HomeLibrary.json";

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/homeLibrary.fxml")));
        primaryStage.setTitle("Home Library");
        primaryStage.setScene(new Scene(root, 850, 650));
        primaryStage.show();
    }

    @FXML
    public void initialize(){
        dialog = new DialogService();
        loadBooksFromJson();
        configureTable();
        configureListeners();
        configureSearchControls();
        configureAddButton();
    }

    private void configureListeners() {
        books.addListener((ListChangeListener<Book>) change ->
                logic.saveBookEntriesAsJSON(books, SAVE_PATH));
    }

    private void configureSearchControls(){
        searchTitleChoice.setOnAction(buttonClick ->
                handleSearchByTitle()
        );

        searchAuthorChoice.setOnAction( buttonClick ->
                handleSearchByAuthor());
    }

    private void loadBooksFromJson(){
        try{
            String json = parser.readJsonFileAsString(SAVE_PATH);
            List<Book> loadedBooks = parser.parseAllBookEntries(json);

            if(loadedBooks.isEmpty()){
                dialog.displayErrorMessage("No books found. If this is a mistake, please check your json file.");
            }
            books.setAll(loadedBooks);
            initializeBooks();

        } catch (IOException e){
            dialog.displayErrorMessage(e.getMessage());
        }
    }

    private void initializeBooks(){
        searchLibrary.setBooks(books);
        logic.setBooks(books);
        bookEntryTable.setItems(filteredBookList);
    }

    private void configureAddButton(){
        addBook.setOnAction(buttonClick -> handleAdd());
    }

    private void configureTable(){
        TableConfigurator tableConfigurator = new TableConfigurator.Builder()
                .withDataColumn(bookDataColumn)
                .withCheckoutColumn(checkoutBookColumn)
                .withDeleteColumn(deleteBookColumn)
                .onCheckout(this::handleCheckout)
                .onReturn(this::handleReturn).onDelete(book -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirm Book Deletion");
                    alert.setContentText("Are you sure you want to delete this book?");
                    alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.YES) {
                            books.remove(book);
                            logic.removeBook(book);
                            filteredBookList.setPredicate(filteredBookList.getPredicate());
                        }
                    });
                }).build();
        tableConfigurator.configureAll();
    }

    @FXML
    private void handleSearchByTitle(){
        String search = searchTerm.getText();
        if(search == null || search.isBlank()) {
            filteredBookList.setPredicate(book -> true);
            return;
        }

        List<Book> results = searchLibrary.searchLibraryByTitle(search);
        filteredBookList.setPredicate(results :: contains);
    }

    @FXML
    private void handleSearchByAuthor(){
        String search = searchTerm.getText();
        if(search == null || search.isBlank()) {
            filteredBookList.setPredicate(book -> true);
            return;
        }

        List<Book> results = searchLibrary.searchLibraryByAuthor(search);
        filteredBookList.setPredicate(results :: contains);
    }

    private void handleAdd(){
        Book newBook = dialog.showAddDialog();
        if (newBook != null) {
            logic.addBook(newBook);
        }
    }

    private void handleCheckout(Book book) {
        String borrower = dialog.showCheckoutDialog(book);

        if(borrower != null){
            Book updated = checkout.checkoutBook(book, borrower);
            replaceBook(book, updated);
            bookEntryTable.refresh();
        }
    }

    private void handleReturn(Book book){
        Book returnedBook = checkout.returnBook(book);
        replaceBook(book, returnedBook);
    }

    private void replaceBook(Book oldBook, Book newBook){
        int idToReplaceBook = books.indexOf(oldBook);
        if(idToReplaceBook >= 0){
            books.set(idToReplaceBook, newBook);
        }
    }
}
