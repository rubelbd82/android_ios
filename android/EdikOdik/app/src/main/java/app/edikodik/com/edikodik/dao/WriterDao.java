package app.edikodik.com.edikodik.dao;

import app.edikodik.com.edikodik.entities.booksearch.Writers;

/**
 * Created by farhadrubel on 7/9/16.
 */

public class WriterDao extends GenericDao {
    @Override
    public void saveAll(Object[] objects) {
        if (objects.length > 0) {
            Writers.deleteAll(Writers.class);
            super.saveAll(objects);
        }
    }
}
