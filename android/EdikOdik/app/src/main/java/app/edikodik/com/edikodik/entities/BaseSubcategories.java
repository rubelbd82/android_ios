package app.edikodik.com.edikodik.entities;

/**
 * Created by farhadrubel on 6/4/16.
 */

public class BaseSubcategories
{
    private String Hits;

    private String Description;

    private String SubCatId;

    private String SubCategoryName;

    private boolean isFeatured;

    public BaseSubcategories(String hits, String description, String subCatId, String subCategoryName, boolean isFeatured) {
        Hits = hits;
        Description = description;
        SubCatId = subCatId;
        SubCategoryName = subCategoryName;
        this.isFeatured = isFeatured;
    }

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

    public boolean isFeatured() {
        return isFeatured;
    }

    public void setFeatured(boolean featured) {
        isFeatured = featured;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Hits = "+Hits+", Description = "+Description+", SubCatId = "+SubCatId+", SubCategoryName = "+SubCategoryName+"]";
    }
}
