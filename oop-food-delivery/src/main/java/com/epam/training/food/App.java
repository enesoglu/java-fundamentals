package com.epam.training.food;

import com.epam.training.food.data.FileDataStore;
import com.epam.training.food.domain.Customer;
import com.epam.training.food.domain.Order;
import com.epam.training.food.service.AuthenticationException;
import com.epam.training.food.service.DefaultFoodDeliveryService;
import com.epam.training.food.service.FoodDeliveryService;
import com.epam.training.food.service.LowBalanceException;
import com.epam.training.food.values.FoodSelection;
import com.epam.training.food.view.CLIView;
import com.epam.training.food.view.View;

public class App {
    public static void main(String[] args) {

        FileDataStore dataStore = new FileDataStore("test");

        dataStore.init();

        FoodDeliveryService service = new DefaultFoodDeliveryService(dataStore);

        View view = new CLIView();

        // Authentication
        Customer loggedInCustomer;
        try {
            var credentials = view.readCredentials();
            loggedInCustomer = service.authenticate(credentials);
            view.printWelcomeMessage(loggedInCustomer);
        } catch (AuthenticationException e) {
            view.printErrorMessage(e.getMessage());
            return;
        }

        // Loop for ordering
        view.printAllFoods(service.listAllFood());

        while (true) {
            FoodSelection selection = view.readFoodSelection(service.listAllFood());

            if (selection.amount() == -1) {
                break;
            }

            try {
                service.updateCart(loggedInCustomer, selection.food(), selection.amount());
                view.printAddedToCart(selection.food(), selection.amount());
            } catch (LowBalanceException | IllegalArgumentException e) {
                view.printErrorMessage(e.getMessage());
            }
        }

        // Create order
        try {
            Order order = service.createOrder(loggedInCustomer);
            view.printOrderCreatedStatement(order, loggedInCustomer.getBalance());

            dataStore.writeOrders();

        } catch (IllegalStateException e) {
            view.printErrorMessage(e.getMessage());
        }
    }
}