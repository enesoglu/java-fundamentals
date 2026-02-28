package com.epam.training.smarthome;

import com.epam.training.smarthome.commands.EventCommandFactory;
import com.epam.training.smarthome.commands.EventCommandType;
import com.epam.training.smarthome.controller.HomeController;
import com.epam.training.smarthome.domain.observer.MessageObserver;

public class Application {

    public static void main(String[] args) {
        Application application = new Application();
        application.run();
    }

    private void run() {
        MessageObserver messageObserver = new MessageObserver();
        HomeController homeController = new HomeController.HomeControllerBuilder(messageObserver).build();
        EventCommandFactory commandFactory = new EventCommandFactory(homeController);

        System.out.println("--> Change to holiday event");
        commandFactory.createEventCommand(EventCommandType.CHANGE_TO_HOLIDAY).execute();
        System.out.println();

        System.out.println("--> Going home event");
        commandFactory.createEventCommand(EventCommandType.GOING_HOME).execute();
        System.out.println();

        System.out.println("--> Movement event");
        commandFactory.createEventCommand(EventCommandType.MOVEMENT).execute();
        System.out.println();

        System.out.println("--> Arrive home event");
        commandFactory.createEventCommand(EventCommandType.ARRIVES_HOME).execute();
        System.out.println();

        System.out.println("--> Movement event");
        commandFactory.createEventCommand(EventCommandType.MOVEMENT).execute();
        System.out.println();

        System.out.println("--> Change to working day event");
        commandFactory.createEventCommand(EventCommandType.CHANGE_TO_WORKING_DAY).execute();
        System.out.println();

        System.out.println("--> Going home event");
        commandFactory.createEventCommand(EventCommandType.GOING_HOME).execute();
        System.out.println();

        System.out.println("--> Arrive home event");
        commandFactory.createEventCommand(EventCommandType.ARRIVES_HOME).execute();
        System.out.println();

        System.out.println("--> Movement event");
        commandFactory.createEventCommand(EventCommandType.MOVEMENT).execute();
        System.out.println();

        System.out.println("all messages dispatched by the devices:");
        for (String message : messageObserver.getMessages()) {
            System.out.println(message);
        }
    }
}
