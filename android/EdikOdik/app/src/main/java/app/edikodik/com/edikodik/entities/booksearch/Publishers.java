package app.edikodik.com.edikodik.entities.booksearch;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

/**
 * Created by farhadrubel on 6/12/16.
 */

public class Publishers extends SugarRecord
{
//    @SerializedName("id")
    private String publisherId;

    private String label;


    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public String getLabel ()
    {
        return label;
    }

    public void setLabel (String label)
    {
        this.label = label;
    }

    @Override
    public String toString()
    {
        return label;
    }
}