package com.marmotlabs.garage.service.utils;

/**
 * Enum specifying the status of the exitVehicle() operation
 *
 * @author Sofia Craciun
 */
public enum ExitVehicleStatus {

    /**
     * Indicates that the vehicle has been successfully removed from the garage
     */
    OK,
    /**
     * Indicates that the vehicle removal has been rejected, because it was not
     * found in the garage
     */
    ERROR_VEHICLE_NOT_IN_THE_GARAGE;
}
