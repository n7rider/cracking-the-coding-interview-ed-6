package n7rider.ch7.object_oriented_design;

import java.util.List;
import java.util.Map;

/**
 * Online Book Reader: Design the data structures for an online book reader system.
 *
 * After completing:
 * Adding a map of bookDictionary feels odd since it is inefficient when implementing. However,
 * I think OOPS paradigm is that way - we try to define things in term of objects within objects, but
 * we may not necessarily deal with them that way in APIs or systems.
 *
 * After finishing:
 * Instead of directly having arrays of books or bookDictionary in the main class, the solution uses
 * Library, UserManager, Display inside OnlineReaderSystem. This makes sense system-wise and also for
 * efficient implementation
 */
public class Question7_5 {
    abstract class BookReader {
        // singleton
        Book[] books;
        Map<Metadata, List<String>> bookDictionary;

        abstract Map<Metadata, List<String>> configureDB(Book[] books);
        User[] users;
    }

    abstract class SearchSystem {
        abstract Book[] sortBy(Map<Metadata, List<String>> bookDictionary);
    }

    class Book {
        byte[] content;
        Metadata metadata;
    }

    class Metadata {
        String author;
        String isbn;
        String genre;
        //...
    }

    abstract class Viewer {
        abstract byte[] viewBook(String isbn);

        abstract void changeFontSize(int size);
    }

    abstract class User {
        abstract boolean login();
        String[] favorites;
        Map<String, Integer> progress;

        // load and save for progress and favorites
    }



}

/**
 * Components:
 *
 * BookReader // Singleton
 * Books
 * BookContent
 * BookMetadata
 *  Author
 *  Genre
 *  Year
 *  Title
 *  ISBN
 * Reader
 * BookDictionary - Map<BookMetadata, List<Book> // Or replace with a sql repository
 * User[]
 *   ISBN[] favorite
 *   Map<Book, ISBN> progress
 *
 *
 *
 * Fields and methods:
 * BookReader
 *
 *  configureDBorMap
 *    for each Book
 *      for each Metadata
 *          upsert isbn to metadata map // Or add as new ones are being added
 *
 *  Book[]
 *      BookContent
 *          byte[] content
 *      BookMetadata
 *          Author
 *          Genre
 *          Year
 *          Title
 *          ISBN // key
 *
 * Viewer
 *  viewBook(isbn)
 *  changeFontSize(int size)
 *  closeBook(book)
 *
 * BookDictionary
 *  sortBy(Metadata type)
 *
 * Users
 *   addToFavorite(ISBN)
 *   showFavorites()
 *   saveProgress(ISBN)
 *   int loadProgress(ISBN)
 *   boolean login()
 *
 *
 * Flow:
 * BookReader.configureDBorMap
 *
 * User
 * login()
 * call showFavorites, loadProgress to show in screen
 *
 * Viewer.view(ISBN)
 * (OR)
 * BookDictionary.sortBy (Metadata e.g., Author)
 *
 *
 *
 */