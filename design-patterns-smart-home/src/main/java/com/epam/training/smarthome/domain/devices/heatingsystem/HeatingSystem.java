package com.epam.training.smarthome.domain.devices.heatingsystem;

public interface HeatingSystem {
    void turnOn();
    void turnOff();
    boolean isTurnedOn();
}
