package edu.bsu.cs;

import edu.bsu.cs.model.Book;
import edu.bsu.cs.model.Checkout;
import edu.bsu.cs.model.CheckoutLogic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestCheckoutLogic {
    private CheckoutLogic checkoutLogic;
    private Book book;

    @BeforeEach
    void setUpCheckoutTest(){
        checkoutLogic = new CheckoutLogic();
        book = new Book.Builder()
                .setTitle("1984")
                .setAuthor("George Orwell")
                .setIsbn("978-0451524935")
                .setLocation("Library")
                .setCheckoutStatus(false)
                .build();
    }

    @Test
    void testCheckoutBookMarksAsCheckedOut(){
        Book updatedBook = checkoutLogic.checkoutBook(book, "Alice");
        Assertions.assertTrue(updatedBook.getCheckoutStatus());
    }

    @Test
    void testCheckoutBookHistoryIsNotNull(){
        Book updatedBook = checkoutLogic.checkoutBook(book, "Alice");
        Assertions.assertNotNull(updatedBook.getCheckoutHistory());
    }

    @Test
    void testCheckoutBookHistoryIs1(){
        Book updatedBook = checkoutLogic.checkoutBook(book, "Alice");
        Assertions.assertEquals(1, updatedBook.getCheckoutHistory().size());
    }

    @Test
    void testCheckoutBookHistoryBorrowerCorrect(){
        Book updatedBook = checkoutLogic.checkoutBook(book, "Alice");
        Assertions.assertEquals("Alice",
                updatedBook
                        .getCheckoutHistory()
                        .getFirst()
                        .getName());
    }

    @Test
    void testChangeCheckoutStatusUponDueKeepsStatusTrueIfNotDue(){
        Checkout checkout = new Checkout.Builder()
                .setName("Test User")
                .setCheckoutDate(LocalDate.now())
                .setDueDate(LocalDate.now().plusDays(3))
                .build();

        Book bookWithHistory = new Book.Builder()
                .setTitle("1984")
                .setAuthor("George Orwell")
                .setIsbn("978-0451524935")
                .setLocation("Library")
                .setCheckoutStatus(true)
                .setCheckoutHistory(new ArrayList<>(List.of(checkout)))
                .build();

        Book updatedBook = checkoutLogic.changeCheckoutStatusUponDue(bookWithHistory);
        Assertions.assertTrue(updatedBook.getCheckoutStatus());
    }
}
