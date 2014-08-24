package com.marmotlabs.garage.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *
 * @author Sofia Craciun <sofia.craciun@gmail.com>
 */
@Entity
@Table(name = "SPACE")
@SequenceGenerator(name = "SPACE_ID_GEN", sequenceName = "SQ_SPACE", allocationSize = 1)
public class Space implements Serializable {

    @Id
    @Column(name = "SPACE_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SPACE_ID_GEN")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LEVEL_FK", nullable = false)
    private Level level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VEHICLE_FK", nullable = true, unique = true)
    private Vehicle vehicle;

    @Column(name = "POSITION", nullable = false)
    private int position;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("id", id).
                append("level", level).
                append("vehicle", vehicle).
                append("position", position).
                toString();
    }
}
