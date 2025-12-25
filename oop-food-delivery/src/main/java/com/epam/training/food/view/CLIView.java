package com.epam.training.food.view;

import com.epam.training.food.domain.Credentials;
import com.epam.training.food.domain.Customer;
import com.epam.training.food.domain.Food;
import com.epam.training.food.domain.Order;
import com.epam.training.food.values.FoodSelection;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class CLIView implements View {

    private final Scanner scanner;

    public CLIView() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public Credentials readCredentials() {

        Credentials credentials = new Credentials();

        System.out.println("Enter customer name:");
        credentials.setUserName(scanner.nextLine());

        System.out.println("Enter customer password:");
        credentials.setPassword(scanner.nextLine());

        return credentials;
    }

    @Override
    public void printWelcomeMessage(Customer customer) {
        System.out.println("Welcome, " + customer.getName()
                + ". Your balance is: " + customer.getBalance() + " EUR.");
    }

    @Override
    public void printAllFoods(List<Food> foods) {
        System.out.println("\nFoods offered today:");
        for (Food food : foods) {
            System.out.printf("- %s %g EUR each\n", food.getName(), food.getPrice());
        }
    }

    @Override
    public FoodSelection readFoodSelection(List<Food> foods) {
        System.out.println("Please enter the name and amount of food (separated by comma) you would like to buy:");

        String input = scanner.nextLine();

        if (input.isEmpty()) {
            return FoodSelection.NONE;
        }

        try {
            String[] parts = input.split(",");
            String foodName = parts[0].trim();
            int amount = Integer.parseInt(parts[1].trim());

            Food foundFood = null;

            for (Food food : foods) {
                if (food.getName().equals(foodName)) {
                    foundFood = food;
                    break;
                }
            }

            if (foundFood != null)
                return new FoodSelection(foundFood, amount);
            else
                return FoodSelection.NONE;

        } catch (Exception e) {
            return FoodSelection.NONE;
        }
    }

    @Override
    public void printAddedToCart(Food food, int pieces) {
        System.out.printf("Added %d piece(s) of %s to the cart.\n", pieces, food.getName());
    }

    @Override
    public void printErrorMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void printOrderCreatedStatement(Order order, BigDecimal balance) {
        String orderStatement = "Order %s has been confirmed.\n";

        String balanceStatement = "Your balance is %s EUR.\n";
        String thanksStatement = "Thank you for your purchase.\n";

        System.out.printf(orderStatement + balanceStatement + thanksStatement
                , order.getFormattedString(), balance);
    }
}
