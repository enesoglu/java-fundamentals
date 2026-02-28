package com.epam.training.smarthome.domain.devices.heatingsystem;

import com.epam.training.smarthome.domain.observer.Observable;

public class HeatingSystemAdapter extends Observable implements HeatingSystem {

    private final LegacyHeatingSystem legacyHeatingSystem;

    public HeatingSystemAdapter(LegacyHeatingSystem legacyHeatingSystem) {
        this.legacyHeatingSystem = legacyHeatingSystem;
    }

    @Override
    public void turnOn() {
        if (!legacyHeatingSystem.isTurnedOn()) {
            legacyHeatingSystem.operate(true);
            notifyObservers("[HeatingSystem] turn on");
            System.out.println("[HeatingSystem] turn on");
        }
    }

    @Override
    public void turnOff() {
        if (legacyHeatingSystem.isTurnedOn()) {
            legacyHeatingSystem.operate(false);
            notifyObservers("[HeatingSystem] turn off");
            System.out.println("[HeatingSystem] turn off");
        }
    }

    @Override
    public boolean isTurnedOn() {
        return legacyHeatingSystem.isTurnedOn();
    }
}
