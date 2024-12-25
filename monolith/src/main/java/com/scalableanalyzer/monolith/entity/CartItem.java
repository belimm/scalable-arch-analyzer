package com.scalableanalyzer.monolith.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "Cart_Item")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;
    private Integer quantity;
    private Double price;
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public CartItem() {
        this.createdAt = LocalDateTime.now();
    }

    public CartItem(Long cartItemId, Integer quantity, Double price) {
        this.cartItemId = cartItemId;
        this.quantity = quantity;
        this.price = price;
        this.createdAt = LocalDateTime.now();
    }

    public CartItem(Long cartItemId, Integer quantity, Double price, Product product) {
        this.cartItemId = cartItemId;
        this.quantity = quantity;
        this.price = price;
        this.product = product;
        this.createdAt = LocalDateTime.now();
    }

    public CartItem(Long cartItemId, Integer quantity, Double price,  Cart cart, Product product) {
        this.cartItemId = cartItemId;
        this.quantity = quantity;
        this.price = price;
        this.createdAt = LocalDateTime.now();
        this.cart = cart;
        this.product = product;
    }

    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "cartItemId=" + cartItemId +
                ", quantity=" + quantity +
                ", price=" + price +
                ", createdAt=" + createdAt +
                ", cart=" + cart +
                ", product=" + product +
                '}';
    }
}
