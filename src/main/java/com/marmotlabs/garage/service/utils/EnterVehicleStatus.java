package com.marmotlabs.garage.service.utils;

/**
 * Enum specifying the status of the enterVehicle() operation
 *
 * @author Sofia Craciun <craciun.sofia@gmail.com>
 */
public enum EnterVehicleStatus {

    /**
     * Indicates that the vehicle has been accepted in the garage
     */
    OK,
    /**
     * Indicates that the vehicle has been rejected, because the garage is full
     */
    ERROR_GARAGE_IS_FULL,
    /**
     * Indicates that the vehicle has been rejected, because it is already in
     * the garage
     */
    ERROR_VEHICLE_ALREADY_IN,
    /**
     * Indicates that the vehicle has been rejected, because the none was
     * specified
     */
    ERROR_LICENSE_PLATE_IS_MANDATORY;
}
