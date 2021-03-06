package com.marmotlabs.garage.webmvc.controllers;

import com.marmotlabs.garage.model.Vehicle;
import com.marmotlabs.garage.webmvc.controllers.utils.Pages;
import com.marmotlabs.garage.service.GarageService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Index (main page) MVC controller.
 *
 * @author Sofia Craciun
 */
@Controller
@RequestMapping(value = "/")
public class IndexController {

    @Autowired
    private GarageService garageService;

    /**
     * Handles the root application request.
     *
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        Integer numberOfFreeSpaces = garageService.getNumberOfFreeSpaces();
        List<Vehicle> allVehiclesIn = garageService.getAllVehiclesIn();

        model.addAttribute("numberOfFreeSpaces", numberOfFreeSpaces);
        model.addAttribute("allVehiclesIn", allVehiclesIn);
        return Pages.INDEX;
    }

}
