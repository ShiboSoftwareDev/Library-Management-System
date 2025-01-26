public class Book extends Item {
    private String category;
    private String author;
    private int quantity;

    public Book(String title, String category, String author, int quantity) {
        super(title, quantity);
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
        return "Book: " + title + ", category: " + category + " by " + author + ", Available: " + quantity + "/"
                + capacity;
    }

    @Override
    public String toCsvString() {
        return "BOOK," + title + "," + category + "," + author + "," + quantity + "," + capacity;
    }

    public boolean isSameBook(Book other) {
        return this.getTitle().equalsIgnoreCase(other.getTitle())
                && this.getAuthor().equalsIgnoreCase(other.getAuthor())
                && this.getCategory().equalsIgnoreCase(other.getCategory());
    }

}
