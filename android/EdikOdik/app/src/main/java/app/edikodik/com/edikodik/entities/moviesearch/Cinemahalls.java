package app.edikodik.com.edikodik.entities.moviesearch;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

/**
 * Created by farhadrubel on 6/5/16.
 */

public class Cinemahalls extends SugarRecord
{
//    @SerializedName("id")
    private String cinemahallId;

    private String label;

    public String getCinemahallId() {
        return cinemahallId;
    }

    public void setCinemahallId(String cinemahallId) {
        this.cinemahallId = cinemahallId;
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

