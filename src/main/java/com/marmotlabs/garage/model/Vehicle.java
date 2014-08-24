package com.marmotlabs.garage.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *
 * @author Sofia Craciun <sofia.craciun@gmail.com>
 */
@Entity
@Table(name = "VEHICLE")
@SequenceGenerator(name = "VEHICLE_ID_GEN", sequenceName = "SQ_VEHICLE", allocationSize = 1)
public class Vehicle implements Serializable {

    @Id
    @Column(name = "VEHICLE_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VEHICLE_ID_GEN")
    private Long id;

    @Column(name = "LICENSE_PLATE", nullable = false, unique = true)
    private String licensePlate;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", nullable = false)
    private TypeOfVehicle type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public TypeOfVehicle getType() {
        return type;
    }

    public void setType(TypeOfVehicle type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("id", id).
                append("licensePlate", licensePlate).
                append("type", type).
                toString();
    }
}
