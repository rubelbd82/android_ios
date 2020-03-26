package app.edikodik.com.edikodik.entities.moviesearch;

import app.edikodik.com.edikodik.entities.Genres;
import app.edikodik.com.edikodik.entities.Languages;

/**
 * Created by farhadrubel on 6/5/16.
 */

public class MovieSearchResponse
{
    private String total;

    private Languages[] languages;

    private String logo;

    private Cinemahalls[] cinemahalls;

    private Genres[] genres;

    private Data[] data;

    private String ads_top;

    private String ads_mobile;

    private Params params;

    private String pagination;

    private String user;

    private Featured Featured;

    public String getTotal ()
    {
        return total;
    }

    public void setTotal (String total)
    {
        this.total = total;
    }

    public Languages[] getLanguages ()
    {
        return languages;
    }

    public void setLanguages (Languages[] languages)
    {
        this.languages = languages;
    }

    public String getLogo ()
    {
        return logo;
    }

    public void setLogo (String logo)
    {
        this.logo = logo;
    }

    public Cinemahalls[] getCinemahalls ()
    {
        return cinemahalls;
    }

    public void setCinemahalls (Cinemahalls[] cinemahalls)
    {
        this.cinemahalls = cinemahalls;
    }

    public Genres[] getGenres ()
    {
        return genres;
    }

    public void setGenres (Genres[] genres)
    {
        this.genres = genres;
    }

    public Data[] getData ()
    {
        return data;
    }

    public void setData (Data[] data)
    {
        this.data = data;
    }

    public String getAds_top ()
    {
        return ads_top;
    }

    public void setAds_top (String ads_top)
    {
        this.ads_top = ads_top;
    }

    public Params getParams ()
    {
        return params;
    }

    public void setParams (Params params)
    {
        this.params = params;
    }

    public String getPagination ()
    {
        return pagination;
    }

    public void setPagination (String pagination)
    {
        this.pagination = pagination;
    }

    public String getUser ()
{
    return user;
}

    public void setUser (String user)
    {
        this.user = user;
    }

    public Featured getFeatured ()
    {
        return Featured;
    }

    public void setFeatured (Featured Featured)
    {
        this.Featured = Featured;
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
        return "ClassPojo [total = "+total+", languages = "+languages+", logo = "+logo+", cinemahalls = "+cinemahalls+", genres = "+genres+", data = "+data+", ads_top = "+ads_top+", params = "+params+", pagination = "+pagination+", user = "+user+", Featured = "+Featured+"]";
    }
}
