package com.marmotlabs.garage.service;

import com.marmotlabs.garage.model.VehicleType;

/**
 *
 * @author Sofia Craciun <sofia.craciun@gmail.com>
 */
public interface GarageService {

    /**
     * Returns the number of free spaces from all levels.
     *
     * @return
     */
    Integer getNumberOfFreeSpaces();

    EnterVehicleResponse enterVehicle(String licensePlate, VehicleType type);

    ExitVehicleResponse exitVehicle(String licensePlate);

}
