package com.marmotlabs.garage.dao.impl;

import com.marmotlabs.garage.dao.AbstractDao;
import java.io.Serializable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Sofia Craciun
 *
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

    /**
     * <p>
     * Follows the chain:</p>
     *
     * <pre>
     * Database (PostgreSQL) > JNDI resource (ROOT.xml) > DataSource (data-access.xml) > SessionFactory (data-access.xml) > Session (Hibernate) > TrasactionManager (Hibernate) > Transaction (@Service) > Query (DAO) > Result (DAO) > business result
     * </pre>
     *
     * <p>
     * to obtain a Session on which all the queries will be ran.</p>
     *
     * <p>
     * It needs a transactional context, such as the @Service.</p>
     *
     * @return a session to the database
     */
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
