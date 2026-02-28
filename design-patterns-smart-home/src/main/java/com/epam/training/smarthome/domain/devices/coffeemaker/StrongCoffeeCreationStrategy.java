package com.epam.training.smarthome.domain.devices.coffeemaker;

public class StrongCoffeeCreationStrategy implements CoffeeCreationStrategy {

    @Override
    public int getCaffeineAmount() {
        return 40;
    }
}
