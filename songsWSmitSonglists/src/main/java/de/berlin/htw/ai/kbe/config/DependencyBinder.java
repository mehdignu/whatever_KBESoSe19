package de.berlin.htw.ai.kbe.config;

import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import de.berlin.htw.ai.kbe.interfaces.PlaylistDAO;
import de.berlin.htw.ai.kbe.interfaces.UsersDAO;
import de.berlin.htw.ai.kbe.DBPlaylistDAO;
import de.berlin.htw.ai.kbe.DBSongsDAO;
import de.berlin.htw.ai.kbe.DBUserDAO;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import de.berlin.htw.ai.kbe.interfaces.SongsDAO;

public class DependencyBinder extends AbstractBinder {

    @Override
    protected void configure() {

        bind(Persistence.createEntityManagerFactory("songDB-PU")).to(EntityManagerFactory.class);

        bind(DBSongsDAO.class).to(SongsDAO.class).in(Singleton.class);

        bind(DBUserDAO.class).to(UsersDAO.class).in(Singleton.class);

        bind(DBPlaylistDAO.class).to(PlaylistDAO.class);

    }
}