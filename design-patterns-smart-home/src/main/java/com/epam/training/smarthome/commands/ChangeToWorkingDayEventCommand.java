package com.epam.training.smarthome.commands;

import com.epam.training.smarthome.controller.HomeController;

public class ChangeToWorkingDayEventCommand extends EventCommand {

    public ChangeToWorkingDayEventCommand(HomeController homeController) {
        super(homeController);
    }

    @Override
    public void execute() {
        homeController.onChangeToWorkingDay();
    }
}
