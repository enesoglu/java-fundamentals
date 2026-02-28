package com.epam.training.smarthome.commands;

import com.epam.training.smarthome.controller.HomeController;

public class ChangeToHolidayEventCommand extends EventCommand {

    public ChangeToHolidayEventCommand(HomeController homeController) {
        super(homeController);
    }

    @Override
    public void execute() {
        homeController.onChangeToHoliday();
    }
}
