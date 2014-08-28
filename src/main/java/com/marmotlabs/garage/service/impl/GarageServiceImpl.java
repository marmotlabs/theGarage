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
import com.marmotlabs.garage.service.utils.FindSpaceByVehicleResponse;
import com.marmotlabs.garage.service.utils.FindSpaceByVehicleStatus;
import java.util.List;
import org.apache.log4j.Logger;
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

    private static final Logger logger = Logger.getLogger(GarageServiceImpl.class);

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
    public EnterVehicleResponse enterVehicle(final String licensePlate, final VehicleType vehicleType) {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering vehicle: licensePlate=" + licensePlate + ", vehicleType=" + vehicleType);
        }

        EnterVehicleResponse result = new EnterVehicleResponse();

        if (licensePlate == null || licensePlate.isEmpty()) {
            if (logger.isInfoEnabled()) {
                logger.info("The user tried to enter a vehicle without specifying a licensePlate");
            }
            // If the licensePlate is null, the car is rejected, return ERROR_LICENSE_PLATE_IS_MANDATORY
            result.setStatus(EnterVehicleStatus.ERROR_LICENSE_PLATE_IS_MANDATORY);
        } else {
            // Get the first empty space
            Space space = spaceDao.getFirstEmptySpace();

            if (space == null) {
                if (logger.isInfoEnabled()) {
                    logger.info("No space left in the garage for vehicle: licensePlate=" + licensePlate + ", vehicleType=" + vehicleType);
                }
                // If there is no empty space found, the garage is full, return ERROR_GARAGE_IS_FULL
                result.setStatus(EnterVehicleStatus.ERROR_GARAGE_IS_FULL);
            } else {
                // Get vehicle by licensePlate
                Vehicle vehicle = vehicleDao.getVehicleByLicensePlate(licensePlate);

                if (vehicle == null) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Vehicle does not exist in the database, create vehicle: licensePlate=" + licensePlate + ", vehicleType=" + vehicleType);
                    }

                    // If vehicle does not exist, create vehicle
                    vehicle = new Vehicle();
                    vehicle.setLicensePlate(licensePlate);
                    vehicle.setType(vehicleType);

                    vehicleDao.saveOrUpdate(vehicle);
                }

                // Check if vehicle is already in the garage
                Space currentSpaceForVehicle = spaceDao.getSpaceByLicensePlate(vehicle.getLicensePlate());
                if (currentSpaceForVehicle == null) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Entering vehicle: vehicle= " + vehicle.toString());
                    }
                    // Most optimistic case: garage is not full + vehicle not already in

                    // Level is lazy, but we will need it in the view, where the session will be closed
                    Hibernate.initialize(space.getLevel());

                    // Assign a space to the current vehicle
                    if (logger.isDebugEnabled()) {
                        logger.debug("Assigning space= /" + space.toString() + " to vehicle= /" + vehicle.toString());
                    }
                    space.setVehicle(vehicle);
                    spaceDao.saveOrUpdate(space);
                    // Populate the result
                    result.setStatus(EnterVehicleStatus.OK);
                    result.setSpace(space);
                    result.setVehicle(vehicle);
                } else {
                    // This vehicle is already in the garage, return ERROR_VEHICLE_ALREADY_IN
                    if (logger.isInfoEnabled()) {
                        logger.info("This vehicle is already in the garage: licensePlate=" + licensePlate + ", vehicleType=" + vehicleType);
                    }
                    result.setStatus(EnterVehicleStatus.ERROR_VEHICLE_ALREADY_IN);
                }
            }
        }

        return result;
    }

    @Override
    @Transactional(readOnly = false)
    public ExitVehicleResponse exitVehicle(final String licensePlate) {
        if (logger.isDebugEnabled()) {
            logger.debug("Exit vehicle: licensePlate= " + licensePlate);
        }
        ExitVehicleResponse result = new ExitVehicleResponse();

        // Check if vehicle is in the garage 
        Space currentSpaceForVehicle = spaceDao.getSpaceByLicensePlate(licensePlate);

        if (currentSpaceForVehicle == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Vehicle with licensePlate= " + licensePlate + " is not in the garage");
            }

            // If there is no empty space found, return ERROR_VEHICLE_NOT_IN_THE_GARAGE
            result.setStatus(ExitVehicleStatus.ERROR_VEHICLE_NOT_IN_THE_GARAGE);
        } else {
            // Level is lazy, byt we will need the level in the view, where the session will be closed
            Hibernate.initialize(currentSpaceForVehicle.getLevel());

            if (logger.isInfoEnabled()) {
                logger.info("Vehicle with licensePlate= " + licensePlate + " was removed");
            }

            currentSpaceForVehicle.setVehicle(null);
            spaceDao.saveOrUpdate(currentSpaceForVehicle);

            // Populate the result
            result.setStatus(ExitVehicleStatus.OK);
            result.setSpace(currentSpaceForVehicle);
            result.setVehicle(vehicleDao.getVehicleByLicensePlate(licensePlate));
        }

        return result;
    }

    @Override
    public FindSpaceByVehicleResponse findSpaceByVehicle(final String licensePlate) {
        FindSpaceByVehicleResponse response = new FindSpaceByVehicleResponse();
        Space space = spaceDao.getSpaceByLicensePlate(licensePlate);
        if (space != null) {
            // Level is lazy, but we will need it in the view, where the session will be closed
            Hibernate.initialize(space.getLevel());
            response.setSpace(space);
            response.setStatus(FindSpaceByVehicleStatus.SPACE_FOUND);
        } else {
            response.setStatus(FindSpaceByVehicleStatus.SPACE_NOT_FOUND);
        }
        return response;
    }

    @Override
    public List<Vehicle> getAllVehiclesIn() {
        // Returns the list with vehicles in the garage
        List<Vehicle> allVehiclesIn = vehicleDao.findAllVehiclesIn();

        return allVehiclesIn;
    }
}
