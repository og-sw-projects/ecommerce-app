package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.client.ProductClient;
import com.ecommerce.orderservice.dto.Product;
import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.repo.OrderRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRep orderRep;

    @Autowired
    private ProductClient productClient;

    public List<Order> getAllOrders() {
        return orderRep.findAll();
    }

    public Order getOrderById(String id) {
        return orderRep.findById(id).orElse(null);
    }

    public Order addOrder(Order order) {
        Product product = productClient.getProductById(order.getProductId());
        if (product != null) {
            return orderRep.save(order);
        } else {
            throw new RuntimeException("Product not found");
        }
    }
}
