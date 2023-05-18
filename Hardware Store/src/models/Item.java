package models;

public class Item {
    private String name;
    private double price;
    private int quantity;
    private int asileNum;

    public Item(String name, double price, int quantity, int asileNum) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.asileNum = asileNum;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getAsileNum() {
        return asileNum;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
