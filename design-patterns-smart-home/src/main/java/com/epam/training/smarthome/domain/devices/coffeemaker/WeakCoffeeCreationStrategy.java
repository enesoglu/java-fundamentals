package com.epam.training.smarthome.domain.devices.coffeemaker;

public class WeakCoffeeCreationStrategy implements CoffeeCreationStrategy {

    @Override
    public int getCaffeineAmount() {
        return 20;
    }
}
