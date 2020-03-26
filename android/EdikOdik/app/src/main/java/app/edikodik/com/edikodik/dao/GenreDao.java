package app.edikodik.com.edikodik.dao;

import app.edikodik.com.edikodik.entities.Genres;

/**
 * Created by farhadrubel on 7/9/16.
 */

public class GenreDao extends GenericDao {
    @Override
    public void saveAll(Object[] objects) {
        if (objects.length > 0) {
            Genres.deleteAll(Genres.class);
            super.saveAll(objects);
        }
    }
}
