package app.edikodik.com.edikodik.dao;

import app.edikodik.com.edikodik.entities.Districts;
import java.lang.reflect.*;
/**
 * Created by farhadrubel on 5/26/16.
 */

public class DistrictDao extends GenericDao {
    @Override
    public void saveAll(Object[] objects) {
        if (objects.length > 0) {
            Districts.deleteAll(Districts.class);
            super.saveAll(objects);
        }
    }

}
