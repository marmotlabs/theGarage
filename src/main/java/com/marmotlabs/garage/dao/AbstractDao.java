package com.marmotlabs.garage.dao;

import java.io.Serializable;

/**
 * Provides common database operations
 *
 * @author Sofia Craciun <craciun.sofia@gmail.com>
 * @param <E> the entity type managed by the concrete implementation of this
 * class
 * @param <I> the @Id type managed by the concrete implementation of this class
 */
public interface AbstractDao<E, I extends Serializable> {

    /**
     * Gets the entity E by the provided id I, or null if there is no such persistent instance.
     * 
     * @param id the identifier
     * @return the entity E, or null if none found
     */
    E findById(I id);

    /**
     * Persist the given entity E.
     * 
     * @param e the entity E to be persisted
     */
    void saveOrUpdate(E e);

    /**
     * Remove the entity E from the datastore.
     * 
     * @param e the entity E to be removed
     */
    void delete(E e);
}
