package model;

import java.util.List;

public class Order {
    private String id;
    private List<OrderItem> orderItems;
    private Double total;
    private Double discount;
    private String date;
    private Boolean isCheckedOut = false;

    public Order(String id, List<OrderItem> orderItems, Double total) {
        this.id = id;
        this.orderItems = orderItems;
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public Double getTotal() {
        return orderItems.stream()
                .mapToDouble(OrderItem::getSubtotal)
                .sum();
    }

    public Boolean getCheckedOut() {
        return isCheckedOut;
    }

    public void setCheckedOut(Boolean checkedOut) {
        isCheckedOut = checkedOut;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return id + " - " + orderItems + " - " + total;
    }
}
