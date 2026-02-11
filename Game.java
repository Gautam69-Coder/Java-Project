import java.awt.Color;

public class Game {
    private String id;
    private String title;
    private double price;
    private String description;
    private Color color;

    public Game(String id, String title, double price, String description, Color color) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.color = color;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public double getPrice() { return price; }
    public String getDescription() { return description; }
    public Color getColor() { return color; }

    @Override
    public String toString() { return title + " - $" + price; }
}
