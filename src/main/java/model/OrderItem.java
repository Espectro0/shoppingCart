package model;

import java.text.DecimalFormat;

public class OrderItem {
    private Product product;
    private Integer quantity;
    private Double subtotal;

    private final DecimalFormat df = new DecimalFormat("#,###.00");

    public OrderItem(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
        this.subtotal = product.getPrice() * quantity;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getSubtotal() {
        return product.getPrice() * quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
        this.subtotal = product.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return product.getName() + " [ " + quantity + " ] - $ " + df.format(subtotal);
    }
}
