package com.marmotlabs.garage.dao;

import com.marmotlabs.garage.model.Vehicle;

/**
 *
 * @author Sofia Craciun <craciun.sofia@gmail.com>
 */
public interface VehicleDao extends AbstractDao< Vehicle, Long> {

    Vehicle getVehicleByLicensePlate(String licensePlate);

}
