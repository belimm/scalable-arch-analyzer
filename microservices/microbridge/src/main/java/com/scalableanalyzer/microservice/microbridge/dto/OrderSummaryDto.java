package com.scalableanalyzer.microservice.microbridge.dto;

public class OrderSummaryDto {

    private Long customerId;
    private Long orderId;
    private Double totalPrice;
    private Integer totalQuantity;

    public OrderSummaryDto() {
    }

    public OrderSummaryDto(Long customerId, Long orderId, Double totalPrice, Integer totalQuantity) {
        this.customerId = customerId;
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.totalQuantity = totalQuantity;
    }


    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}
