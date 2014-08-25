package com.marmotlabs.garage.service.impl;

import com.marmotlabs.garage.dao.SpaceDao;
import com.marmotlabs.garage.dao.VehicleDao;
import com.marmotlabs.garage.model.Space;
import com.marmotlabs.garage.model.Vehicle;
import com.marmotlabs.garage.model.VehicleType;
import com.marmotlabs.garage.service.utils.EnterVehicleResponse;
import com.marmotlabs.garage.service.utils.EnterVehicleStatus;
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
 * @author Sofia Craciun <sofia.craciun@gmail.com>
 */
@RunWith(MockitoJUnitRunner.class)
public class GarageServiceTest {

    @InjectMocks
    GarageServiceImpl garageService;

    @Mock
    private SpaceDao spaceDao;

    @Mock
    private VehicleDao vehicleDao;

    @Test
    public void testGetNumberOfFreeSpaces() {
        final Integer FREE_SPACES = 1;

        Mockito.when(spaceDao.getNumberOfFreeSpaces()).thenReturn(FREE_SPACES);

        Assert.assertEquals(FREE_SPACES, garageService.getNumberOfFreeSpaces());
    }
    
    @Test
    public void testEnterVehicle_GarageIsFull() {
        Mockito.when(spaceDao.getFirstEmptySpace()).thenReturn(null);

        EnterVehicleResponse response = garageService.enterVehicle("M IP 9999", VehicleType.CAR);
        
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getStatus());
        Assert.assertEquals(EnterVehicleStatus.ERROR_GARAGE_IS_FULL, response.getStatus());
    }
    
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

}
