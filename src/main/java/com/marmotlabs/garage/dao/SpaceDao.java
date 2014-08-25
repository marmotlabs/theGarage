package com.marmotlabs.garage.dao;

import com.marmotlabs.garage.model.Space;

/**
 *
 * @author Sofia Craciun <craciun.sofia@gmail.com>
 */
public interface SpaceDao extends AbstractDao<Space, Long> {

    /**
     * Returns the number of free spaces from all levels.
     *
     * @return
     */
    Integer getNumberOfFreeSpaces();

    /**
     * Returns the space where the car with the given license plate is parked.
     *
     * @param licensePlate
     * @return
     */
    Space getSpaceByLicensePlate(String licensePlate);

    /**
     * Returns the first empty space.
     *
     * @return
     */
    Space getFirstEmptySpace();
}
