package com.example.orderservice.service;


import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.entity.Order;
import com.example.orderservice.feign.CustomerClient;
import com.example.orderservice.feign.ProductClient;
import com.example.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerClient customerClient;

    @Autowired
    private ProductClient productClient;

    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(order -> {
            // Récupérer les détails du client et du produit via Feign
            Object customerDetails = customerClient.getCustomerById(order.getCustomerId());
            Object productDetails = productClient.getProductById(order.getProductId());

            // Construire la réponse enrichie
            OrderResponse response = new OrderResponse();
            response.setOrderId(order.getId());
            response.setCustomerDetails(customerDetails);
            response.setProductDetails(productDetails);
            response.setQuantity(order.getQuantity());

            return response;
        }).collect(Collectors.toList());
    }

    public Order createOrder(Order order) {
        // Valider le client via Customer Service
        Object customer = customerClient.getCustomerById(order.getCustomerId());
        System.out.println("customer: " );
        if (customer == null) {
            throw new RuntimeException("Customer not found!");
        }

        // Valider le produit via Product Service
        Object product = productClient.getProductById(order.getProductId());
        if (product == null) {
            throw new RuntimeException("Product not found!");
        }

        // Définir la date de commande et sauvegarder
        order.setOrderDate(java.time.LocalDateTime.now());
        return orderRepository.save(order);
    }
}
