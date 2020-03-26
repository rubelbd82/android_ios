package app.edikodik.com.edikodik.entities;

import com.orm.SugarRecord;

/**
 * Created by farhadrubel on 5/23/16.
 */
public class Categories extends SugarRecord
{
    private String position;

    private String Hits;

    private String IconImageLink;

    private String Description;

    private String Published;

    private String CatName;

    private String CatId;

    public String getPosition ()
    {
        return position;
    }

    public void setPosition (String position)
    {
        this.position = position;
    }

    public String getHits ()
    {
        return Hits;
    }

    public void setHits (String Hits)
    {
        this.Hits = Hits;
    }

    public String getIconImageLink ()
    {
        return IconImageLink;
    }

    public void setIconImageLink (String IconImageLink)
    {
        this.IconImageLink = IconImageLink;
    }

    public String getDescription ()
    {
        return Description;
    }

    public void setDescription (String Description)
    {
        this.Description = Description;
    }

    public String getPublished ()
    {
        return Published;
    }

    public void setPublished (String Published)
    {
        this.Published = Published;
    }

    public String getCatName ()
    {
        return CatName;
    }

    public void setCatName (String CatName)
    {
        this.CatName = CatName;
    }

    public String getCatId ()
    {
        return CatId;
    }

    public void setCatId (String CatId)
    {
        this.CatId = CatId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [position = "+position+", Hits = "+Hits+", IconImageLink = "+IconImageLink+", Description = "+Description+", Published = "+Published+", CatName = "+CatName+", CatId = "+CatId+"]";
    }
}