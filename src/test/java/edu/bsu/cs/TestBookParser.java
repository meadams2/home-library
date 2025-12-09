package edu.bsu.cs;

import edu.bsu.cs.model.Book;
import edu.bsu.cs.model.BookParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class TestBookParser {
    @Test
    public void canReadJSONData () throws IOException {
        BookParser testParser = new BookParser();
        String jsonData = testParser.readJsonFileAsString("src/test/resources/gsonDemo.json");
        Assertions.assertNotNull(jsonData);
    }
    @Test
    public void testParseSingleEntryNotNull() throws IOException {
        BookParser testParser = new BookParser();
        String jsonData = testParser.readJsonFileAsString("src/test/resources/gsonDemo.json");
        Book book = testParser.parseBookEntry(jsonData);
        Assertions.assertNotNull(book);
    }

    @Test
    public void testParseSingleEntry() throws IOException{
        BookParser testParser = new BookParser();
        String jsonData = testParser.readJsonFileAsString("src/test/resources/gsonDemo.json");
        Book book = testParser.parseBookEntry(jsonData);
        String actualBookEntry = book.toString();
        Assertions.assertEquals("Book{title='Title', author='Author', isbn='1234567890', location='Location', checkoutStatus=false, checkoutHistory=[]}", actualBookEntry);
    }

    @Test
    public void testMultipleEntriesNotNull() throws IOException{
        BookParser testParser = new BookParser();
        String jsonData = testParser.readJsonFileAsString("src/test/resources/gsonDemo.json");
        List<Book> books = testParser.parseAllBookEntries(jsonData);
        Assertions.assertNotNull(books);
    }

    @Test
    public void testCorrectEntry2() throws IOException{
        BookParser testParser = new BookParser();
        String jsonData = testParser.readJsonFileAsString("src/test/resources/gsonDemo.json");
        List<Book> books = testParser.parseAllBookEntries(jsonData);
        String thirdEntry = books.get(2).toString();
        Assertions.assertEquals("Book{title='Title', author='Author', isbn='1234567890', location='Location', checkoutStatus=false, checkoutHistory=[]}", thirdEntry);
    }

    @Test
    public void testNumberEntriesIs5() throws IOException{
        BookParser testParser = new BookParser();
        String jsonData = testParser.readJsonFileAsString("src/test/resources/gsonDemo.json");
        List<Book> books = testParser.parseAllBookEntries(jsonData);
        int bookListSize = books.size();
        Assertions.assertEquals(5, bookListSize);
    }
}
