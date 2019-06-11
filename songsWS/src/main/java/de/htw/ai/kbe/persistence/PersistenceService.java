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

    EntityManagerFactory emf = Persistence.createEntityManagerFactory(Constants.PERS_UNIT_NAME);

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


}
