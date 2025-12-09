package edu.bsu.cs;

import edu.bsu.cs.model.Book;
import edu.bsu.cs.model.SearchLibrary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TestSearchLibrary {
    private SearchLibrary search;
    private List<Book> books;

    @BeforeEach
    void setUpTest(){
        books = new ArrayList<>();
        search = new SearchLibrary();
        search.setBooks(books);
    }
    @Test
    public void testNullSearchForTitleReturnsEmptyList(){
        Book searchBook = new Book.Builder()
                .setTitle("Great Expectations")
                .setAuthor("Charles Dickens")
                .setIsbn("978-0141439563")
                .setLocation("Office")
                .setCheckoutStatus(false).build();
        books.add(searchBook);

        List<Book> result = search.searchLibraryByTitle("");
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testSearchForExactMatchTitle(){
        Book searchBook = new Book.Builder()
                .setTitle("Great Expectations")
                .setAuthor("Charles Dickens")
                .setIsbn("978-0141439563")
                .setLocation("Office")
                .setCheckoutStatus(false).build();
        books.add(searchBook);
        List<Book> searchResult = search.searchLibraryByTitle("Great Expectations");

        String searchResultEntry = searchResult.getFirst().toString();
        Assertions.assertEquals("Book{title='Great Expectations', author='Charles Dickens', isbn='978-0141439563', location='Office', checkoutStatus=false, checkoutHistory=null}",
                searchResultEntry);
    }

    @Test
    public void testSearchForPartialMatchTitle(){
        Book searchBook = new Book.Builder()
                .setTitle("Great Expectations")
                .setAuthor("Charles Dickens")
                .setIsbn("978-0141439563")
                .setLocation("Office")
                .setCheckoutStatus(false).build();
        books.add(searchBook);
        List<Book> searchResult = search.searchLibraryByTitle("great");

        String searchResultEntry = searchResult.getFirst().toString();
        Assertions.assertEquals("Book{title='Great Expectations', author='Charles Dickens', isbn='978-0141439563', location='Office', checkoutStatus=false, checkoutHistory=null}",
                searchResultEntry);
    }

    @Test
    public void testSearchExactTitleMultipleIdenticalBooksSizeIs5(){
        for(int i =0; i < 5; i++){
            Book searchBook = new Book.Builder()
                    .setTitle("Great Expectations")
                    .setAuthor("Charles Dickens")
                    .setIsbn("978-0141439563")
                    .setLocation("Office")
                    .setCheckoutStatus(false).build();
            books.add(searchBook);
        }
        List<Book> searchResult = search.searchLibraryByTitle("Great Expectations");

        Assertions.assertEquals(5, searchResult.size());
    }

    @Test
    public void testSearchPartialTitleMultipleIdenticalBooksSizeIs5(){
        for(int i =0; i < 5; i++){
            Book searchBook = new Book.Builder()
                    .setTitle("Great Expectations")
                    .setAuthor("Charles Dickens")
                    .setIsbn("978-0141439563")
                    .setLocation("Office")
                    .setCheckoutStatus(false).build();
            books.add(searchBook);
        }
        List<Book> searchResult = search.searchLibraryByTitle("great");

        Assertions.assertEquals(5, searchResult.size());
    }

    @Test
    public void testNullSearchForAuthorReturnsEmptyList(){
        Book searchBook = new Book.Builder()
                .setTitle("Great Expectations")
                .setAuthor("Charles Dickens")
                .setIsbn("978-0141439563")
                .setLocation("Office")
                .setCheckoutStatus(false).build();
        books.add(searchBook);
        List<Book> result = search.searchLibraryByAuthor("");
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testSearchForExactAuthor(){
        Book searchBook = new Book.Builder()
                .setTitle("Great Expectations")
                .setAuthor("Charles Dickens")
                .setIsbn("978-0141439563")
                .setLocation("Office")
                .setCheckoutStatus(false).build();
        books.add(searchBook);

        List<Book> searchResult = search.searchLibraryByAuthor("Charles Dickens");

        String searchResultEntry = searchResult.getFirst().toString();
        Assertions.assertEquals("Book{title='Great Expectations', author='Charles Dickens', isbn='978-0141439563', location='Office', checkoutStatus=false, checkoutHistory=null}",
                searchResultEntry);
    }

    @Test
    public void testSearchForPartialMatchAuthor(){
        Book searchBook = new Book.Builder()
                .setTitle("Great Expectations")
                .setAuthor("Charles Dickens")
                .setIsbn("978-0141439563")
                .setLocation("Office")
                .setCheckoutStatus(false).build();
        books.add(searchBook);

        List<Book> searchResult = search.searchLibraryByAuthor("Charles");

        String searchResultEntry = searchResult.getFirst().toString();
        Assertions.assertEquals("Book{title='Great Expectations', author='Charles Dickens', isbn='978-0141439563', location='Office', checkoutStatus=false, checkoutHistory=null}",
                searchResultEntry);
    }

    @Test
    public void testSearchExactAuthorMultipleIdenticalBooksSizeIs5(){
        for(int i =0; i < 5; i++){
            Book searchBook = new Book.Builder()
                    .setTitle("Great Expectations")
                    .setAuthor("Charles Dickens")
                    .setIsbn("978-0141439563")
                    .setLocation("Office")
                    .setCheckoutStatus(false).build();
            books.add(searchBook);
        }

        List<Book> searchResult = search.searchLibraryByAuthor("Charles Dickens");

        Assertions.assertEquals(5, searchResult.size());
    }

    @Test
    public void testSearchPartialAuthorMultipleIdenticalBooksSizeIs5(){
        for(int i =0; i < 5; i++){
            Book searchBook = new Book.Builder()
                    .setTitle("Great Expectations")
                    .setAuthor("Charles Dickens")
                    .setIsbn("978-0141439563")
                    .setLocation("Office")
                    .setCheckoutStatus(false).build();
            books.add(searchBook);
        }

        List<Book> searchResult = search.searchLibraryByAuthor("Charles");

        Assertions.assertEquals(5, searchResult.size());
    }
}
