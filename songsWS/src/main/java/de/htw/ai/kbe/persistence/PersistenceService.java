package de.htw.ai.kbe.persistence;

import de.htw.ai.kbe.resource.Constants;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.persistence.criteria.CriteriaBuilder;

@Singleton
public class PersistenceService {

    @PersistenceUnit(name= Constants.PERS_UNIT_NAME)
    EntityManagerFactory emf;

    private EntityManager em;

    public PersistenceService() {}

    public EntityManager getEntityManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(Constants.PERS_UNIT_NAME);
        return emf.createEntityManager();
    }


}
