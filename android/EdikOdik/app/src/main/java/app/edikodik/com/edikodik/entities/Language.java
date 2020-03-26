package app.edikodik.com.edikodik.entities;

/**
 * Created by farhadrubel on 6/12/16.
 */

public class Language
{
    private String Name;

    private String Id;

    private String ShortCode;

    public String getName ()
    {
        return Name;
    }

    public void setName (String Name)
    {
        this.Name = Name;
    }

    public String getId ()
    {
        return Id;
    }

    public void setId (String Id)
    {
        this.Id = Id;
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
        return "ClassPojo [Name = "+Name+", Id = "+Id+", ShortCode = "+ShortCode+"]";
    }
}