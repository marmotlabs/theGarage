package com.marmotlabs.garage.service;

import com.marmotlabs.garage.model.Space;
import com.marmotlabs.garage.model.Vehicle;

/**
 *
 * @author Sofia Craciun <sofia.craciun@gmail.com>
 */
public class ExitVehicleResponse {

    private ExitVehicleStatus status;
    private Vehicle vehicle;
    private Space space;

    public ExitVehicleStatus getStatus() {
        return status;
    }

    public void setStatus(ExitVehicleStatus status) {
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
