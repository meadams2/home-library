package edu.bsu.cs.console;
import edu.bsu.cs.model.Book;

import java.util.Scanner;

public class ConsoleView {
    public String initialMenu() {
        System.out.print("""
                Please select an option:\s
                 \
                1). Add a book\s
                 \
                2). Search\s
                 \
                3). Delete entire library\s
                 \
                4) Exit program\s
                """);
        return getUserInput();
    }

    public String getUserInput(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public Book selectNewBookData() {
        Book.Builder builder = new Book.Builder();

        System.out.println("Please enter the book title: ");
        builder.setTitle(getUserInput());
        System.out.println("Please enter the author's name: ");
        builder.setAuthor(getUserInput());
        System.out.println("Please enter the ISBN number for this book, if applicable: ");
        builder.setIsbn(getUserInput());
        System.out.println("Please enter where this book is located: ");
        builder.setLocation(getUserInput());

        return builder.build();
    }

    public void displayMessage(String consoleOutput) {
        System.out.println(consoleOutput);
    }

    public String displayConfirmation(){
        displayMessage("Please select 1 or 2: \n 1) Confirm \n 2) Cancel");
        return getUserInput();
    }

    public void displayErrorMessage(String errorMessage){
        System.err.println(errorMessage);
    }

    public String searchMenu(){
        displayMessage("How do you want to search? \n 1) By title \n 2) By author");
        return getUserInput();
    }

    public String searchByTitleUserInput(){
        displayMessage("Enter title: ");
        return getUserInput();
    }

    public String searchByAuthorUserInput(){
        displayMessage("Enter Author: ");
        return getUserInput();
    }

    public String bookMenu(Book book){
        displayMessage("What do you want to do for " + book.getTitle() + "?\n" +
                "1). Checkout book\n" +
                "2). Remove book\n" +
                "3) Keep");
        return getUserInput();
    }

    public String getNameForCheckout(){
        displayMessage("Enter name: ");
        return getUserInput();
    }

}
