 import java.util.*;

 public class Library {
     private List<Book> books;
     public Library() {
         books = new ArrayList<>();
     }

     public void addBook(Book book) {
         for (Book existingBook : books) {
             if (isSameBook(existingBook, book)) {
                 existingBook.setQuantity(existingBook.getQuantity() + book.getQuantity());
                 return;
             }
         }
         books.add(book);
     }

     private boolean isSameBook(Book book1, Book book2) {
         return book1.getTitle().equalsIgnoreCase(book2.getTitle())
             && book1.getAuthor().equalsIgnoreCase(book2.getAuthor())
             && book1.getCategory().equalsIgnoreCase(book2.getCategory());
     }

     public Book findBook(String title) {
         for (Book book : books) {
             if (book.getTitle().equalsIgnoreCase(title)) {
                 return book;
             }
         }
         return null;
     }

    public void removeBook(String title, String author) {
         Book book = findBook(title);
         if (book != null && book.getAuthor().equalsIgnoreCase(author)) {
             books.remove(book);
             System.out.println("Book removed successfully");
         } else {
             System.out.println("Book not found");
         }
     }

     public void borrowBook(String title) {
         Book book = findBook(title);
         if (book != null) {
             if (book.getQuantity() > 0) {
                 book.setQuantity(book.getQuantity() - 1);
                 System.out.println("Book borrowed successfully");
             } else {
                 System.out.println("No copies available");
             }
         } else {
             System.out.println("Book not found");
         }
     }

     public void returnBook(String title) {
         Book book = findBook(title);
         if (book != null) {
             book.setQuantity(book.getQuantity() + 1);
             System.out.println("Book returned successfully");
         } else {
             System.out.println("Book not found");
         }
     }

     public void listAllBooks() {
         if (books.isEmpty()) {
             System.out.println("No books in library");
             return;
         }
         for (Book book : books) {
             System.out.println(book.toString());
         }
     }

     public void listAvailableBooks() {
         boolean foundBooks = false;
         for (Book book : books) {
             if (book.getQuantity() > 0) {
                 System.out.println(book.toString());
                 foundBooks = true;
             }
         }
         if (!foundBooks) {
             System.out.println("No available books");
         }
     }

     public List<Book> findBookByTitle(String title) {
         List<Book> matchingBooks = new ArrayList<>();
         for (Book book : books) {
             if (book.getTitle().equalsIgnoreCase(title)) {
                 matchingBooks.add(book);
             }
         }
         return matchingBooks;
     }
 }
