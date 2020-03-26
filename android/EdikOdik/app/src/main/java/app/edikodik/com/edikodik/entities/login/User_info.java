package app.edikodik.com.edikodik.entities.login;

/**
 * Created by farhadrubel on 6/18/16.
 */

public class User_info
{
    private String zip;

    private String phone;

    private String website;

    private String linkedin;

    private String photo;

    private String googleplus;

    private String country;

    private String id;

    private String address;

    private String facebook;

    private String name;

    private String company;

    private String user_id;

    private String mobile_no;

    private String profile;

    public String getZip ()
    {
        return zip;
    }

    public void setZip (String zip)
    {
        this.zip = zip;
    }

    public String getPhone ()
    {
        return phone;
    }

    public void setPhone (String phone)
    {
        this.phone = phone;
    }

    public String getWebsite ()
    {
        return website;
    }

    public void setWebsite (String website)
    {
        this.website = website;
    }

    public String getLinkedin ()
    {
        return linkedin;
    }

    public void setLinkedin (String linkedin)
    {
        this.linkedin = linkedin;
    }

    public String getPhoto ()
    {
        return photo;
    }

    public void setPhoto (String photo)
    {
        this.photo = photo;
    }

    public String getGoogleplus ()
    {
        return googleplus;
    }

    public void setGoogleplus (String googleplus)
    {
        this.googleplus = googleplus;
    }

    public String getCountry ()
{
    return country;
}

    public void setCountry (String country)
    {
        this.country = country;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getAddress ()
    {
        return address;
    }

    public void setAddress (String address)
    {
        this.address = address;
    }

    public String getFacebook ()
    {
        return facebook;
    }

    public void setFacebook (String facebook)
    {
        this.facebook = facebook;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getCompany ()
    {
        return company;
    }

    public void setCompany (String company)
    {
        this.company = company;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    public String getMobile_no ()
    {
        return mobile_no;
    }

    public void setMobile_no (String mobile_no)
    {
        this.mobile_no = mobile_no;
    }

    public String getProfile ()
    {
        return profile;
    }

    public void setProfile (String profile)
    {
        this.profile = profile;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [zip = "+zip+", phone = "+phone+", website = "+website+", linkedin = "+linkedin+", photo = "+photo+", googleplus = "+googleplus+", country = "+country+", id = "+id+", address = "+address+", facebook = "+facebook+", name = "+name+", company = "+company+", user_id = "+user_id+", mobile_no = "+mobile_no+", profile = "+profile+"]";
    }
}