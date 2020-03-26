package app.edikodik.com.edikodik.dao;

import app.edikodik.com.edikodik.entities.Languages;

/**
 * Created by farhadrubel on 7/9/16.
 */

public class LanguageDao extends GenericDao {
    @Override
    public void saveAll(Object[] objects) {
        if (objects.length > 0) {
            Languages.deleteAll(Languages.class);
            super.saveAll(objects);
        }
    }
}
