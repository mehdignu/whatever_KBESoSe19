package de.htw.ai.kbe.storage;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemorySongCollection {

    private static InMemorySongCollection instance = null;

    private Map<Integer, Song> storage;

    private InMemorySongCollection() {
        storage = new ConcurrentHashMap<Integer, Song>();
        initSomeSongs();
    }

    public static InMemorySongCollection getInstance() {
        if (instance == null) {
            instance = new InMemorySongCollection();
        }
        return instance;
    }

    private void initSomeSongs() {

        Song song1 = new Song.Builder(1, "looking for the summer")
                .artist("Chris Rea")
                .album("Summer")
                .released(1992)
                .build();

        storage.put(song1.getId(), song1);

        Song song2 = new Song.Builder(2, "Bad")
                .artist("Michael Jackson")
                .album("Bad")
                .released(1992)
                .build();

        storage.put(song2.getId(), song2);

        Song song3 = new Song.Builder(3, "Vogue")
                .artist("Madonna")
                .album("Vogue")
                .released(1990)
                .build();

        storage.put(song3.getId(), song3);

        Song song4 = new Song.Builder(4, "Wicked love")
                .artist("Chris Issac")
                .album("Wicked")
                .released(1991)
                .build();

        storage.put(song4.getId(), song4);
    }

    public Song getSong(Integer id) {
        return storage.get(id);
    }

    public Collection<Song> getAllSongs() {
        return storage.values();
    }


    public Integer addSong(Song song) {
        song.setId((int) storage.keySet().stream().count() + 1);
        storage.put(song.getId(), song);
        return song.getId();
    }

    // updates a contact in the db
    public boolean updateSong(Song song) {
        return false;
    }

    // returns deleted contact
    public Song deleteSong(Integer id) {
        Song song = getSong(id);
        storage.remove(id);
        return song;
    }
}