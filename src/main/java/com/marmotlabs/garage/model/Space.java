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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
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
@NamedQueries({
    @NamedQuery(name = Space.GET_NUMBER_OF_FREE_SPACES, query = "select count(s) from Space s where s.vehicle is null"),
    @NamedQuery(name = Space.GET_SPACE_BY_LICENSE_PLATE, query = "select s from Space s, Vehicle v where s.vehicle = v.id and v.licensePlate = :licensePlate"),
    @NamedQuery(name = Space.GET_FIRST_EMPTY_SPACE, query = "select s from Space s where s.vehicle is null order by s.level asc, s.position asc")
})
public class Space implements Serializable {

    public static final String GET_NUMBER_OF_FREE_SPACES = "Space.getNumberOfFreeSpaces";
    public static final String GET_SPACE_BY_LICENSE_PLATE = "Space.getSpaceByLicensePlate";
    public static final String GET_FIRST_EMPTY_SPACE = "Space.getFirstEmptySpace";

    @Id
    @Column(name = "SPACE_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SPACE_ID_GEN")
    private Long id;

    @Column(name = "POSITION", nullable = false)
    private Integer position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LEVEL_FK", nullable = false)
    private Level level;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VEHICLE_FK", nullable = true, unique = true)
    private Vehicle vehicle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("id", id).
                append("position", position).
                append("level", level).
                append("vehicle", vehicle).
                toString();
    }
}
