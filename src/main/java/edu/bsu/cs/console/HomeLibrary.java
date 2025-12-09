package edu.bsu.cs.console;


import edu.bsu.cs.model.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeLibrary {
    private static final ConsoleView console = new ConsoleView();
    private final LibraryLogic logic = new LibraryLogic();
    private final SearchLibrary search = new SearchLibrary();
    private final BookParser parser = new BookParser();
    private final CheckoutLogic checkoutLogic = new CheckoutLogic();

    private final String fileName = new File("src/main/resources/HomeLibrary.json").getAbsolutePath();
    private final String shortFileName = "HomeLibrary.json";

    private List<Book> homeLibrary;

    public static void main(String[] args) {
        HomeLibrary library = new HomeLibrary();
        library.start();
    }

    public HomeLibrary(){
        initializeLibrary();
    }

    private void initializeLibrary(){
        try{
            new File(fileName);

            String homeLibraryFile = parser.readJsonFileAsString(fileName);
            homeLibrary = parser.parseAllBookEntries(homeLibraryFile);

            if(homeLibrary == null){
                homeLibrary = new ArrayList<>();
            }

            search.setBooks(homeLibrary);
            logic.setBooks(homeLibrary);

        } catch(IOException e){
            console.displayErrorMessage("Error initializing library: "+ e.getMessage());
            homeLibrary = new ArrayList<>();
        }
    }

    public void start () {
        boolean keepGoing = true;

        while (keepGoing) {
            String menuChoice = console.initialMenu();
            switch (menuChoice) {
                case "1" -> addBookToLibrary();
                case "2" -> handleSearch();
                case "3" -> deleteEntireLibrary();
                case "4" -> {
                    saveLibraryAndExit();
                    keepGoing = false;
                }
                default -> console.displayErrorMessage("Not a valid input.");
            }
        }
    }

    private void addBookToLibrary(){
        Book newBook = console.selectNewBookData();
        String confirmation = console.displayConfirmation();
        ResultFormatter bookFormatter = new ResultFormatter();

        if("1".equals(confirmation)){
            logic.addBook(newBook);
            console.displayMessage(newBook.getTitle() + " added successfully.");
            console.displayMessage(bookFormatter.formatAll(homeLibrary));
        } else {
            console.displayMessage("Add book cancelled.");
        }
    }

    private void handleSearch(){
        String searchMethod = console.searchMenu();
        List<Book> searchResults = new ArrayList<>();

        switch (searchMethod){
            case "1" -> searchResults = search.searchLibraryByTitle(
                    console.searchByTitleUserInput());
            case "2" -> searchResults = search.searchLibraryByAuthor(
                    console.searchByAuthorUserInput());
            default -> console.displayMessage("Invalid search method.");
        }

        console.displayMessage(search.returnResults(searchResults));
        if(!searchResults.isEmpty()){
            handleBookMenu(searchResults);
        }
    }

    private void handleBookMenu(List<Book> books){
        for(Book book : books){
            String action = console.bookMenu(book);

            switch (action){
                case "1"-> checkout(book);
                case "2" -> removeBookFromLibrary(book);
                case "3" -> console.displayMessage("Exiting to main.");
                default -> console.displayMessage("Invalid choice.");
            }
        }
    }

    private void checkout(Book book){
        String borrower = console.getNameForCheckout();
        Book updatedBook = checkoutLogic.checkoutBook(book, borrower);

        int index = homeLibrary.indexOf(book);
        if(index != -1){
            homeLibrary.set(index, updatedBook);
        }

        logic.setBooks(homeLibrary);
        search.setBooks(homeLibrary);

        List<Checkout> history = updatedBook.getCheckoutHistory();
        //noinspection SequencedCollectionMethodCanBeUsed
        Checkout last = history.get(history.size()-1);
        console.displayMessage(updatedBook.getTitle() + " checked out successfully by "
                + last.getName() + ", due on " + last.getDueDate());
    }

    private void removeBookFromLibrary(Book book){
        logic.removeBook(book);
        homeLibrary.remove(book);
        console.displayMessage(book.getTitle() + " removed from library");
    }

    private void saveLibraryAndExit(){
        console.displayMessage("Saving books to "+ shortFileName);
        logic.saveBookEntriesAsJSON(homeLibrary, fileName);
        console.displayMessage("Exiting Program.");
    }

    private void deleteEntireLibrary(){
        console.displayMessage("WARNING: This is a destructive action and will delete ALL books from the library.");
        console.displayMessage("Are you sure you want to continue?");
        String confirmation = console.displayConfirmation();

        if(!"1".equals(confirmation)){
            console.displayMessage("Delete cancelled.");
            return;
        }

        console.displayMessage("Enter the name of the file " + shortFileName);
        String confirmFileName = console.getUserInput().trim();

        if(confirmFileName.isEmpty()){
            console.displayMessage("No file name entered. Delete cancelled.");
            return;
        }

        if(!shortFileName.equalsIgnoreCase(confirmFileName)){
            console.displayMessage("File name did not match. Delete cancelled.");
            return;
        }

        homeLibrary.clear();
        logic.setBooks(homeLibrary);
        search.setBooks(homeLibrary);

        logic.saveBookEntriesAsJSON(homeLibrary, fileName);
        console.displayMessage("Library successfully deleted from "+ shortFileName);
    }
}