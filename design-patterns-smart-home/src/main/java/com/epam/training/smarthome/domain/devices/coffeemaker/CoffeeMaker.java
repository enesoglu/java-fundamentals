package com.epam.training.smarthome.domain.devices.coffeemaker;

import com.epam.training.smarthome.domain.observer.Observable;

public class CoffeeMaker extends Observable {

    private CoffeeCreationStrategy coffeeCreationStrategy;

    public CoffeeMaker(CoffeeCreationStrategy coffeeCreationStrategy) {
        this.coffeeCreationStrategy = coffeeCreationStrategy;
    }

    public void createCoffee() {
        String message = "[CoffeeMaker] create coffee with " + coffeeCreationStrategy.getCaffeineAmount() + "mg caffeine";
        notifyObservers(message);
        System.out.println(message);
    }

    public void changeCoffeeCreationMode(CoffeeCreationStrategy coffeeCreationStrategy) {
        this.coffeeCreationStrategy = coffeeCreationStrategy;
        String message = "[CoffeeMaker] change the type of coffee";
        notifyObservers(message);
        System.out.println(message);
    }

    public CoffeeCreationStrategy getCoffeeCreationStrategy() {
        return coffeeCreationStrategy;
    }
}
