package edu.bsu.cs.model;

import java.util.*;

public class SearchLibrary {
    private List<Book> books = new ArrayList<>();

    public void setBooks(List<Book> books){
        this.books = books;
    }
    public List<Book> searchLibraryByTitle(String searchTerm){
        List<Book> results = new ArrayList<>();
        if(searchTerm == null || searchTerm.isEmpty()){
            return results;
        }
        String lowerSearch = searchTerm.toLowerCase();

        for(Book book : books){
            if(book.getTitle() != null && book.getTitle().toLowerCase().contains(lowerSearch)){
                results.add(book);
            }
        }
        return results;
    }

    public List<Book> searchLibraryByAuthor(String searchTerm){
        List<Book> results = new ArrayList<>();
        if(searchTerm == null || searchTerm.isEmpty()){
            return results;
        }

        String lowerSearch = searchTerm.toLowerCase();

        for(Book book : books){
            if(book.getAuthor() != null && book.getAuthor().toLowerCase().contains(lowerSearch)){
                results.add(book);
            }
        }

        return results;
    }

    public String returnResults(List<Book> searchResults){
        ResultFormatter formatter = new ResultFormatter();
        if(!searchResults.isEmpty()){
            return formatter.format10Entries(searchResults);
        } else return "No books found with this entry";
    }
}
