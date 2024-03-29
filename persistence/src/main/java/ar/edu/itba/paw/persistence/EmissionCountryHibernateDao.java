package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Country;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;

@Repository
public class EmissionCountryHibernateDao implements EmissionCountryDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Collection<Country> getAllCountries() {
        final TypedQuery<Country> query = entityManager.createQuery("from Country as c ORDER BY c.name ASC", Country.class);
        return query.getResultList();
    }
}
