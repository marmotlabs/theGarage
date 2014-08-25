package com.marmotlabs.garage.service.impl;

import com.marmotlabs.garage.dao.SpaceDao;
import com.marmotlabs.garage.dao.VehicleDao;
import com.marmotlabs.garage.model.Space;
import com.marmotlabs.garage.model.Vehicle;
import com.marmotlabs.garage.model.VehicleType;
import com.marmotlabs.garage.service.GarageService;
import com.marmotlabs.garage.service.utils.EnterVehicleResponse;
import com.marmotlabs.garage.service.utils.EnterVehicleStatus;
import com.marmotlabs.garage.service.utils.ExitVehicleResponse;
import com.marmotlabs.garage.service.utils.ExitVehicleStatus;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Stateless implementation of the {@link GarageService}.
 * 
 * {@inheritDoc}
 */
@Service
@Transactional(readOnly = true)
public class GarageServiceImpl implements GarageService {

    @Autowired
    private SpaceDao spaceDao;

    @Autowired
    private VehicleDao vehicleDao;

    @Override
    public Integer getNumberOfFreeSpaces() {
        return spaceDao.getNumberOfFreeSpaces();
    }

    @Override
    @Transactional(readOnly = false)
    public EnterVehicleResponse enterVehicle(String licensePlate, VehicleType vehicleType) {
        EnterVehicleResponse result = new EnterVehicleResponse();

        // Get the first empty space
        Space space = spaceDao.getFirstEmptySpace();

        if (space == null) {
            // If there is no empty space found, the garage is full, return ERROR_GARAGE_IS_FULL
            result.setStatus(EnterVehicleStatus.ERROR_GARAGE_IS_FULL);
        } else {
            // Get vehicle by licensePlate
            Vehicle vehicle = vehicleDao.getVehicleByLicensePlate(licensePlate);

            if (vehicle == null) {
                // If vehicle does not exist, create vehicle
                vehicle = new Vehicle();
                vehicle.setLicensePlate(licensePlate);
                vehicle.setType(vehicleType);

                vehicleDao.saveOrUpdate(vehicle);
            }

            // Check if vehicle is already in the garage
            Space currentSpaceForVehicle = spaceDao.getSpaceByLicensePlate(vehicle.getLicensePlate());
            if (currentSpaceForVehicle == null) {
                // Most optimistic case: garage is not full + vehicle not already in

                // Level is lazy, but we will need it in the view, where the session will be closed
                Hibernate.initialize(space.getLevel());

                // Assign a space to the current vehicle
                space.setVehicle(vehicle);
                spaceDao.saveOrUpdate(space);
                // Populate the result
                result.setStatus(EnterVehicleStatus.OK);
                result.setSpace(space);
                result.setVehicle(vehicle);
            } else {
                // This vehicle is already in the garage, return ERROR_VEHICLE_ALREADY_IN
                result.setStatus(EnterVehicleStatus.ERROR_VEHICLE_ALREADY_IN);
            }
        }

        return result;
    }

    @Override
    @Transactional(readOnly = false)
    public ExitVehicleResponse exitVehicle(String licensePlate) {
        ExitVehicleResponse result = new ExitVehicleResponse();

        // Check if vehicle is in the garage 
        Space currentSpaceForVehicle = spaceDao.getSpaceByLicensePlate(licensePlate);

        if (currentSpaceForVehicle == null) {
            // If there is no empty space found, return ERROR_VEHICLE_NOT_IN_THE_GARAGE
            result.setStatus(ExitVehicleStatus.ERROR_VEHICLE_NOT_IN_THE_GARAGE);
        } else {
            // Level is lazy, byt we will need the level in the view, where the session will be closed
            Hibernate.initialize(currentSpaceForVehicle.getLevel());

            currentSpaceForVehicle.setVehicle(null);
            spaceDao.saveOrUpdate(currentSpaceForVehicle);

            // Populate the result
            result.setStatus(ExitVehicleStatus.OK);
            result.setSpace(currentSpaceForVehicle);
            result.setVehicle(vehicleDao.getVehicleByLicensePlate(licensePlate));
        }

        return result;
    }
}
