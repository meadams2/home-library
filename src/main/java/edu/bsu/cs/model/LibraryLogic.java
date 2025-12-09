package edu.bsu.cs.model;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LibraryLogic {
    List<Book> books = new ArrayList<>();

    public void setBooks(List<Book> books){
        this.books = books;
    }
    public boolean listContainsTitle(Book compareBook){
        return books.stream().anyMatch(book ->
                book.getTitle().equalsIgnoreCase(compareBook.getTitle())
        );
    }

    private int countCopiesOfBook(Book newBook){
        int count = 0;
        for (Book book : books) {
            boolean sameTitle = book.getTitle().equalsIgnoreCase(newBook.getTitle());
            boolean sameIsbn = (book.getIsbn() == null || newBook.getIsbn() == null ||
                    book.getIsbn().equalsIgnoreCase(newBook.getIsbn()));
            if (sameTitle && sameIsbn) {
                count++;
            }
        }
        return count;
    }

    public void addBook(Book book){
        Book.Builder builder = new Book.Builder();
        String title;

        if(listContainsTitle(book)){
            int copies = countCopiesOfBook(book);
            title = book.getTitle() + " ("+ (copies + 1)+ ")";
        }
        else{
            title = book.getTitle();
        }

        Book newBook = builder
                .setTitle(title)
                .setAuthor(book.getAuthor())
                .setIsbn(book.getIsbn())
                .setLocation(book.getLocation())
                .setCheckoutStatus(false)
                .setCheckoutHistory(null)
                .build();

        books.add(newBook);
    }

    public boolean removeBook(Book book){
        if(!books.contains(book)) return false;
        else {
            books.remove(book);
            return true;
        }
    }

    public Book getBookWithMatchingTitle(String title){
        if(books == null || title == null){
            return null;
        }

        for(Book book : books){
            if(title.equals(book.getTitle())){
                return book;
            }
        }

        return null;
    }

    public void saveBookEntriesAsJSON(List<Book> books, String fileName){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class,
                        (com.google.gson.JsonSerializer<LocalDate>) (src, typeOfSrc, context) ->
                                new com.google.gson.JsonPrimitive(src.toString()))
                .registerTypeAdapter(LocalDate.class,
                        (com.google.gson.JsonDeserializer<LocalDate>)
                                (json, typeOfT, context) -> LocalDate.parse(json.getAsString()))
                .setPrettyPrinting()
                .create();
        try(Writer writer = new FileWriter(fileName)){
            gson.toJson(books, writer);
        } catch (IOException e){
            e.getMessage();
        }
    }
}
