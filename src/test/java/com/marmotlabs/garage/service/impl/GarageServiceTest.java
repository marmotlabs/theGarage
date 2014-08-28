package com.marmotlabs.garage.service.impl;

import com.marmotlabs.garage.dao.SpaceDao;
import com.marmotlabs.garage.dao.VehicleDao;
import com.marmotlabs.garage.model.Space;
import com.marmotlabs.garage.model.Vehicle;
import com.marmotlabs.garage.model.VehicleType;
import com.marmotlabs.garage.service.utils.EnterVehicleResponse;
import com.marmotlabs.garage.service.utils.EnterVehicleStatus;
import com.marmotlabs.garage.service.utils.ExitVehicleResponse;
import com.marmotlabs.garage.service.utils.ExitVehicleStatus;
import com.marmotlabs.garage.service.utils.FindSpaceByVehicleResponse;
import com.marmotlabs.garage.service.utils.FindSpaceByVehicleStatus;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * {@link GarageServiceImpl}
 *
 * @author Sofia Craciun
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class GarageServiceTest {

    /**
     * The class being tested
     */
    @InjectMocks
    GarageServiceImpl garageService;

    /**
     * A mock (fake) object that will be injected into the garageService
     */
    @Mock
    private SpaceDao spaceDao;

    /**
     * A mock (fake) object that will be injected into the garageService
     */
    @Mock
    private VehicleDao vehicleDao;

    /**
     * {@link GarageServiceImpl#getNumberOfFreeSpaces() }
     */
    @Test
    public void testGetNumberOfFreeSpaces() {
        final Integer FREE_SPACES = 1;

        Mockito.when(spaceDao.getNumberOfFreeSpaces()).thenReturn(FREE_SPACES);

        Assert.assertEquals(FREE_SPACES, garageService.getNumberOfFreeSpaces());
    }

    /**
     * Tests the enter garage operation, when the garage is full.
     *
     * Expecting error message.
     *
     * {@link GarageServiceImpl#enterVehicle(java.lang.String, com.marmotlabs.garage.model.VehicleType)}
     */
    @Test
    public void testEnterVehicle_GarageIsFull() {
        Mockito.when(spaceDao.getFirstEmptySpace()).thenReturn(null);

        EnterVehicleResponse response = garageService.enterVehicle("M IP 9999", VehicleType.CAR);

        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getStatus());
        Assert.assertEquals(EnterVehicleStatus.ERROR_GARAGE_IS_FULL, response.getStatus());
    }

    /**
     * Tests the enter garage operation, when the vehicle is already in.
     *
     * Expecting error message.
     *
     * {@link GarageServiceImpl#enterVehicle(java.lang.String, com.marmotlabs.garage.model.VehicleType)}
     */
    @Test
    public void testEnterVehicle_VehicleAlreadyIn() {
        Space firstEmtpySpace = new Space();
        Vehicle vehicle = new Vehicle();
        Space spaceAlreadyOccupiedByVehicle = new Space();

        Mockito.when(spaceDao.getFirstEmptySpace()).thenReturn(firstEmtpySpace);
        Mockito.when(vehicleDao.getVehicleByLicensePlate(Matchers.anyString())).thenReturn(vehicle);
        Mockito.when(spaceDao.getSpaceByLicensePlate(Matchers.anyString())).thenReturn(spaceAlreadyOccupiedByVehicle);

        EnterVehicleResponse response = garageService.enterVehicle("M IP 9999", VehicleType.CAR);

        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getStatus());
        Assert.assertEquals(EnterVehicleStatus.ERROR_VEHICLE_ALREADY_IN, response.getStatus());
    }

    /**
     * Tests the enter garage operation with no error message.
     *
     * {@link GarageServiceImpl#enterVehicle(java.lang.String, com.marmotlabs.garage.model.VehicleType)}
     */
    @Test
    public void testEnterVehicle_OK() {
        Space firstEmtpySpace = new Space();

        Mockito.when(spaceDao.getFirstEmptySpace()).thenReturn(firstEmtpySpace);
        Mockito.when(vehicleDao.getVehicleByLicensePlate(Matchers.anyString())).thenReturn(null);
        Mockito.when(spaceDao.getSpaceByLicensePlate(Matchers.anyString())).thenReturn(null);

        EnterVehicleResponse response = garageService.enterVehicle("M IP 9999", VehicleType.CAR);

        Mockito.verify(spaceDao, Mockito.times(1)).saveOrUpdate(firstEmtpySpace);
        Mockito.verify(vehicleDao, Mockito.times(1)).saveOrUpdate(Matchers.any(Vehicle.class));

        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getStatus());
        Assert.assertEquals(EnterVehicleStatus.OK, response.getStatus());
    }

    /**
     * Tests the enter garage operation, with no (empty) license plate.
     *
     * Expecting error message.
     *
     * {@link GarageServiceImpl#enterVehicle(java.lang.String, com.marmotlabs.garage.model.VehicleType)}
     */
    @Test
    public void testEnterVehicle_LicensePlateMandatory() {
        EnterVehicleResponse response = garageService.enterVehicle("", VehicleType.CAR);

        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getStatus());
        Assert.assertEquals(EnterVehicleStatus.ERROR_LICENSE_PLATE_IS_MANDATORY, response.getStatus());
    }

    /**
     * Tests the exit garage operation, when the vehicle is not in.
     *
     * Expecting error message.
     *
     * {@link GarageServiceImpl#exitVehicle(java.lang.String)}
     */
    @Test
    public void testExitVehicle_VehicleNotInGarage() {
        Mockito.when(spaceDao.getSpaceByLicensePlate(Matchers.anyString())).thenReturn(null);

        ExitVehicleResponse response = garageService.exitVehicle("M IP 9999");

        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getStatus());
        Assert.assertEquals(ExitVehicleStatus.ERROR_VEHICLE_NOT_IN_THE_GARAGE, response.getStatus());
    }

    /**
     * Tests the exit garage operation with no error message.
     *
     * {@link GarageServiceImpl#exitVehicle(java.lang.String)}
     */
    @Test
    public void testExitVehicle_OK() {
        Space currentSpaceForVehicle = new Space();

        Mockito.when(spaceDao.getSpaceByLicensePlate(Matchers.anyString())).thenReturn(currentSpaceForVehicle);

        ExitVehicleResponse response = garageService.exitVehicle(Matchers.anyString());

        Mockito.verify(spaceDao, Mockito.times(1)).saveOrUpdate(currentSpaceForVehicle);

        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getStatus());
        Assert.assertEquals(ExitVehicleStatus.OK, response.getStatus());
    }

    /**
     * Tests the search of space, with no error message.
     *
     * {@link GarageServiceImpl#findSpaceByVehicle(java.lang.String) }
     */
    @Test
    public void testFindSpaceByVehicle_SpaceFound() {
        Space space = new Space();
        Mockito.when(spaceDao.getSpaceByLicensePlate(Matchers.anyString())).thenReturn(space);

        FindSpaceByVehicleResponse response = garageService.findSpaceByVehicle("M IP 9999");

        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getStatus());
        Assert.assertEquals(space, response.getSpace());
        Assert.assertEquals(FindSpaceByVehicleStatus.SPACE_FOUND, response.getStatus());
    }

    /**
     * Tests the search of space, with error message.
     *
     * {@link GarageServiceImpl#findSpaceByVehicle(java.lang.String) }
     */
    @Test
    public void testFindSpaceByVehicle_SpaceNotFound() {
        Mockito.when(spaceDao.getSpaceByLicensePlate(Matchers.anyString())).thenReturn(null);

        FindSpaceByVehicleResponse response = garageService.findSpaceByVehicle("M IP 9999");

        Assert.assertNotNull(response);
        Assert.assertNull(response.getSpace());
        Assert.assertNotNull(response.getStatus());
        Assert.assertEquals(FindSpaceByVehicleStatus.SPACE_NOT_FOUND, response.getStatus());
    }

    /**
     *
     * {@link GarageServiceImpl#getAllVehiclesIn()}
     */
    @Test
    public void testFindAllVehiclesIn() {
        ArrayList<Vehicle> allVehicleIn = new ArrayList<Vehicle>();

        Mockito.when(vehicleDao.findAllVehiclesIn()).thenReturn(allVehicleIn);

        Assert.assertEquals(allVehicleIn, garageService.getAllVehiclesIn());
    }
}
