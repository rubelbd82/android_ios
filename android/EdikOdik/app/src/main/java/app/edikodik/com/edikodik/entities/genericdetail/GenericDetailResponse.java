package app.edikodik.com.edikodik.entities.genericdetail;

import app.edikodik.com.edikodik.entities.Districts;
import app.edikodik.com.edikodik.entities.Divisions;
import app.edikodik.com.edikodik.entities.Priceranges;

/**
 * Created by farhadrubel on 6/5/16.
 */

public class GenericDetailResponse {

    private String singlePriceRange;

    private String logo;

    private String detail_right;

    private String phone;

    private String[] payment;

    private Priceranges[] priceranges;

    private String sub_heading;

    private String hours;

    private String detail_left;

    private Divisions[] divisions;

    private String distance;

    private String address;

    private Listing listing;

    private Districts[] districts;

    private String ads_top;

    private String ads_mobile;

    private String user;

    public String getSinglePriceRange ()
    {
        return singlePriceRange;
    }

    public void setSinglePriceRange (String singlePriceRange)
    {
        this.singlePriceRange = singlePriceRange;
    }

    public String getLogo ()
    {
        return logo;
    }

    public void setLogo (String logo)
    {
        this.logo = logo;
    }

    public String getDetail_right ()
    {
        return detail_right;
    }

    public void setDetail_right (String detail_right)
    {
        this.detail_right = detail_right;
    }

    public String getPhone ()
    {
        return phone;
    }

    public void setPhone (String phone)
    {
        this.phone = phone;
    }

    public String[] getPayment ()
    {
        return payment;
    }

    public void setPayment (String[] payment)
    {
        this.payment = payment;
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

    public String getHours ()
    {
        return hours;
    }

    public void setHours (String hours)
    {
        this.hours = hours;
    }

    public String getDetail_left ()
    {
        return detail_left;
    }

    public void setDetail_left (String detail_left)
    {
        this.detail_left = detail_left;
    }

    public Divisions[] getDivisions ()
    {
        return divisions;
    }

    public void setDivisions (Divisions[] divisions)
    {
        this.divisions = divisions;
    }

    public String getDistance ()
    {
        return distance;
    }

    public void setDistance (String distance)
    {
        this.distance = distance;
    }

    public String getAddress ()
    {
        return address;
    }

    public void setAddress (String address)
    {
        this.address = address;
    }

    public Listing getListing ()
    {
        return listing;
    }

    public void setListing (Listing listing)
    {
        this.listing = listing;
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
        return "ClassPojo [singlePriceRange = "+singlePriceRange+", logo = "+logo+", detail_right = "+detail_right+", phone = "+phone+", payment = "+payment+", priceranges = "+priceranges+", sub_heading = "+sub_heading+", hours = "+hours+", detail_left = "+detail_left+", divisions = "+divisions+", distance = "+distance+", address = "+address+", listing = "+listing+", districts = "+districts+", ads_top = "+ads_top+", user = "+user+"]";
    }
}
