import java.io.*;
import java.util.*;

public class FileHandler {
    private static final String FILE_PATH = "library_items.txt";

    public static List<Item> loadItems() {
        List<Item> items = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals("BOOK")) {
                    items.add(new Book(parts[1], parts[2], parts[3], Integer.parseInt(parts[4])));
                    items.get(items.size() - 1).setCapacity(Integer.parseInt(parts[5]));
                } else if (parts[0].equals("CD")) {
                    items.add(new CD(parts[1], parts[2], Integer.parseInt(parts[3]), Integer.parseInt(parts[4])));
                    items.get(items.size() - 1).setCapacity(Integer.parseInt(parts[5]));
                }
            }
        } catch (IOException e) {
            System.out.println("No existing items file found. Starting with empty library.");
        }
        return items;
    }

    public static void saveItems(List<Item> items) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Item item : items) {
                writer.println(item.toCsvString());
            }
        } catch (IOException e) {
            System.out.println("Error saving items to file: " + e.getMessage());
        }
    }
}
