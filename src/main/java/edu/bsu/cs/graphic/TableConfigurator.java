package edu.bsu.cs.graphic;

import edu.bsu.cs.model.Book;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Objects;
import java.util.function.Consumer;

public class TableConfigurator {
    final TableColumn<Book, String> bookDataColumn;
    final TableColumn<Book, Button> checkoutColumn;
    final TableColumn<Book, Void> deleteColumn;

    final Consumer<Book> checkoutHandler;
    final Consumer<Book> returnHandler;
    final Consumer<Book> deleteHandler;

    public TableConfigurator(Builder builder){
        this.bookDataColumn = builder.bookDataColumn;
        this.checkoutColumn = builder.checkoutColumn;
        this.deleteColumn = builder.deleteColumn;

        this.checkoutHandler = builder.checkoutHandler;
        this.returnHandler = builder.returnHandler;
        this.deleteHandler = builder.deleteHandler;
    }

    public void configureAll(){
        configureBookDataColumn();
        configureCheckoutColumn();
        configureDeleteColumn();
    }

    private void configureBookDataColumn() {
        bookDataColumn.setCellValueFactory(new PropertyValueFactory<>("formattedString"));
        bookDataColumn.setCellFactory(column -> new TableCell<>() {
            private final Text text = new Text();
            {
                text.wrappingWidthProperty().bind(column.widthProperty().subtract(10));
            }
            @Override
            protected void updateItem(String item, boolean empty){
                super.updateItem(item, empty);
                if(empty || item == null){
                    setGraphic(null);
                } else {
                    text.setText(item);
                    setGraphic(text);
                }
            }
        });
    }

    private void configureCheckoutColumn() {
        checkoutColumn.setCellFactory(column-> new TableCell<>(){
            private final Button checkoutButton = new Button("Checkout");
            private final Button returnButton   = new Button("Return");

            {
                checkoutButton.setPrefWidth(90);
                checkoutButton.setStyle("-fx-background-color: green;");
                checkoutButton.setTextFill(Color.WHITE);
                returnButton.setPrefWidth(90);

                checkoutButton.setOnAction(evt -> {
                    Book book = getBook();
                    if (book != null) checkoutHandler.accept(book);
                    getTableView().refresh();
                });

                returnButton.setOnAction(evt -> {
                    Book book = getBook();
                    if(book != null) returnHandler.accept(book);
                    getTableView().refresh();
                });
            }

            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || getBook() == null) {
                    setGraphic(null);
                    return;
                }

                if (getBook().getCheckoutStatus()) {
                    setGraphic(returnButton);
                } else {
                    setGraphic(checkoutButton);
                }
            }
            private Book getBook(){
                return getTableRow() == null ? null : getTableRow().getItem();
            }
        });

    }

    private void configureDeleteColumn(){
        deleteColumn.setCellFactory(column->new TableCell<>() {
            private final Button button = new Button("Delete");

            {
                button.setPrefWidth(90);
                button.setStyle("-fx-background-color: #C41E3A;");
                button.setTextFill(Color.WHITE);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getBook() == null) {
                    setGraphic(null);
                    return;
                } else {
                    setGraphic(button);
                }
                button.setOnAction(buttonClicked -> {
                    Book book = getBook();
                    if (book != null) deleteHandler.accept(book);
                });
            }

            private Book getBook(){
                return getTableRow() == null ? null : getTableRow().getItem();
            }
        });
    }

    public static class Builder {
        private TableColumn<Book, String> bookDataColumn;
        private TableColumn<Book, Button> checkoutColumn;
        private TableColumn<Book, Void> deleteColumn;

        private Consumer<Book> checkoutHandler;
        private Consumer<Book> returnHandler;
        private Consumer<Book> deleteHandler;

        public Builder withDataColumn(TableColumn<Book, String> column) {
            this.bookDataColumn = column;
            return this;
        }

        public Builder withCheckoutColumn(TableColumn<Book, Button> checkoutColumn) {
            this.checkoutColumn = checkoutColumn;
            return this;
        }

        public Builder withDeleteColumn(TableColumn<Book, Void> deleteColumn) {
            this.deleteColumn = deleteColumn;
            return this;
        }

        public Builder onCheckout(Consumer<Book> checkoutHandler) {
            this.checkoutHandler = checkoutHandler;
            return this;
        }

        public Builder onReturn(Consumer<Book> returnHandler) {
            this.returnHandler = returnHandler;
            return this;
        }

        public Builder onDelete(Consumer<Book> deleteHandler) {
            this.deleteHandler = deleteHandler;
            return this;
        }

        public TableConfigurator build() {
            Objects.requireNonNull(bookDataColumn, "Data column missing");
            Objects.requireNonNull(checkoutColumn, "Checkout column missing");
            Objects.requireNonNull(deleteColumn, "Delete column missing");

            Objects.requireNonNull(checkoutHandler, "Checkout handler missing");
            Objects.requireNonNull(returnHandler, "Return handler missing");
            Objects.requireNonNull(deleteHandler, "Delete handler missing");

            return new TableConfigurator(this);
        }
    }
}
