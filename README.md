# Home Library
Carter Leckron, Marianne Adams, Kyla Williams

## Build Instructions

To run in the Command Line, run ``main`` in ``HomeLibrary`` class.

To run with the GUI, go to ``gradle>Tasks>application>run`` where HomeLibraryGUI is the main class. 

## Project Summary

The HomeLibrary is a simple program designed for book lovers who want to easily manage their book collections, whether it's academically inclined or purely for pleasure. This system allows users to view, add, and remove books, while providing confirmation prompts prior to addition or removal to prevent errors in their collection. 

### Key Features 

**Users can view a list of books currently in their collection.** Each book entry displays

- Title
- Author
- ISBN-13
- Location
- Checkout Status (after Iteration 2)

**Users can add new books as they acquire them.** The program prompts for: 

- Title
- Author
- ISBN-13
- Location

After submission, the user will be asked to confirm if they want to add the book. If they choose to continue, a confirmation message will display to inform the user that the book was successfully added.

**Users can remove books when they sell or no longer want them.** The system will display all the books currently in the collection and prompt them to select an index. If the index is within a valid range, the system will remove one copy at a time and preserve the remaining copies. Prior to removing the copy, the system will ask the user to confirm that they wish to remove the book from the collection. 

### User Stories

#### Iteration 1

As a bookworm, I need to be able to see which books are in my collection. 

- Each individual book should be displayed with Title, Author, ISBN-13, and Location. 

As a bookworm, I need to be able to add books to my collection because I often obtain new material for the library. 

- When adding a book to the collection, the program should ask for the Title, Author, ISBN-13, and Location. 
- Upon adding a book, the program should ask the user for confirmation. If confirmed, the program should add the book to the list and display a message that says "{Title} added successfully!". If there was an error adding the book, a System.err message should display. 

As a bookseller, I want to be able to remove books from my collection because my collection changes frequently. 

- When a book is removed from the collection, it should display all of the books currently in the library in a clean format with indexes, and ask me which index I want to remove. 
- Removing a book should keep the copies and only remove 1 at a time. 
- There should be a confirmation prompt before removal. If I confirm, the program should remove the book and display a message stating that the book was removed successfully.  

#### Iteration 2

As a bookworm, I want to be able to search books by their title and have the search return Author Name, ISBN, Number of Copies, Checkout Status, and Location

- I want an error message to display when an empty search is performed

As a casual reader, I want to be able to search a book even if I don’t know the full title

- The search will return partial matches for the search
- Top 10 closest result will display with Author Name, ISBN, Number of Copies, Checkout Status, and Location

As a multilingual reader, I want to know if a book I’m searching has copies in different languages
- The search should return all entries

As someone with a favorite author, I want to be able to find all books by that author in my library
- In the search, it should display all book titles by the Author Name, ISBN, Number of Copies, Checkout Status, and Location

As a home librarian, I want to be able to loan books out to my friends
- The book should display that it is checked out.
- The book should have a due date when the loaner needs to return the book by
- Should be able to see who checked out the book

As a bookworm, I would like to be able to save to JSON file.

- Each time I add a book to the collection, it is saved to a JSON file.
- Each time I remove a book from the collection, it is saved to a JSON file.
- I am able to delete my whole library from the command line if I want. 

#### Iteration 3

As a home librarian, I want the GUI to display errors as Alert boxes.
- The alert boxes should display the error message with the need for a confirmation.

As a bookworm, I want the GUI to display confirmation messages in the GUI. 
- The confirmation messages should show up in a separate view.
- There should be two buttons: one with the option to cancel and the other confirming.
- If I click cancel, nothing should be done.

As someone with no command line experience, I want a clean, navigable user interface.
- The user interface should have a button to add books that pulls up an appropriate prompt.
- The UI should have a table displaying all the books in my library in a catalog format. 
- In each library entry, I can click checkout and checkout a book. 
- There should also be a button for each book that allows me to delete the book from the collection.
- Initially, the catalog should display all books in my collection that I can use a scroll bar to scroll through. 

As a home librarian, I want to track which books are currently checked out. 
- If the book is checked out , the checkout button should be replaced with a "return" button.
- The book data will also display true/false of whether the book is checked out in the entry as a backup.


## Rationale

### Implementation

In ``BookParser``, ignored weak compiler warning for line 73 ``JsonObject checkoutEntry = element.getAsJsonObject()`` to prevent deeply nested methods, and duplication is not done enough to warrant separate method.

In the ``ConsoleView`` class

- Added ``displayConfirmation()`` message to meet CoS for adding and removing books by asking for user confirmation. This method serves to separate the model from the view
- Reworked ``selectNewBookData()`` to return Book object instead of array
- Made methods public to be used in implementation
- Added ``displayAllFormatted()`` to meet CoS for displaying current book list. Formatting already displays index.
- Added ``display10Formatted()`` to meet CoS for displaying search results. Formatting already displays index.

In the ``LibraryLogic`` class

- Created boolean method ``listContainsTitle()`` that iterates through the list and checks if the title matches any of the titles of the books that are currently in the list
- Created int method ``countCopies()`` to iterate through the list and check if both the title and the ISBN match the book being added
- Not changing ``LibraryLogic`` to record class as suggested in weak compiler warning since ``LibraryEditor`` is not a data holder and instead is a service class to execute essential operations, interact with I/O, and change the ``books`` list.

In the ``CheckoutLogic`` class

- Suppressed warning in ``changeCheckoutStatusUponDue(Book book)`` for ``Replace with 'getLast()'`` because otherwise the method will throw an error upon routine compile and runtime. 

In the ``HomeLibrary`` class

- Suppressed warning on line 132 to facilitate program working on older versions of Java to prevent runtime compilation error. 
### Testing 

Used ``@BeforeEach`` in ``TestLibraryEditor`` to reduce duplication of having to create the LibraryEditor, SearchLibrary, and book list for every test. 


## Things that May Be Implemented In the Future

- Add a home page with the option for a user to upload a CSV file with their own library data to ease the transition process as a way to "bulk add".
- Possibly transition from a JSON file to a SQL database. 
- Convert relevant portions to JavaScript and add as a website for use elsewhere. 
