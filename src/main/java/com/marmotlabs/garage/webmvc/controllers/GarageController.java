package com.marmotlabs.garage.webmvc.controllers;

import com.marmotlabs.garage.webmvc.controllers.utils.Pages;
import com.marmotlabs.garage.model.VehicleType;
import com.marmotlabs.garage.service.GarageService;
import com.marmotlabs.garage.service.utils.EnterVehicleResponse;
import com.marmotlabs.garage.service.utils.ExitVehicleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * An MVC controller handling all the vehicle operations.
 * 
 * @author Sofia Craciun
 */
@Controller
@RequestMapping(value = "/vehicle")
public class GarageController {

    @Autowired
    private GarageService garageService;

    /**
     * <p>
     * Handles the enter vehicle operation.</p>
     *
     * <p>
     * It requires a vehicleType and a licensePlate.</p>
     *
     * @param vehicleType
     * @param licensePlate
     * @param model
     * @return
     */
    @RequestMapping(value = "/enter", method = RequestMethod.GET)
    public String enterVehicle(@RequestParam String vehicleType, @RequestParam String licensePlate, Model model) {
        EnterVehicleResponse enterVehicleResponse = garageService.enterVehicle(licensePlate, VehicleType.valueOf(vehicleType));

        model.addAttribute("enterVehicleStatus", enterVehicleResponse.getStatus().toString());
        model.addAttribute("enterSpace", enterVehicleResponse.getSpace());
        model.addAttribute("enterVehicle", enterVehicleResponse.getVehicle());

        model.addAttribute("numberOfFreeSpaces", garageService.getNumberOfFreeSpaces());

        return Pages.INDEX;
    }

    /**
     * <p>
     * Handles the exit vehicle operation.</p>
     *
     * <p>
     * It requires a licensePlate.</p>
     *
     * @param licensePlate
     * @param model
     * @return
     */
    @RequestMapping(value = "/exit", method = RequestMethod.GET)
    public String exitVehicle(@RequestParam String licensePlate, Model model) {
        ExitVehicleResponse exitVehicleResponse = garageService.exitVehicle(licensePlate);

        model.addAttribute("exitVehicleStatus", exitVehicleResponse.getStatus().toString());
        model.addAttribute("exitSpace", exitVehicleResponse.getSpace());
        model.addAttribute("exitVehicle", exitVehicleResponse.getVehicle());

        model.addAttribute("numberOfFreeSpaces", garageService.getNumberOfFreeSpaces());

        return Pages.INDEX;
    }
}
