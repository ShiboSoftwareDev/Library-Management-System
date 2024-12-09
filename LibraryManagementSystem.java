 import java.util.*;

 public class LibraryManagementSystem {
     private Library library;
     private Scanner scanner;

     public LibraryManagementSystem() {
         library = new Library();
         scanner = new Scanner(System.in);
     }

     public void run() {
         System.out.println("'help' shows a list of commands.\n");
         boolean running = true;

         while (running) {
             System.out.print("> ");
             String command = scanner.nextLine().trim().toLowerCase();

             if (command.isEmpty()) {
                 continue;
             }

             String[] parts = command.split(" ");

             switch (parts[0]) {
                 case "help":
                     if (parts.length > 1) {
                         showHelpForCommand(command);
                     } else {
                         showHelp();
                     }
                     break;

                 case "add":
                     handleAdd(command);
                     break;

                 case "remove":
                     handleRemove(command);
                     break;

                 case "find":
                     handleFind(command);
                     break;

                 case "borrow":
                     handleBorrow(command);
                     break;

                 case "return":
                     handleReturn(command);
                     break;

                 case "list":
                     handleList(command);
                     break;

                 case "quit":
                 case "exit":
                     running = false;
                     return;

                 default:
                     System.out.println("Invalid command! Type 'help' for available commands.");
                     break;
             }
         }
     }


     private void showHelp() {
         System.out.println("Available commands:");
         System.out.println("add <title> <category> <author> <quantity>");
         System.out.println("remove <title> <author>");
         System.out.println("find <title>");
         System.out.println("borrow <title>");
         System.out.println("return <title>");
         System.out.println("list all");
         System.out.println("list available books");
         System.out.println("exit");
     }

     private void showHelpForCommand(String command) {
         String cmd = command.substring(5).trim().toLowerCase();

         switch (cmd) {
             case "add":
                 System.out.println("add <title> <category> <author> <quantity>");
                 System.out.println("Adds a new book or updates quantity if it already exists.");
                 break;
             case "remove":
                 System.out.println("remove <title> <author>");
                 System.out.println("Removes a book from the library.");
                 break;
             case "find":
                 System.out.println("find <title>");
                 System.out.println("Searches for a book by title.");
                 break;
             case "borrow":
                 System.out.println("borrow <title>");
                 System.out.println("Borrows a book.");
                 break;
             case "return":
                 System.out.println("return <title>");
                 System.out.println("Returns a book.");
                 break;
             case "list":
                 System.out.println("list [all|available]");
                 System.out.println("Lists all books or only available books.");
                 break;
             default:
                 System.out.println("Unknown command: " + cmd);
                 break;
         }
     }

     private void handleAdd(String command) {
         String[] parts = command.split(" ");
         if (parts.length != 5) {
             System.out.println("Invalid format! Use: add <title> <category> <author> <quantity>");
             return;
         }

         try {
             String title = parts[1];
             String category = parts[2];
             String author = parts[3];
             int quantity = Integer.parseInt(parts[4]);

             if (quantity <= 0) {
                 System.out.println("Quantity must be greater than 0!");
                 return;
             }

             Book book = new Book(title, category, author, quantity);
             library.addBook(book);
             System.out.println("Book added successfully.");
         } catch (NumberFormatException e) {
             System.out.println("Invalid quantity! Please enter a valid number.");
         }
     }

     private void handleRemove(String command) {
         String[] parts = command.split(" ");
         if (parts.length != 3) {
             System.out.println("Invalid format! Use: remove <title> <author>");
             return;
         }

         String title = parts[1];
         String author = parts[2];
         library.removeBook(title, author);
     }

     private void handleFind(String command) {
         String[] parts = command.split(" ");
         if (parts.length != 2) {
             System.out.println("Invalid format! Use: find <title>");
             return;
         }

         String title = parts[1];
         Book book = library.findBook(title);

         if (book != null) {
             System.out.println(book.toString());
         } else {
             System.out.println("Book not found.");
         }
     }

     private void handleBorrow(String command) {
         String[] parts = command.split(" ");
         if (parts.length != 2) {
             System.out.println("Invalid format! Use: borrow <title>");
             return;
         }

         String title = parts[1];
         library.borrowBook(title);
     }

     private void handleReturn(String command) {
         String[] parts = command.split(" ");
         if (parts.length != 2) {
             System.out.println("Invalid format! Use: return <title>");
             return;
         }

         String title = parts[1];
         library.returnBook(title);
     }

     private void handleList(String command) {
         String[] parts = command.split(" ");
         if (parts.length != 2) {
             System.out.println("Invalid format! Use: list [all|available]");
             return;
         }

         switch (parts[1]) {
             case "all":
                 library.listAllBooks();
                 break;
             case "available":
                 library.listAvailableBooks();
                 break;
             default:
                 System.out.println("Use 'list all' or 'list available'");
                 break;
         }
     }

     public static void main(String[] args) {
        new LibraryManagementSystem().run();
     }
 }
