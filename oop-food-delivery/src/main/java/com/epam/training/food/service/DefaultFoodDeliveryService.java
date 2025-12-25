package com.epam.training.food.service;

import com.epam.training.food.data.FileDataStore;
import com.epam.training.food.domain.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DefaultFoodDeliveryService implements FoodDeliveryService {

    private final FileDataStore fileDataStore;

    public DefaultFoodDeliveryService(FileDataStore fileDataStore) {
        this.fileDataStore = fileDataStore;
    }

    @Override
    public Customer authenticate(Credentials credentials) throws AuthenticationException {
        List<Customer> customers = fileDataStore.getCustomers();

        for (Customer customer: customers){
            if (customer.getUserName().equals(credentials.getUserName())
            && customer.getPassword().equals(credentials.getPassword())) {
                return customer;
            }
        }

        throw new AuthenticationException("Wrong username and/or password");
    }

    @Override
    public List<Food> listAllFood() {
        return fileDataStore.getFoods();
    }

    @Override
    public void updateCart(Customer customer, Food food, int pieces) throws LowBalanceException {

        if (pieces < 0){
            throw new IllegalArgumentException("Piece cannot be negative");
        }

        Cart cart = customer.getCart();
        List<OrderItem> items = cart.getOrderItems();

        OrderItem existingItem = null;

        //  Is same food is already in the cart?
        for (OrderItem item: items){
            if (item.getFood().equals(food)){
                existingItem = item;
            }
        }

        if (pieces == 0){
            if(existingItem == null){
                throw new IllegalArgumentException("Cannot delete non-existing food");
            }

            // Remove the item and new cart total is = Old Total - (Food Price * Pieces)"
            items.remove(existingItem);
            cart.setPrice(cart.getPrice().subtract(existingItem.getPrice()));

            return;
        }

        BigDecimal newPriceOfItem = food.getPrice().multiply(BigDecimal.valueOf(pieces));
        BigDecimal currentCartTotal = cart.getPrice();

        BigDecimal oldPriceOfItem = (existingItem != null) ? existingItem.getPrice() : BigDecimal.ZERO;

        BigDecimal projectedNewCartTotal = currentCartTotal.subtract(oldPriceOfItem).add(newPriceOfItem);

        // Balance check
        if (projectedNewCartTotal.compareTo(customer.getBalance()) > 0) {
            throw new LowBalanceException("Unsufficient balance! You need: " + projectedNewCartTotal + "EUR.");
        }

        // Update cart
        if (existingItem != null) {
            // Update existing one
            existingItem.setPieces(pieces);
            existingItem.setPrice(newPriceOfItem);
        } else {
            // Create new one and add
            OrderItem newItem = new OrderItem(food, pieces, newPriceOfItem);
            items.add(newItem);
        }

        cart.setPrice(projectedNewCartTotal);
    }

    @Override
    public Order createOrder(Customer customer) throws IllegalStateException {
        Cart cart = customer.getCart();

        if (cart.getOrderItems().isEmpty()){
            throw new IllegalStateException("Cannot create an empty order");
        }

        Order order = new Order(customer);

        cart.setOrderItems(new ArrayList<>());
        cart.setPrice(BigDecimal.ZERO);

        Order savedOrder = fileDataStore.createOrder(order);

        customer.getOrders().add(savedOrder);
        customer.setBalance(customer.getBalance().subtract(order.getPrice()));

        return savedOrder;
    }
}
