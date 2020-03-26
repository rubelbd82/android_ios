package app.edikodik.com.edikodik.entities.autocomplete;

/**
 * Created by farhadrubel on 7/6/16.
 */

public class AutoCompleteResponse
{
    private String value;

    public String getValue ()
    {
        return value;
    }

    public void setValue (String value)
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return value;
    }
}
