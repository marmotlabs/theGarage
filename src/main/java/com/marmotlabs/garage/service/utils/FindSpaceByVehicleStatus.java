package com.marmotlabs.garage.service.utils;

/**
 * Enum specifying the status of the findSpaceByVehicle() operation
 *
 * @author Sofia Craciun
 */
public enum FindSpaceByVehicleStatus {

    /**
     * Indicates that the Space for the provided vehicle was found
     */
    SPACE_FOUND,
    /**
     * Indicates that the Space for the provided vehicle was not found
     */
    SPACE_NOT_FOUND;
}
