package app.edikodik.com.edikodik.entities;

/**
 * Created by farhadrubel on 6/5/16.
 */

public class Divisions
{
    private String DivName;

    private String DivId;

    public String getDivName ()
    {
        return DivName;
    }

    public void setDivName (String DivName)
    {
        this.DivName = DivName;
    }

    public String getDivId ()
    {
        return DivId;
    }

    public void setDivId (String DivId)
    {
        this.DivId = DivId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [DivName = "+DivName+", DivId = "+DivId+"]";
    }
}
