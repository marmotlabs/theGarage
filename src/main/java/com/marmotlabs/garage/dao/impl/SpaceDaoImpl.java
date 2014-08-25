package com.marmotlabs.garage.dao.impl;

import com.marmotlabs.garage.dao.SpaceDao;
import com.marmotlabs.garage.model.Space;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Sofia Craciun <craciun.sofia@gmail.com>
 */
@Repository
public class SpaceDaoImpl extends AbstractDaoImpl<Space, Long> implements SpaceDao {

    public SpaceDaoImpl() {
        super(Space.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getNumberOfFreeSpaces() {
        Query query = getCurrentSession().getNamedQuery(Space.GET_NUMBER_OF_FREE_SPACES);

        return ((Number) query.uniqueResult()).intValue();
    }

    @Override
    public Space getSpaceByLicensePlate(String licensePlate) {
        Query query = getCurrentSession().getNamedQuery(Space.GET_SPACE_BY_LICENSE_PLATE);

        query.setParameter("licensePlate", licensePlate);

        return (Space) query.uniqueResult();
    }

    @Override
    public Space getFirstEmptySpace() {
        Query query = getCurrentSession().getNamedQuery(Space.GET_FIRST_EMPTY_SPACE);
        query.setMaxResults(1);

        return (Space) query.uniqueResult();
    }

}
