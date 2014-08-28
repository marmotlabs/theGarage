package com.marmotlabs.garage.dao.impl;

import com.marmotlabs.garage.dao.VehicleDao;
import com.marmotlabs.garage.model.Vehicle;
import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Sofia Craciun
 */
@Repository
public class VehicleDaoImpl extends AbstractDaoImpl<Vehicle, Long> implements VehicleDao {

    public VehicleDaoImpl() {
        super(Vehicle.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vehicle getVehicleByLicensePlate(String licensePlate) {
        Query query = getCurrentSession().getNamedQuery(Vehicle.GET_VEHICLE_BY_LICENSE_PLATE);

        query.setParameter("licensePlate", licensePlate);

        return (Vehicle) query.uniqueResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Vehicle> findAllVehiclesIn() {
        Query query = getCurrentSession().getNamedQuery(Vehicle.GET_ALL_VEHICLES_IN);

        return (List<Vehicle>) query.list();
    }

}
