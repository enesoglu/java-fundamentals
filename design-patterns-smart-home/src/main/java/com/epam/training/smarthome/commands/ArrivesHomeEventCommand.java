package com.epam.training.smarthome.commands;

import com.epam.training.smarthome.controller.HomeController;

public class ArrivesHomeEventCommand extends EventCommand {

    public ArrivesHomeEventCommand(HomeController homeController) {
        super(homeController);
    }

    @Override
    public void execute() {
        homeController.onArrivesHome();
    }
}
