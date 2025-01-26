import java.util.*;
import javax.swing.JOptionPane;

public class Library {
    private List<Item> items;

    public Library() {
        items = FileHandler.loadItems();
    }



    private boolean hasConflictingName(Item newItem) {
        String newTitle = newItem.getTitle();
        String newIdentifier = "";
        
        if (newItem instanceof Book) {
            newIdentifier = ((Book)newItem).getAuthor();
        } else if (newItem instanceof CD) {
            newIdentifier = ((CD)newItem).getCompany();
        }

        for (Item existingItem : items) {
            if (!existingItem.getClass().equals(newItem.getClass()) && 
                existingItem.getTitle().equalsIgnoreCase(newTitle)) {
                if (existingItem instanceof Book) {
                    if (((Book)existingItem).getAuthor().equalsIgnoreCase(newIdentifier)) {
                        return true;
                    }
                } else if (existingItem instanceof CD) {
                    if (((CD)existingItem).getCompany().equalsIgnoreCase(newIdentifier)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void addItem(Item item) {
        String message = "";
        
        if (hasConflictingName(item)) {
            message = "Cannot add item - an item of different type with same name and author/company already exists";
        } else {
            if (item instanceof Book) {
                Book newBook = (Book) item;
                for (Item existingItem : items) {
                    if (existingItem instanceof Book) {
                        Book existingBook = (Book) existingItem;
                        if (existingBook.isSameBook(newBook)) {
                            existingBook.setQuantity(existingBook.getQuantity() + newBook.getQuantity());
                            existingBook.setCapacity(existingBook.getCapacity() + newBook.getQuantity());
                            message = "Book quantity updated successfully";
                            break;
                        }
                    }
                }
                if (message.isEmpty()) {
                    items.add(item);
                    message = "Book added successfully";
                }
            } else if (item instanceof CD) {
                CD newCD = (CD) item;
                for (Item existingItem : items) {
                    if (existingItem instanceof CD) {
                        CD existingCD = (CD) existingItem;
                        if (existingCD.isSameCD(newCD)) {
                            existingCD.setQuantity(existingCD.getQuantity() + newCD.getQuantity());
                            existingCD.setCapacity(existingCD.getCapacity() + newCD.getQuantity());
                            message = "CD quantity updated successfully";
                            break;
                        }
                    }
                }
                if (message.isEmpty()) {
                    items.add(item);
                    message = "CD added successfully";
                }
            }
        }
        
        if (message.startsWith("Cannot")) {
            JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, message, "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    public Item findItem(String title) {
        for (Item item : items) {
            if (item.getTitle().equalsIgnoreCase(title)) {
                return item;
            }
        }
        return null;
    }

    public List<Item> findAllItems(String title) {
        List<Item> foundItems = new ArrayList<>();
        for (Item item : items) {
            if (item.getTitle().equalsIgnoreCase(title)) {
                foundItems.add(item);
            }
        }
        return foundItems;
    }

    public void removeItem(String title, String identifier) {
        List<Item> foundItems = findAllItems(title);
        boolean itemRemoved = false;
        
        for (Item item : foundItems) {
            if (item instanceof Book) {
                Book book = (Book) item;
                if (book.getAuthor().equalsIgnoreCase(identifier)) {
                    items.remove(book);
                    itemRemoved = true;
                }
            } else if (item instanceof CD) {
                CD cd = (CD) item;
                if (cd.getCompany().equalsIgnoreCase(identifier)) {
                    items.remove(cd);
                    itemRemoved = true;
                }
            }
        }
        
        if (!itemRemoved) {
        }
    }

    public void borrowItem(String title) {
        Item item = findItem(title);
        if (item instanceof Book) {
            Book book = (Book) item;
            if (book.getQuantity() > 0) {
                book.setQuantity(book.getQuantity() - 1);
            } else {
            }
        } else if (item instanceof CD) {
            CD cd = (CD) item;
            if (cd.getQuantity() > 0) {
                cd.setQuantity(cd.getQuantity() - 1);
            } else {
                System.out.println("No copies available");
            }
        } else {
        }
    }

    public void returnItem(String title) {
        Item item = findItem(title);
        if (item instanceof Book) {
            Book book = (Book) item;
            if (book.getQuantity() < book.getCapacity()) {
                book.setQuantity(book.getQuantity() + 1);
            } else {
            }
        } else if (item instanceof CD) {
            CD cd = (CD) item;
            if (cd.getQuantity() < cd.getCapacity()) {
                cd.setQuantity(cd.getQuantity() + 1);
            } else {
            }
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
