package app.edikodik.com.edikodik.entities;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

/**
 * Created by farhadrubel on 6/5/16.
 */

public class Languages extends SugarRecord
{
    private String Name;

//    @SerializedName("id")
    private String languageId;

    private String ShortCode;

    public String getName ()
    {
        return Name;
    }

    public void setName (String Name)
    {
        this.Name = Name;
    }

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    public String getShortCode ()
    {
        return ShortCode;
    }

    public void setShortCode (String ShortCode)
    {
        this.ShortCode = ShortCode;
    }

    @Override
    public String toString()
    {
        return Name;
    }
}

