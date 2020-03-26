package app.edikodik.com.edikodik.entities.genericsearch;

import app.edikodik.com.edikodik.entities.Districts;
import app.edikodik.com.edikodik.entities.Divisions;
import app.edikodik.com.edikodik.entities.Priceranges;

/**
 * Created by farhadrubel on 6/5/16.
 */

public class GenericSearchResponse
{
    private String total;

    private String logo;

    private Priceranges[] priceranges;

    private String sub_heading;

    private Districts[] districts;

    private String ads_top;

    private String ads_mobile;

    private Divisions[] divisions;

    private Listings[] listings;

    private String user;

    public String getTotal ()
    {
        return total;
    }

    public void setTotal (String total)
    {
        this.total = total;
    }

    public String getLogo ()
    {
        return logo;
    }

    public void setLogo (String logo)
    {
        this.logo = logo;
    }

    public Priceranges[] getPriceranges ()
    {
        return priceranges;
    }

    public void setPriceranges (Priceranges[] priceranges)
    {
        this.priceranges = priceranges;
    }

    public String getSub_heading ()
    {
        return sub_heading;
    }

    public void setSub_heading (String sub_heading)
    {
        this.sub_heading = sub_heading;
    }

    public Districts[] getDistricts ()
    {
        return districts;
    }

    public void setDistricts (Districts[] districts)
    {
        this.districts = districts;
    }

    public String getAds_top ()
    {
        return ads_top;
    }

    public void setAds_top (String ads_top)
    {
        this.ads_top = ads_top;
    }

    public Divisions[] getDivisions ()
    {
        return divisions;
    }

    public void setDivisions (Divisions[] divisions)
    {
        this.divisions = divisions;
    }

    public Listings[] getListings ()
    {
        return listings;
    }

    public void setListings (Listings[] listings)
    {
        this.listings = listings;
    }

    public String getUser ()
    {
    return user;
    }

    public void setUser (String user)
    {
        this.user = user;
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
        return "ClassPojo [total = "+total+", logo = "+logo+", priceranges = "+priceranges+", sub_heading = "+sub_heading+", districts = "+districts+", ads_top = "+ads_top+", divisions = "+divisions+", listings = "+listings+", user = "+user+"]";
    }
}
