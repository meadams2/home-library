package edu.bsu.cs.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CheckoutLogic {
    public Book checkoutBook(Book book, String borrowerName){
        if(book == null) return null;
        if(book.getCheckoutStatus()){
            return changeCheckoutStatusUponDue(book);
        }

        Checkout checkoutEntry = new Checkout.Builder()
                .setName(borrowerName)
                .setCheckoutDate(LocalDate.now())
                .setDueDate(LocalDate.now().plusDays(14))
                .build();

        List<Checkout> updatedHistory = addToCheckoutHistory(checkoutEntry,book);

        return new Book.Builder().setTitle(book.getTitle())
                .setAuthor(book.getAuthor())
                .setIsbn(book.getIsbn())
                .setLocation(book.getLocation())
                .setCheckoutStatus(true)
                .setCheckoutHistory(updatedHistory)
                .build();

    }

    public List<Checkout> addToCheckoutHistory(Checkout checkout, Book book){
        List<Checkout> updatedHistory = new ArrayList<>();
        if(book.getCheckoutHistory() != null){
            updatedHistory.addAll(book.getCheckoutHistory());
        }
        updatedHistory.add(checkout);
        return updatedHistory;
    }

    public Book changeCheckoutStatusUponDue(Book book){
        List<Checkout> checkoutHistory = book.getCheckoutHistory();
        if(checkoutHistory == null || checkoutHistory.isEmpty()){
            return book;
        }

        @SuppressWarnings("SequencedCollectionMethodCanBeUsed")
        Checkout lastCheckout = checkoutHistory.get(checkoutHistory.size()-1);

        if(lastCheckout.getDueDate().equals(LocalDate.now())){
            return new Book.Builder()
                    .setTitle(book.getTitle())
                    .setAuthor(book.getAuthor())
                    .setIsbn(book.getIsbn())
                    .setLocation(book.getLocation())
                    .setCheckoutStatus(false)
                    .setCheckoutHistory(book.getCheckoutHistory())
                    .build();
        }

        return book;
    }

    public Book returnBook(Book book){
        return new Book.Builder().setTitle(book.getTitle())
                .setAuthor(book.getAuthor())
                .setIsbn(book.getIsbn())
                .setLocation(book.getLocation())
                .setCheckoutStatus(false)
                .setCheckoutHistory(book.getCheckoutHistory()).build();
    }
}
