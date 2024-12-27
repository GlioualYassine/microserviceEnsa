package com.example.orderservice.dto;


public class OrderResponse {

    private Long orderId;
    private Object customerDetails;
    private Object productDetails;
    private Integer quantity;

    // Getters et Setters
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Object getCustomerDetails() {
        return customerDetails;
    }

    public void setCustomerDetails(Object customerDetails) {
        this.customerDetails = customerDetails;
    }

    public Object getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(Object productDetails) {
        this.productDetails = productDetails;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
