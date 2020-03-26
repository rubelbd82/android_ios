package app.edikodik.com.edikodik.entities;

/**
 * Created by farhadrubel on 6/4/16.
 */

public class Subcategories_featured
{
    private String Hits;

    private String Description;

    private String SubCatId;

    private String SubCategoryName;

    public String getHits ()
    {
        return Hits;
    }

    public void setHits (String Hits)
    {
        this.Hits = Hits;
    }

    public String getDescription ()
    {
        return Description;
    }

    public void setDescription (String Description)
    {
        this.Description = Description;
    }

    public String getSubCatId ()
    {
        return SubCatId;
    }

    public void setSubCatId (String SubCatId)
    {
        this.SubCatId = SubCatId;
    }

    public String getSubCategoryName ()
    {
        return SubCategoryName;
    }

    public void setSubCategoryName (String SubCategoryName)
    {
        this.SubCategoryName = SubCategoryName;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Hits = "+Hits+", Description = "+Description+", SubCatId = "+SubCatId+", SubCategoryName = "+SubCategoryName+"]";
    }
}
