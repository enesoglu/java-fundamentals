package com.epam.training.smarthome.domain.devices.heatingsystem;

public class LegacyHeatingSystem {

    private boolean isTurnedOn;

    public LegacyHeatingSystem(boolean isTurnedOn) {
        this.isTurnedOn = isTurnedOn;
    }

    public void operate(boolean turnOn) {
        this.isTurnedOn = turnOn;
    }

    public boolean isTurnedOn() {
        return isTurnedOn;
    }
}
