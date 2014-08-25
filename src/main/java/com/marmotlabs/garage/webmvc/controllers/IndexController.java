package com.marmotlabs.garage.webmvc.controllers;

import com.marmotlabs.garage.service.GarageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Sofia Craciun <sofia.craciun@gmail.com>
 */
@Controller
@RequestMapping(value = "/")
public class IndexController {

    @Autowired
    private GarageService garageService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        Integer numberOfFreeSpaces = garageService.getNumberOfFreeSpaces();

        model.addAttribute("numberOfFreeSpaces", numberOfFreeSpaces);
        return Pages.INDEX;
    }

}
