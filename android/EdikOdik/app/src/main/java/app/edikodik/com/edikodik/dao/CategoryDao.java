package app.edikodik.com.edikodik.dao;

import com.orm.SugarRecord;

import java.util.List;

import app.edikodik.com.edikodik.entities.Categories;
import app.edikodik.com.edikodik.entities.Districts;

/**
 * Created by farhadrubel on 5/26/16.
 */

public class CategoryDao extends GenericDao {
    @Override
    public void saveAll(Object[] objects) {
        if (objects.length > 0) {
            try {
                Categories.deleteAll(Categories.class);
            } catch (Exception e) {
                //
            }
            super.saveAll(objects);
        }
    }

}
