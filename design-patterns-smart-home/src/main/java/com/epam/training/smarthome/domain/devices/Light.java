package com.epam.training.smarthome.domain.devices;

import com.epam.training.smarthome.domain.observer.Observable;

public class Light extends Observable {

    private boolean isTurnedOn;

    public Light(boolean isTurnedOn) {
        this.isTurnedOn = isTurnedOn;
    }

    public void turnOn() {
        if (!isTurnedOn) {
            isTurnedOn = true;
            notifyObservers("[Light] turn on");
            System.out.println("[Light] turn on");
        }
    }

    public void turnOff() {
        if (isTurnedOn) {
            isTurnedOn = false;
            notifyObservers("[Light] turn off");
            System.out.println("[Light] turn off");
        }
    }

    public boolean isTurnedOn() {
        return isTurnedOn;
    }
}
