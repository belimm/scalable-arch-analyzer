package com.scalableanalyzer.microservice.user.entity;

import com.scalableanalyzer.microservice.user.entity.Order;
import com.scalableanalyzer.microservice.user.entity.Product;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "User_Order_Item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;
    private Integer quantity;
    private Double price;
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product productOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;


    public OrderItem() {
        this.createdAt = LocalDateTime.now();
    }

    public OrderItem(Long orderItemId, Integer quantity, Double price, Product productOrder) {
        this.orderItemId = orderItemId;
        this.quantity = quantity;
        this.price = price;
        this.productOrder = productOrder;

        this.createdAt = LocalDateTime.now();
    }

    public OrderItem(Long orderItemId, Integer quantity, Double price) {
        this.orderItemId = orderItemId;
        this.quantity = quantity;
        this.price = price;
        this.createdAt = LocalDateTime.now();
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Product getProductOrder() {
        return productOrder;
    }

    public void setProductOrder(Product productOrder) {
        this.productOrder = productOrder;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "orderItemId=" + orderItemId +
                ", quantity=" + quantity +
                ", price=" + price +
                ", createdAt=" + createdAt +
                ", productOrder=" + productOrder +
                '}';
    }
}
