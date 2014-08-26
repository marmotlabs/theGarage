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
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author Sofia Craciun {@link GarageServiceImpl}
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class GarageServiceTest {

    @InjectMocks
    GarageServiceImpl garageService;

    @Mock
    private SpaceDao spaceDao;

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
}
