package com.marmotlabs.garage.service;

import com.marmotlabs.garage.service.utils.ExitVehicleResponse;
import com.marmotlabs.garage.service.utils.EnterVehicleResponse;
import com.marmotlabs.garage.model.VehicleType;
import com.marmotlabs.garage.service.utils.FindSpaceByVehicleResponse;

/**
 * <p>
 * The main application service, containing all the 3 possible actions.</p>
 *
 * <p>
 * Transactions are isolated on this layer. Default behavior is that
 * transactions are readOnly, if not specified otherwise at method-level.</p>
 *
 * @author Sofia Craciun
 */
public interface GarageService {

    /**
     * Returns the number of free spaces from all levels.
     *
     * Does not return null.
     *
     * @return the number of free spaces from all levels
     */
    Integer getNumberOfFreeSpaces();

    /**
     * Returns the response containing the vehicle, space and message.
     *
     * Does not return null.
     *
     * @param licensePlate the unique license plate
     * @param type the vehicle type
     * @return the response containing the vehicle, space and message.
     */
    EnterVehicleResponse enterVehicle(String licensePlate, VehicleType type);

    /**
     * Returns the response containing the vehicle, space and message.
     *
     * Does not return null.
     *
     * @param licensePlate
     * @return the response containing the vehicle, space and message.
     */
    ExitVehicleResponse exitVehicle(String licensePlate);

    /**
     * Returns the response containing the Space occupied by the vehicle with
     * the provided licensePlate, or a null Space if none found.
     *
     * The response itself could not be null.
     *
     * @param licensePlate
     * @return the response containing a nullable Space occupied by the vehicle
     */
    FindSpaceByVehicleResponse findSpaceByVehicle(String licensePlate);

}
