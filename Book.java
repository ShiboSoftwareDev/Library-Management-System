public class Book {
    private String title;
    private String category;
    private String author;
    private String email;
    private int quantity;

    public Book(String title, String category, String author, int quantity) {
        this.title = title;
        this.category = category;
        this.author = author;
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String toString() {
        return this.getTitle() + ", category:" + this.getCategory() + " by " + this.getAuthor() + " quantity: "  + this.getQuantity();
    }
    
}
