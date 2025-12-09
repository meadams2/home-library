package edu.bsu.cs.model;

import java.util.List;
public class ResultFormatter {
    public String formatAll(List<Book> books){
        StringBuilder builder = new StringBuilder();
        for (Book book : books) {
            builder.append(book.getFormattedString());
        }
        return builder.toString();
    }

    public String format10Entries(List<Book> books){
        StringBuilder builder = new StringBuilder();
        int maxEntries = Math.min(10, books.size());
        for(int currentBook = 0; currentBook < maxEntries; currentBook++){
            Book book = books.get(currentBook);
            builder.append(book.getFormattedString());
        }
        return builder.toString();
    }
}
