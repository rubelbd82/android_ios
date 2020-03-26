package app.edikodik.com.edikodik.entities;

/**
 * Created by farhadrubel on 6/4/16.
 */

public class CategoryResponse
{
    private String logo;

    private String sub_heading;

    private Subcategories_featured[] subcategories_featured;

    private String ads_right;

    private String ads_top;

    private Subcategories[] subcategories;

    private String ads_bottom;
    private String ads_mobile;

    private String cat_name;

    public String getLogo ()
    {
        return logo;
    }

    public void setLogo (String logo)
    {
        this.logo = logo;
    }

    public String getSub_heading ()
    {
        return sub_heading;
    }

    public void setSub_heading (String sub_heading)
    {
        this.sub_heading = sub_heading;
    }

    public Subcategories_featured[] getSubcategories_featured ()
    {
        return subcategories_featured;
    }

    public void setSubcategories_featured (Subcategories_featured[] subcategories_featured)
    {
        this.subcategories_featured = subcategories_featured;
    }

    public String getAds_right ()
    {
        return ads_right;
    }

    public void setAds_right (String ads_right)
    {
        this.ads_right = ads_right;
    }

    public String getAds_top ()
    {
        return ads_top;
    }

    public void setAds_top (String ads_top)
    {
        this.ads_top = ads_top;
    }

    public Subcategories[] getSubcategories ()
    {
        return subcategories;
    }

    public void setSubcategories (Subcategories[] subcategories)
    {
        this.subcategories = subcategories;
    }

    public String getAds_bottom ()
    {
        return ads_bottom;
    }

    public void setAds_bottom (String ads_bottom)
    {
        this.ads_bottom = ads_bottom;
    }

    public String getCat_name ()
    {
        return cat_name;
    }

    public void setCat_name (String cat_name)
    {
        this.cat_name = cat_name;
    }

    public String getAds_mobile() {
        return ads_mobile;
    }

    public void setAds_mobile(String ads_mobile) {
        this.ads_mobile = ads_mobile;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [logo = "+logo+", sub_heading = "+sub_heading+", subcategories_featured = "+subcategories_featured+", ads_right = "+ads_right+", ads_top = "+ads_top+", subcategories = "+subcategories+", ads_bottom = "+ads_bottom+", cat_name = "+cat_name+"]";
    }
}
