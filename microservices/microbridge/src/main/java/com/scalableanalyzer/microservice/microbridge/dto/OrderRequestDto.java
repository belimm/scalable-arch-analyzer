package com.scalableanalyzer.microservice.microbridge.dto;

import java.util.List;

public class OrderRequestDto {
    public Long userId;
    public Long addressId;
    public String status;
    public List<OrderItemDto> orderItems;

    public OrderRequestDto() {
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderItemDto> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDto> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return "OrderRequestDto{" +
                "userId=" + userId +
                ", addressId=" + addressId +
                ", status='" + status + '\'' +
                ", orderItems=" + orderItems +
                '}';
    }
}
