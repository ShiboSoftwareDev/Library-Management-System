public abstract class Item {
    protected String title;

    public Item(String title) {
        this.title = title;
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
