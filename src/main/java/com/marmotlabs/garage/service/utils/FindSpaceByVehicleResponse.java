package com.marmotlabs.garage.service.utils;

import com.marmotlabs.garage.model.Space;

/**
 * POJO class used as a return type for the findSpaceByVehicle() operation.
 *
 * @author Sofia Craciun
 */
public class FindSpaceByVehicleResponse {

    private FindSpaceByVehicleStatus status;
    private Space space;

    public FindSpaceByVehicleStatus getStatus() {
        return status;
    }

    public void setStatus(FindSpaceByVehicleStatus status) {
        this.status = status;
    }

    public Space getSpace() {
        return space;
    }

    public void setSpace(Space space) {
        this.space = space;
    }
}
