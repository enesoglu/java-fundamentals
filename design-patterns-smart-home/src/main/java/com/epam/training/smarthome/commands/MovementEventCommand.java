package com.epam.training.smarthome.commands;

import com.epam.training.smarthome.controller.HomeController;

public class MovementEventCommand extends EventCommand {

    public MovementEventCommand(HomeController homeController) {
        super(homeController);
    }

    @Override
    public void execute() {
        homeController.onMovement();
    }
}
