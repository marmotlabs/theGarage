package com.marmotlabs.garage.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * <p>Entity class representing a level in the garage.</p>
 *
 * <p>Offers full flexibility about the number of levels in the garage.</p>
 *
 * <p>At least one level is required in order to have any {@link Space} and thus
 * any {@link Vehicle} in the database.</p>
 *
 * @author Sofia Craciun
 */
@Entity
@Table(name = "LEVEL")
@SequenceGenerator(name = "LEVEL_ID_GEN", sequenceName = "SQ_LEVEL", allocationSize = 1)
public class Level implements Serializable {

    /**
     * Technical ID.
     */
    @Id
    @Column(name = "LEVEL_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LEVEL_ID_GEN")
    private Long id;

    /**
     * Business ID (uniquely identifies the entity).
     */
    @Column(name = "STORY", nullable = false, unique = true)
    private String story;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("id", id).
                append("story", story).
                toString();
    }

}
