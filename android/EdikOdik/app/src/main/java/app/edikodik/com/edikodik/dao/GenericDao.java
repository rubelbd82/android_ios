package app.edikodik.com.edikodik.dao;

import android.util.Log;

import com.orm.SugarRecord;

import java.lang.reflect.Type;
import java.util.List;
import java.lang.Class;

import app.edikodik.com.edikodik.entities.Categories;

/**
 * Created by farhadrubel on 5/26/16.
 */

public class GenericDao<T> {
    public Long save(Object object) {
        SugarRecord record = (SugarRecord) object;
        return record.save();
    }

    public void saveAll(Object[] objects) {
        for (Object object : objects) {
            SugarRecord record = (SugarRecord) object;
            record.save();

        }
    }

    public void sync(T[] objects) {
        if (objects.length > 0) {
            try {
                if (objects[0].getClass() == SugarRecord.class) {
                    SugarRecord.deleteAll(objects[0].getClass());
                }
                saveAll(objects);
                Log.d("wid", "saved all");


            } catch (Exception e) {
                //
                Log.d("wid", "Couldn't sync, exception is: " + e);
            }
        }
    }
}
