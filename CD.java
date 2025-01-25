public class CD extends Item {
    private String company;
    private int duration;

    public CD(String title, String company, int duration, double price) {
        super(title, price);
        this.company = company;
        this.duration = duration;
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

    @Override
    public String toString() {
        return "CD: " + title + ", company: " + company + ", duration: " + duration + " mins, price: $" + price;
    }

    @Override
    public String toCsvString() {
        return "CD," + title + "," + company + "," + duration + "," + price;
    }
}
