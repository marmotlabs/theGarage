package com.marmotlabs.garage.webmvc.controllers;

import com.marmotlabs.garage.model.Space;
import com.marmotlabs.garage.model.Vehicle;
import com.marmotlabs.garage.service.GarageService;
import com.marmotlabs.garage.service.utils.FindSpaceByVehicleResponse;
import com.marmotlabs.garage.webmvc.controllers.utils.Pages;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * An MVC controller handling all the manager operations.
 *
 * @author Sofia Craciun <sofia.craciun@gmail.com>
 */
@Controller
@RequestMapping(value = "/garageManager")
public class GarageManagerController {

    @Autowired
    private GarageService garageService;

    /**
     * <p>
     * Handles the findSpaceByVehicle operation.</p>
     *
     * <p>
     * It requires a licensePlate.</p>
     *
     * @param licensePlate
     * @param model
     * @return
     */
    @RequestMapping(value = "/findSpace", method = RequestMethod.GET)
    public String findSpaceByVehicle(@RequestParam String licensePlate, Model model) {
        FindSpaceByVehicleResponse response = garageService.findSpaceByVehicle(licensePlate);
        Space space = response.getSpace();

        model.addAttribute("findSpaceStatus", response.getStatus().toString());
        model.addAttribute("spaceFound", space);
        model.addAttribute("allVehiclesIn", garageService.getAllVehiclesIn());
        return Pages.GARAGE_MANAGER;
    }

    /**
     * <p>
     * Handles the getAllVehiclesIn the garage request.</p>
     *
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String getAllVehiclesIn(Model model) {
        List<Vehicle> allVehiclesIn = garageService.getAllVehiclesIn();

        model.addAttribute("allVehiclesIn", allVehiclesIn);
        return Pages.GARAGE_MANAGER;
    }

}
