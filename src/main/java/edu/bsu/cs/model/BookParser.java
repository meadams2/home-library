package edu.bsu.cs.model;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookParser {
    public String readJsonFileAsString(String fileName) throws IOException{
        Path path = Path.of(fileName);

        if(!Files.exists(path) || Files.size(path) == 0){
            return "[]";
        }
        return Files.readString(path, StandardCharsets.UTF_8);
    }

    public Book parseBookEntry(String libraryData) {
        JsonArray rawBookData = JsonParser.parseString(libraryData).getAsJsonArray();
        if (rawBookData.isEmpty()){
            return null;
        }

        JsonObject rawBookObjects = rawBookData.get(0).getAsJsonObject();
        return parseBookFromJsonObject(rawBookObjects);
    }

    public List<Book> parseAllBookEntries(String libraryData) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class,
                        (JsonDeserializer<LocalDate>)
                                (json, type, context) -> LocalDate.parse(json.getAsString()))
                .create();

        Type listType = new TypeToken<List<JsonObject>>() {}.getType();
        List<JsonObject> rawBookObjects = gson.fromJson(libraryData, listType);

        List<Book> books = new ArrayList<>();
        if(rawBookObjects == null){
            return books;
        }

        for(JsonObject bookObject : rawBookObjects){
            books.add(parseBookFromJsonObject(bookObject));
        }

        return books;
    }

    private Book parseBookFromJsonObject(JsonObject rawBookData){
        Book.Builder bookBuilder = new Book.Builder();

        if (rawBookData.has("title")) bookBuilder.setTitle(rawBookData.get("title").getAsString());
        if(rawBookData.has("author")) bookBuilder.setAuthor(rawBookData.get("author").getAsString());
        if(rawBookData.has("isbn")) bookBuilder.setIsbn(rawBookData.get("isbn").getAsString());
        if(rawBookData.has("location")) bookBuilder.setLocation(rawBookData.get("location").getAsString());

        boolean checkoutStatus = rawBookData.has("checkoutStatus") && rawBookData.get("checkoutStatus").getAsBoolean();
        bookBuilder.setCheckoutStatus(checkoutStatus);

        List<Checkout> checkoutHistory = new ArrayList<>();
        if(rawBookData.has("checkoutHistory")){
            JsonArray historyArray = rawBookData.getAsJsonArray("checkoutHistory");

            for(JsonElement element : historyArray){
                JsonObject checkoutEntry = element.getAsJsonObject();
                Checkout.Builder checkoutBuilder = new Checkout.Builder();
                if(checkoutEntry.has("name"))
                    checkoutBuilder.setName(checkoutEntry.get("name").getAsString());
                if(checkoutEntry.has("checkoutDate"))
                    checkoutBuilder.setCheckoutDate(LocalDate.parse(checkoutEntry.get("checkoutDate").getAsString()));
                if(checkoutEntry.has("dueDate"))
                    checkoutBuilder.setDueDate(LocalDate.parse(checkoutEntry.get("dueDate").getAsString()));

                checkoutHistory.add(checkoutBuilder.build());
            }
        }
        bookBuilder.setCheckoutHistory(checkoutHistory);
        return bookBuilder.build();
    }
}
