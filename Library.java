import java.util.*;

public class Library {
    private List<Item> items;

    public Library() {
        items = FileHandler.loadItems();
    }

    public void addItem(Item item) {
        if (item instanceof Book) {
            Book newBook = (Book) item;
            for (Item existingItem : items) {
                if (existingItem instanceof Book) {
                    Book existingBook = (Book) existingItem;
                    if (isSameBook(existingBook, newBook)) {
                        existingBook.setQuantity(existingBook.getQuantity() + newBook.getQuantity());
                        return;
                    }
                }
            }
        }
        items.add(item);
    }

    private boolean isSameBook(Book book1, Book book2) {
        return book1.getTitle().equalsIgnoreCase(book2.getTitle())
                && book1.getAuthor().equalsIgnoreCase(book2.getAuthor())
                && book1.getCategory().equalsIgnoreCase(book2.getCategory());
    }

    public Item findItem(String title) {
        for (Item item : items) {
            if (item.getTitle().equalsIgnoreCase(title)) {
                return item;
            }
        }
        return null;
    }

    public void removeItem(String title, String identifier) {
        Item item = findItem(title);
        if (item instanceof Book) {
            Book book = (Book) item;
            if (book.getAuthor().equalsIgnoreCase(identifier)) {
                items.remove(book);
                System.out.println("Book removed successfully");
                return;
            }
        } else if (item instanceof CD) {
            CD cd = (CD) item;
            if (cd.getCompany().equalsIgnoreCase(identifier)) {
                items.remove(cd);
                System.out.println("CD removed successfully");
                return;
            }
        }
        System.out.println("Item not found");
    }

    public void borrowItem(String title) {
        Item item = findItem(title);
        if (item instanceof Book) {
            Book book = (Book) item;
            if (book.getQuantity() > 0) {
                book.setQuantity(book.getQuantity() - 1);
                System.out.println("Book borrowed successfully");
            } else {
                System.out.println("No copies available");
            }
        } else if (item instanceof CD) {
            System.out.println("CDs cannot be borrowed");
        } else {
            System.out.println("Item not found");
        }
    }

    public void returnItem(String title) {
        Item item = findItem(title);
        if (item instanceof Book) {
            Book book = (Book) item;
            book.setQuantity(book.getQuantity() + 1);
            System.out.println("Book returned successfully");
        } else if (item instanceof CD) {
            System.out.println("CDs cannot be returned");
        } else {
            System.out.println("Item not found");
        }
    }

    public void listAllItems() {
        if (items.isEmpty()) {
            System.out.println("No items in library");
            return;
        }
        for (Item item : items) {
            System.out.println(item.toString());
        }
    }

    public void listAvailableItems() {
        boolean foundItems = false;
        for (Item item : items) {
            if (item instanceof Book) {
                Book book = (Book) item;
                if (book.getQuantity() > 0) {
                    System.out.println(book.toString());
                    foundItems = true;
                }
            } else if (item instanceof CD) {
                System.out.println(item.toString());
                foundItems = true;
            }
        }
        if (!foundItems) {
            System.out.println("No available items");
        }
    }

    public List<Item> findItemsByTitle(String title) {
        List<Item> matchingItems = new ArrayList<>();
        for (Item item : items) {
            if (item.getTitle().equalsIgnoreCase(title)) {
                matchingItems.add(item);
            }
        }
        return matchingItems;
    }

    public void saveToFile() {
        FileHandler.saveItems(items);
    }

    public List<Item> getAllItems() {
        return new ArrayList<>(items);
    }
}
