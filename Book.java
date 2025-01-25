public class Book extends Item {
    private String category;
    private String author;
    private int quantity;

    public Book(String title, String category, String author, int quantity, double price) {
        super(title, price);
        this.category = category;
        this.author = author;
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Book: " + title + ", category: " + category + " by " + author + ", quantity: " + quantity + ", price: $" + price;
    }

    @Override
    public String toCsvString() {
        return "BOOK," + title + "," + category + "," + author + "," + quantity + "," + price;
    }
    
}
