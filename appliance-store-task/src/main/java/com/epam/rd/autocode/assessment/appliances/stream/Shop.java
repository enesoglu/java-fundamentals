package com.epam.rd.autocode.assessment.appliances.stream;

import com.epam.rd.autocode.assessment.appliances.model.*;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Shop implements Add, Find, Sort {
    private Set<Client> clients = new HashSet<>();
    private Set<Employee> employees = new HashSet<>();
    private Set<Manufacturer> manufacturers = new HashSet<>();
    private Set<Appliance> appliances = new HashSet<>();
    private Set<Order> orders = new HashSet<>();

    public Set<Client> getClients() {
        return clients;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public Set<Manufacturer> getManufacturers() {
        return manufacturers;
    }

    public Set<Appliance> getAppliances() {
        return appliances;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    @Override
    public void addClient(Client client) {
        clients.add(client);
    }

    @Override
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    @Override
    public void addAppliance(Appliance appliance) {
        appliances.add(appliance);
    }

    @Override
    public void addOrder(Order order) {
        orders.add(order);
    }

    @Override
    public void addManufacturer(Manufacturer manufacturer) {
        manufacturers.add(manufacturer);
    }

    @Override
    public Manufacturer findManufacturerById(long id) {
        return manufacturers.stream()
                .filter(Objects::nonNull)
                .filter(m -> m.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Manufacturer with id=" + id + " was not found"));
    }

    @Override
    public Manufacturer findManufacturerByName(String name) {
        return manufacturers.stream()
                .filter(Objects::nonNull)
                .filter(m -> Objects.equals(m.getName(), name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Manufacturer with name=" + name + " was not found"));
    }

    @Override
    public List<Order> findOrderByEmployee(Employee employee) {
        return orders.stream()
                .filter(Objects::nonNull)
                .filter(o -> Objects.equals(o.getEmployee(), employee))
                .collect(Collectors.toList());
    }

    @Override
    public Order findCheapestOrder() {
        return orders.stream()
                .filter(Objects::nonNull)
                .min(Comparator.comparing(this::getOrderAmount))
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public Order findMostExpensiveOrder() {
        return orders.stream()
                .filter(Objects::nonNull)
                .max(Comparator.comparing(this::getOrderAmount))
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public List<Manufacturer> sortManufacturersByName() {
        // manufacturers without a name go to the end of the list
        return manufacturers.stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(Manufacturer::getName, Comparator.nullsLast(Comparator.naturalOrder())))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<Order> sortOrderByClientId() {
        return orders.stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(o -> o.getClient().getId()))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<Appliance> sortAppliancesByCategory() {
        return appliances.stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(Appliance::getCategory))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<Order> sortOrderByAmount() {
        return orders.stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(this::getOrderAmount))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    // sum of the prices of all appliances in the order
    private BigDecimal getOrderAmount(Order order) {
        if (order.getAppliances() == null) {
            return BigDecimal.ZERO;
        }
        return order.getAppliances().values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
