package com.epam.training.smarthome.domain.devices;

import com.epam.training.smarthome.domain.observer.Observable;

public class FrontDoor extends Observable {

    private boolean isOpen;

    public FrontDoor(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public void open() {
        if (!isOpen) {
            isOpen = true;
            notifyObservers("[FrontDoor] open");
            System.out.println("[FrontDoor] open");
        }
    }

    public void close() {
        if (isOpen) {
            isOpen = false;
            notifyObservers("[FrontDoor] close");
            System.out.println("[FrontDoor] close");
        }
    }

    public boolean isOpen() {
        return isOpen;
    }
}
