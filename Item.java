public abstract class Item {
    protected String title;
    protected int capacity;

    public Item(String title, int capacity) {
        this.title = title;
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public abstract String toCsvString();

    public abstract String toString();
}
