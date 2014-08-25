package com.marmotlabs.garage.dao;

import com.marmotlabs.garage.model.Vehicle;

/**
 * DAO handling the Vehicle entities.
 *
 * @author Sofia Craciun <craciun.sofia@gmail.com>
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

}
