package com.marmotlabs.garage.dao.impl;

import com.marmotlabs.garage.dao.AbstractDao;
import java.io.Serializable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Sofia Craciun <craciun.sofia@gmail.com>
 * @param <E>
 * @param <I>
 */
public abstract class AbstractDaoImpl<E, I extends Serializable> implements AbstractDao<E, I> {

    private final Class<E> entityClass;

    protected AbstractDaoImpl(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    @Autowired
    private SessionFactory sessionFactory;

    // Database > JNDI resource > DataSource > SessionFactory > Session > TrasactionManager > Transaction > Query > Result > nrOfFreeSpaces
    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public E findById(I id) {
        return (E) getCurrentSession().get(entityClass, id);
    }

    @Override
    public void saveOrUpdate(E e) {
        getCurrentSession().saveOrUpdate(e);
    }

    @Override
    public void delete(E e) {
        getCurrentSession().delete(e);
    }
}
