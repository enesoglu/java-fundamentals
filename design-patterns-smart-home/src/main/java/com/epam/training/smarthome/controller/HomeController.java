package com.epam.training.smarthome.controller;

import com.epam.training.smarthome.domain.devices.AlarmSystem;
import com.epam.training.smarthome.domain.devices.FrontDoor;
import com.epam.training.smarthome.domain.devices.Light;
import com.epam.training.smarthome.domain.devices.coffeemaker.CoffeeMaker;
import com.epam.training.smarthome.domain.devices.coffeemaker.StrongCoffeeCreationStrategy;
import com.epam.training.smarthome.domain.devices.coffeemaker.WeakCoffeeCreationStrategy;
import com.epam.training.smarthome.domain.devices.heatingsystem.HeatingSystemAdapter;
import com.epam.training.smarthome.domain.devices.heatingsystem.LegacyHeatingSystem;
import com.epam.training.smarthome.domain.observer.MessageObserver;

public class HomeController {

    private final Light light;
    private final AlarmSystem alarmSystem;
    private final HeatingSystemAdapter heatingSystemAdapter;
    private final FrontDoor frontDoor;
    private final CoffeeMaker coffeeMaker;

    private HomeController(Light light, AlarmSystem alarmSystem,
                          HeatingSystemAdapter heatingSystemAdapter,
                          FrontDoor frontDoor, CoffeeMaker coffeeMaker) {
        this.light = light;
        this.alarmSystem = alarmSystem;
        this.heatingSystemAdapter = heatingSystemAdapter;
        this.frontDoor = frontDoor;
        this.coffeeMaker = coffeeMaker;
    }

    public void onMovement() {
        if (alarmSystem.isTurnedOn()) {
            alarmSystem.alarm();
        }
        if (!light.isTurnedOn()) {
            light.turnOn();
        } else {
            System.out.println("[HomeController] nothing to do (light is already turned on)");
        }
    }

    public void onGoingHome() {
        if (!heatingSystemAdapter.isTurnedOn()) {
            heatingSystemAdapter.turnOn();
        } else {
            System.out.println("[HomeController] nothing to do (heating system is already turned on)");
        }
    }

    public void onArrivesHome() {
        if (alarmSystem.isTurnedOn()) {
            alarmSystem.turnOff();
        } else {
            System.out.println("[HomeController] nothing to do (alarm system is already turned off)");
        }
        if (!frontDoor.isOpen()) {
            frontDoor.open();
        } else {
            System.out.println("[HomeController] nothing to do (front door is already opened)");
        }
        coffeeMaker.createCoffee();
    }

    public void onChangeToHoliday() {
        coffeeMaker.changeCoffeeCreationMode(new WeakCoffeeCreationStrategy());
    }

    public void onChangeToWorkingDay() {
        coffeeMaker.changeCoffeeCreationMode(new StrongCoffeeCreationStrategy());
    }

    public static class HomeControllerBuilder {

        private final MessageObserver messageObserver;
        private Light light;
        private AlarmSystem alarmSystem;
        private HeatingSystemAdapter heatingSystemAdapter;
        private FrontDoor frontDoor;
        private CoffeeMaker coffeeMaker;

        public HomeControllerBuilder(MessageObserver messageObserver) {
            this.messageObserver = messageObserver;
        }

        public HomeControllerBuilder light(Light light) {
            this.light = light;
            return this;
        }

        public HomeControllerBuilder alarmSystem(AlarmSystem alarmSystem) {
            this.alarmSystem = alarmSystem;
            return this;
        }

        public HomeControllerBuilder heatingSystemAdapter(HeatingSystemAdapter heatingSystemAdapter) {
            this.heatingSystemAdapter = heatingSystemAdapter;
            return this;
        }

        public HomeControllerBuilder frontDoor(FrontDoor frontDoor) {
            this.frontDoor = frontDoor;
            return this;
        }

        public HomeControllerBuilder coffeeMaker(CoffeeMaker coffeeMaker) {
            this.coffeeMaker = coffeeMaker;
            return this;
        }

        public HomeController build() {
            // Default values
            if (light == null) {
                light = new Light(false);
            }
            if (alarmSystem == null) {
                alarmSystem = new AlarmSystem(false);
            }
            if (heatingSystemAdapter == null) {
                heatingSystemAdapter = new HeatingSystemAdapter(new LegacyHeatingSystem(false));
            }
            if (frontDoor == null) {
                frontDoor = new FrontDoor(false);
            }
            if (coffeeMaker == null) {
                coffeeMaker = new CoffeeMaker(new StrongCoffeeCreationStrategy());
            }

            // Add observer to all devices
            light.addObserver(messageObserver);
            alarmSystem.addObserver(messageObserver);
            heatingSystemAdapter.addObserver(messageObserver);
            frontDoor.addObserver(messageObserver);
            coffeeMaker.addObserver(messageObserver);

            return new HomeController(light, alarmSystem, heatingSystemAdapter, frontDoor, coffeeMaker);
        }
    }
}
