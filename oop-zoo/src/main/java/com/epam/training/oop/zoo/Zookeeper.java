package com.epam.training.oop.zoo;

import com.epam.training.oop.zoo.animals.Animal;

import java.util.Arrays;

public class Zookeeper {
    private final String name;
    private final Consumption[] consumption;

    public Zookeeper(String name, Consumption... consumption) {
        this.name = name;
        this.consumption = consumption;
    }


    public void feed(Animal[] animals) {
        for (Animal animal : animals){
            if (Arrays.asList(consumption).contains(animal.getConsumption())) {
                animal.makeSound();
                System.out.println(getName() + " is feeding " +
                        animal.getName() + " the " + animal.getClass().getSimpleName());
            }
        }
    }

    public String getName() {
        return name;
    }
}
