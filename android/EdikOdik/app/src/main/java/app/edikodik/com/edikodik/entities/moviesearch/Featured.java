package app.edikodik.com.edikodik.entities.moviesearch;

import app.edikodik.com.edikodik.entities.moviesearch.Data;

/**
 * Created by farhadrubel on 6/5/16.
 */

public class Featured
{
    private String total;

    private Data[] data;

    public String getTotal ()
    {
        return total;
    }

    public void setTotal (String total)
    {
        this.total = total;
    }

    public Data[] getData ()
    {
        return data;
    }

    public void setData (Data[] data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [total = "+total+", data = "+data+"]";
    }
}
