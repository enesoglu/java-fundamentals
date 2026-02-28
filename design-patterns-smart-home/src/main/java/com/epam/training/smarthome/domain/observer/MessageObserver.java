package com.epam.training.smarthome.domain.observer;

import java.util.ArrayList;
import java.util.List;

public class MessageObserver implements Observer {

    private final List<String> messages = new ArrayList<>();

    @Override
    public void update(String message) {
        messages.add(message);
    }

    public List<String> getMessages() {
        return messages;
    }

}
