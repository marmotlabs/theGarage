package com.marmotlabs.garage.dao;

import com.marmotlabs.garage.model.Space;

/**
 * DAO handling the Space entities.
 *
 * @author Sofia Craciun <craciun.sofia@gmail.com>
 */
public interface SpaceDao extends AbstractDao<Space, Long> {

    /**
     * Returns the number of free spaces from all levels.
     *
     * @return the number of free spaces from all levels
     */
    Integer getNumberOfFreeSpaces();

    /**
     * Returns the Space where the car with the given license plate is parked,
     * or null if the vehicle is not in the garage.
     *
     * @param licensePlate the unique license plate of a vehicle
     * @return the designated Space, or null if none found
     */
    Space getSpaceByLicensePlate(String licensePlate);

    /**
     * Returns the first empty Space, or null if the garage is full.
     *
     * @return the first empty Space, or null if none found
     */
    Space getFirstEmptySpace();
}
