package app.edikodik.com.edikodik.entities;

/**
 * Created by farhadrubel on 6/5/16.
 */

public class Priceranges
{
    private String Name;

    private String Maximum;

    private String Minimum;

    private String Id;

    public String getName ()
    {
        return Name;
    }

    public void setName (String Name)
    {
        this.Name = Name;
    }

    public String getMaximum ()
    {
        return Maximum;
    }

    public void setMaximum (String Maximum)
    {
        this.Maximum = Maximum;
    }

    public String getMinimum ()
    {
        return Minimum;
    }

    public void setMinimum (String Minimum)
    {
        this.Minimum = Minimum;
    }

    public String getId ()
    {
        return Id;
    }

    public void setId (String Id)
    {
        this.Id = Id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Name = "+Name+", Maximum = "+Maximum+", Minimum = "+Minimum+", Id = "+Id+"]";
    }
}
