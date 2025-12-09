package edu.bsu.cs.model;

import java.util.List;

public class Book{

    public static final class Builder{
        private String title;
        private String author;
        private String isbn;
        private String location;
        private boolean checkoutStatus;
        private List<Checkout> checkoutHistory;
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }
        public Builder setAuthor(String author) {
            this.author = author;
            return this;
        }
        public Builder setIsbn(String isbn) {
            this.isbn = isbn;
            return this;
        }
        public Builder setLocation(String location) {
            this.location = location;
            return this;
        }
        public Builder setCheckoutStatus(boolean checkoutStatus){
            this.checkoutStatus = checkoutStatus;
            return this;
        }

        public Builder setCheckoutHistory(List<Checkout> checkoutHistory) {
            this.checkoutHistory = checkoutHistory;
            return this;
        }

        public Book build(){
            return new Book(this);
        }
    }

    private final String title;
    private final String author;
    private final String isbn;
    private final String location;
    private final boolean checkoutStatus;
    private final List<Checkout> checkoutHistory;

    public Book(Builder builder) {
        this.title = builder.title;
        this.author = builder.author;
        this.isbn = builder.isbn;
        this.location = builder.location;
        this.checkoutStatus = builder.checkoutStatus;
        this.checkoutHistory = builder.checkoutHistory;
    }

    public String getTitle() {return title;}
    public String getAuthor() {return author;}
    public String getIsbn() {return isbn;}
    public String getLocation() {return location;}
    public boolean getCheckoutStatus(){return checkoutStatus;}

    public List<Checkout> getCheckoutHistory() {return checkoutHistory;}

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", location='" + location + '\'' +
                ", checkoutStatus=" + checkoutStatus +
                ", checkoutHistory=" + checkoutHistory +
                '}';
    }

    public String getFormattedString(){
        return String.format("""
                           %s
                           %s
                           %s
                           Location: %s
                           Checkout Status: %s
                           """,
                title, author, isbn, location, checkoutStatus
        );
    }
}

