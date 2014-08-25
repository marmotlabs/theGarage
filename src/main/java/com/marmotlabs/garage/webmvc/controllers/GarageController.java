package com.marmotlabs.garage.webmvc.controllers;

import com.marmotlabs.garage.model.VehicleType;
import com.marmotlabs.garage.service.GarageService;
import com.marmotlabs.garage.service.EnterVehicleResponse;
import com.marmotlabs.garage.service.ExitVehicleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Sofia Craciun <sofia.craciun@gmail.com>
 */
@Controller
@RequestMapping(value = "/vehicle")
public class GarageController {

    @Autowired
    private GarageService garageService;

    @RequestMapping(value = "/enter", method = RequestMethod.GET)
    public String enterVehicle(@RequestParam String vehicleType, @RequestParam String licensePlate, Model model) {

        EnterVehicleResponse enterVehicleResponse = garageService.enterVehicle(licensePlate, VehicleType.valueOf(vehicleType));
        model.addAttribute("enterVehicleStatus", enterVehicleResponse.getStatus().toString());

        model.addAttribute("enterSpace", enterVehicleResponse.getSpace());
        model.addAttribute("enterVehicle", enterVehicleResponse.getVehicle());

        Integer numberOfFreeSpaces = garageService.getNumberOfFreeSpaces();
        model.addAttribute("numberOfFreeSpaces", numberOfFreeSpaces);

        return Pages.INDEX;
    }

    @RequestMapping(value = "/exit", method = RequestMethod.GET)
    public String exitVehicle(@RequestParam String licensePlate, Model model) {

        ExitVehicleResponse exitVehicleResponse = garageService.exitVehicle(licensePlate);

        model.addAttribute("exitVehicleStatus", exitVehicleResponse.getStatus().toString());

        model.addAttribute("exitSpace", exitVehicleResponse.getSpace());
        model.addAttribute("exitVehicle", exitVehicleResponse.getVehicle());

        Integer numberOfFreeSpaces = garageService.getNumberOfFreeSpaces();
        model.addAttribute("numberOfFreeSpaces", numberOfFreeSpaces);

        return Pages.INDEX;
    }
}
