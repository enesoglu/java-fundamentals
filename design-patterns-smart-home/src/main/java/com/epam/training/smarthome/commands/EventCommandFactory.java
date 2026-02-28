package com.epam.training.smarthome.commands;

import com.epam.training.smarthome.controller.HomeController;

public class EventCommandFactory {

    private final HomeController homeController;

    public EventCommandFactory(HomeController homeController) {
        this.homeController = homeController;
    }

    public EventCommand createEventCommand(EventCommandType type) {
        switch (type) {
            case GOING_HOME:
                return new GoingHomeEventCommand(homeController);
            case ARRIVES_HOME:
                return new ArrivesHomeEventCommand(homeController);
            case MOVEMENT:
                return new MovementEventCommand(homeController);
            case CHANGE_TO_HOLIDAY:
                return new ChangeToHolidayEventCommand(homeController);
            case CHANGE_TO_WORKING_DAY:
                return new ChangeToWorkingDayEventCommand(homeController);
            default:
                throw new IllegalArgumentException("Unknown event type: " + type);
        }
    }

}
