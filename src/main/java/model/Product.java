package model;

import java.text.DecimalFormat;

public class Product {
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private Integer stock;

    private final DecimalFormat df = new DecimalFormat("#,###.00");

    public Product(Integer id, String name, String description, Double price, Integer stock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public Integer getId() {
        return id;
    }

    public Integer getStock() {
        return stock;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return stock > 0;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return id + " | " + name + " | " + description + " | " + df.format(price) + " | " + (isAvailable() ? stock : "Out of Stock");
    }
}