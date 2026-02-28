package com.epam.training.smarthome.domain.devices;

import com.epam.training.smarthome.domain.observer.Observable;

public class AlarmSystem extends Observable {

    private boolean isTurnedOn;

    public AlarmSystem(boolean isTurnedOn) {
        this.isTurnedOn = isTurnedOn;
    }

    public void turnOn() {
        if (!isTurnedOn) {
            isTurnedOn = true;
            notifyObservers("[AlarmSystem] turn on");
            System.out.println("[AlarmSystem] turn on");
        }
    }

    public void turnOff() {
        if (isTurnedOn) {
            isTurnedOn = false;
            notifyObservers("[AlarmSystem] turn off");
            System.out.println("[AlarmSystem] turn off");
        }
    }

    public void alarm() {
        notifyObservers("[AlarmSystem] alarm");
        System.out.println("[AlarmSystem] alarm");
    }

    public boolean isTurnedOn() {
        return isTurnedOn;
    }
}
