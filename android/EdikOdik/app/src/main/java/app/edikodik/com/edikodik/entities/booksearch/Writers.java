package app.edikodik.com.edikodik.entities.booksearch;

import com.orm.SugarRecord;

/**
 * Created by farhadrubel on 6/12/16.
 */

public class Writers extends SugarRecord
{
    private String Writer;

    public String getWriter ()
    {
        return Writer;
    }

    public void setWriter (String Writer)
    {
        this.Writer = Writer;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Writer = "+Writer+"]";
    }
}
