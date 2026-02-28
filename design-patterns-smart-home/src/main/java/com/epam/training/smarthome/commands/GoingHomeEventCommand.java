package com.epam.training.smarthome.commands;

import com.epam.training.smarthome.controller.HomeController;

public class GoingHomeEventCommand extends EventCommand {

    public GoingHomeEventCommand(HomeController homeController) {
        super(homeController);
    }

    @Override
    public void execute() {
        homeController.onGoingHome();
    }
}
