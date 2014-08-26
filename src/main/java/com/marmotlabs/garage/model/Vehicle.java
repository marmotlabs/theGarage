package com.marmotlabs.garage.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * <p>
 * Entity class representing a vehicle.</p>
 *
 * <p>
 * A vehicle from our database could be associated to a {@link Space} or
 * not.</p>
 *
 * @author Sofia Craciun
 */
@Entity
@Table(name = "VEHICLE")
@SequenceGenerator(name = "VEHICLE_ID_GEN", sequenceName = "SQ_VEHICLE", allocationSize = 1)
@NamedQuery(name = Vehicle.GET_VEHICLE_BY_LICENSE_PLATE, query = "select v from Vehicle v where v.licensePlate = :licensePlate")
public class Vehicle implements Serializable {

    public static final String GET_VEHICLE_BY_LICENSE_PLATE = "Vehicle.getVehicleByLicensePlate";

    /**
     * Technical ID.
     */
    @Id
    @Column(name = "VEHICLE_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VEHICLE_ID_GEN")
    private Long id;

    /**
     * Vehicle type: CAR or MOTORBIKE (for the moment).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", nullable = false)
    private VehicleType type;

    /**
     * Business ID (uniquely identifies the entity).
     */
    @Column(name = "LICENSE_PLATE", nullable = false, unique = true)
    private String licensePlate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("id", id).
                append("type", type).
                append("licensePlate", licensePlate).
                toString();
    }
}
