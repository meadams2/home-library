package edu.bsu.cs;

import edu.bsu.cs.model.Book;
import edu.bsu.cs.model.BookParser;
import edu.bsu.cs.model.ResultFormatter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestResultFormatter {
    private final BookParser parser = new BookParser();
    private final ResultFormatter formatter = new ResultFormatter();
    List<Book> books = new ArrayList<>();

    @Test
    void testFormatAllWithFiveBooks() throws IOException {
        String sampleJsonInput = parser.readJsonFileAsString("src/test/resources/gsonDemo.json");
        books = parser.parseAllBookEntries(sampleJsonInput);

        String formatted = formatter.formatAll(books);
        Assertions.assertNotNull(formatted);
    }

    @Test
    void testResultAllLinesCorrect() throws IOException {
        String sampleJsonInput = parser.readJsonFileAsString("src/test/resources/gsonDemo.json");
        books = parser.parseAllBookEntries(sampleJsonInput);

        String formatted = formatter.formatAll(books);
        Assertions.assertEquals("""
                Title
                Author
                1234567890
                Location: Location
                Checkout Status: false
                Title
                Author
                1234567890
                Location: Location
                Checkout Status: false
                Title
                Author
                1234567890
                Location: Location
                Checkout Status: false
                Title
                Author
                1234567890
                Location: Location
                Checkout Status: false
                Title
                Author
                1234567890
                Location: Location
                Checkout Status: false
                """, formatted);
    }

}
