package com.epam.training.food.data;

import com.epam.training.food.domain.Customer;
import com.epam.training.food.domain.Food;
import com.epam.training.food.domain.Order;


import java.util.ArrayList;
import java.util.List;

public class FileDataStore implements DataStore {

    List<Food> foods = new ArrayList<>();
    List<Customer> customers = new ArrayList<>();
    List<Order> orders = new ArrayList<>();

    private final String baseDirPath;

    public FileDataStore(String baseDirPath) {
        this.baseDirPath = baseDirPath;
    }

    public void init() {
        CustomerReader customerReader = new CustomerReader();
        FoodReader foodReader = new FoodReader();

        String customerFolderPath = this.baseDirPath + "/customers.csv";
        String foodsFolderPath = this.baseDirPath + "/foods.csv";

        this.customers = customerReader.read(customerFolderPath);
        this.foods = foodReader.read(foodsFolderPath);
    }

    @Override
    public List<Customer> getCustomers() {
        return customers;
    }

    @Override
    public List<Food> getFoods() {
        return foods;
    }

    @Override
    public List<Order> getOrders() {
        return orders;
    }

    @Override
    public Order createOrder(Order order) {

        long maxId = -1;

        for (Order existingOrder : this.orders) {
            if (existingOrder.getOrderId() > maxId) {
                maxId = existingOrder.getOrderId();
            }
        }

        long newId = maxId + 1;
        order.setOrderId(newId);

        this.orders.add(order);

        for (Customer customer : this.customers) {
            if (customer.getId() == order.getCustomerId()) {
                customer.getOrders().add(order);
                break;
            }
        }

        return order;
    }

    @Override
    public void writeOrders() {
        OrderWriter orderWriter = new OrderWriter();
        orderWriter.writeOrders(this.orders, this.baseDirPath + "/orders.csv");
    }
}
