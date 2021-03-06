package com.marmotlabs.garage.service.utils;

import com.marmotlabs.garage.model.Space;
import com.marmotlabs.garage.model.Vehicle;

/**
 * POJO class used as a return type for the enterVehicle() operation.
 * 
 * @author Sofia Craciun
 */
public class EnterVehicleResponse {

    private EnterVehicleStatus status;
    private Vehicle vehicle;
    private Space space;

    public EnterVehicleStatus getStatus() {
        return status;
    }

    public void setStatus(EnterVehicleStatus status) {
        this.status = status;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Space getSpace() {
        return space;
    }

    public void setSpace(Space space) {
        this.space = space;
    }
}
