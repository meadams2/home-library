package edu.bsu.cs;

import edu.bsu.cs.model.Book;
import edu.bsu.cs.model.LibraryLogic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TestLibraryLogic {
    private LibraryLogic logic;
    private List<Book> books;

    @BeforeEach
    void setUpTest(){
        logic = new LibraryLogic();
        books = new ArrayList<>();
        logic.setBooks(books);
    }
    @Test
    void testListReturnsTrueWhenMatchingTitleExists(){
        Book matchingBook = new Book.Builder()
                .setTitle("Great Expectations")
                .setAuthor("Charles Dickens")
                .setIsbn("978-0141439563")
                .setLocation("Office")
                .setCheckoutStatus(false)
                .build();
        books.add(matchingBook);

        Book newBook = new Book.Builder()
                .setTitle("Great Expectations")
                .setAuthor("Charles Dickens")
                .setIsbn("978-0141439563")
                .setLocation("Office")
                .setCheckoutStatus(false)
                .build();

        Assertions.assertTrue(logic.listContainsTitle(newBook));
    }

    @Test
    void testListReturnsFalseWhenMatchingTitleDNE(){
        Book notMatchingTitle = new Book.Builder()
                .setTitle("Harry Potter and the Sorcerer's Stone")
                .setAuthor("JK Rowling")
                .setIsbn("978-0747550990")
                .setLocation("Cupboard Under the Stairs")
                .setCheckoutStatus(false)
                .build();
        books.add(notMatchingTitle);

        Book copyBook = new Book.Builder()
                .setTitle("Real Analysis")
                .setAuthor("Charles Pugh")
                .setIsbn("978-331917770")
                .setLocation("Office")
                .setCheckoutStatus(false)
                .build();

        Assertions.assertFalse(logic.listContainsTitle(copyBook));
    }

    @Test
    void testAddBookAddsNewUnique(){
        Book bookAddition = new Book.Builder()
                .setTitle("Great Expectations")
                .setAuthor("Charles Dickens")
                .setIsbn("978-0141439563")
                .setLocation("Office")
                .setCheckoutStatus(false)
                .build();
        logic.addBook(bookAddition);

        Assertions.assertEquals("Great Expectations", books.getFirst().getTitle());
    }

    @Test
    void testAddDuplicateBookAppendsCopyCount(){
        Book existingBook = new Book.Builder()
                .setTitle("Great Expectations")
                .setAuthor("Charles Dickens")
                .setIsbn("978-0141439563")
                .setLocation("Office")
                .setCheckoutStatus(false)
                .build();
        books.add(existingBook);

        Book duplicate = new Book.Builder()
                .setTitle("Great Expectations")
                .setAuthor("Charles Dickens")
                .setIsbn("978-0141439563")
                .setLocation("Office")
                .setCheckoutStatus(false)
                .build();
        logic.addBook(duplicate);

        Assertions.assertEquals("Great Expectations (2)", books.get(1).getTitle());
    }

    @Test
    void testReturnsBookWithMatchingTitle(){
        Book existingBook = new Book.Builder()
                .setTitle("Great Expectations")
                .setAuthor("Charles Dickens")
                .setIsbn("978-0141439563")
                .setLocation("Office")
                .setCheckoutStatus(false)
                .build();
        books.add(existingBook);
        Book matchingBook = logic.getBookWithMatchingTitle("Great Expectations");

        Assertions.assertEquals("Book{title='Great Expectations', author='Charles Dickens', isbn='978-0141439563', location='Office', checkoutStatus=false, checkoutHistory=null}",
                matchingBook.toString());
    }

    @Test
    void testNotReturnBookWithMismatchTitle(){
        Book exisingBook = new Book.Builder()
                .setTitle("Great Expectations")
                .setAuthor("Charles Dickens")
                .setIsbn("978-0141439563")
                .setLocation("Office")
                .setCheckoutStatus(false)
                .build();
        books.add(exisingBook);
        Book matchingBook = logic.getBookWithMatchingTitle("Moby Dick");
        Assertions.assertNull(matchingBook);
    }
    @Test
    void testRemoveBookReturnsTrue(){
        Book bookRemoval = new Book.Builder()
                .setTitle("Great Expectations")
                .setAuthor("Charles Dickens")
                .setIsbn("978-0141439563")
                .setLocation("Office")
                .setCheckoutStatus(false)
                .build();
        books.add(bookRemoval);

        boolean removed = logic.removeBook(bookRemoval);
        Assertions.assertTrue(removed);
    }

    @Test
    void testBookListIsEmptyWhenRemoved(){
        Book bookRemoval = new Book.Builder()
                .setTitle("Great Expectations")
                .setAuthor("Charles Dickens")
                .setIsbn("978-0141439563")
                .setLocation("Office")
                .setCheckoutStatus(false)
                .build();
        books.add(bookRemoval);

        logic.removeBook(bookRemoval);
        Assertions.assertTrue(books.isEmpty());
    }

    @Test
    void testRemoveBookReturnsErrorBookNotFound(){
        Book existing = new Book.Builder()
                .setTitle("Moby Dick")
                .setAuthor("Herman Melville")
                .setIsbn("12345")
                .setLocation("Living Room")
                .setCheckoutStatus(false)
                .build();
        books.add(existing);

        Book removedBook = new Book.Builder()
                .setTitle("War and Peace")
                .setAuthor("Leo Tolstoy")
                .setIsbn("9999")
                .setLocation("Office")
                .setCheckoutStatus(false)
                .build();
        books.add(removedBook);

        books.remove(1);
        Assertions.assertFalse(logic.removeBook(removedBook));
    }
}
