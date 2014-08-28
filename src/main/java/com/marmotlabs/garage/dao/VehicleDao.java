package com.marmotlabs.garage.dao;

import com.marmotlabs.garage.model.Vehicle;
import java.util.List;

/**
 * DAO handling the Vehicle entities.
 *
 * @author Sofia Craciun
 */
public interface VehicleDao extends AbstractDao<Vehicle, Long> {

    /**
     * Returns the vehicle with the received license plate, or null if none
     * found.
     *
     * @param licensePlate the unique license plate of a vehicle
     * @return the vehicle having the received license plate, or null if none
     * found
     */
    Vehicle getVehicleByLicensePlate(String licensePlate);

    /**
     * Returns the list of vehicles in the garage
     *
     * @return the list of vehicle in the garage
     */
    List<Vehicle> findAllVehiclesIn();
}
