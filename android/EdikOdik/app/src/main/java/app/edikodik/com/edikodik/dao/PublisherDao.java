package app.edikodik.com.edikodik.dao;

import app.edikodik.com.edikodik.entities.booksearch.Publishers;

/**
 * Created by farhadrubel on 7/9/16.
 */

public class PublisherDao extends GenericDao {
    @Override
    public void saveAll(Object[] objects) {
        if (objects.length > 0) {
            Publishers.deleteAll(Publishers.class);
            super.saveAll(objects);
        }
    }
}
