package app.edikodik.com.edikodik.dao;

import app.edikodik.com.edikodik.entities.moviesearch.Cinemahalls;

/**
 * Created by farhadrubel on 7/9/16.
 */

public class CinemahallDao extends GenericDao {
    @Override
    public void saveAll(Object[] objects) {
        if (objects.length > 0) {
            Cinemahalls.deleteAll(Cinemahalls.class);
            super.saveAll(objects);
        }
    }
}
