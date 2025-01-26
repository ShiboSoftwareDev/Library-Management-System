public class CD extends Item {
    private String company;
    private int duration;
    private int quantity;

    public CD(String title, String company, int duration, int quantity) {
        super(title, quantity);
        this.company = company;
        this.duration = duration;
        this.quantity = quantity;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CD: " + title + ", company: " + company + ", duration: " + duration + " mins, Available: " + quantity + "/" + capacity;
    }

    @Override
    public String toCsvString() {
        return "CD," + title + "," + company + "," + duration + "," + quantity + "," + capacity;
    }

    public boolean isSameCD(CD other) {
        return this.getTitle().equalsIgnoreCase(other.getTitle())
                && this.getCompany().equalsIgnoreCase(other.getCompany());
    }
}
