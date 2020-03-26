package app.edikodik.com.edikodik.entities;

import com.orm.SugarRecord;

/**
 * Created by farhadrubel on 5/23/16.
 */
public class Districts extends SugarRecord
{
    public Districts() {

    }

    public Districts(String divId, String districtName, String districtId) {
        DivId = divId;
        DistrictName = districtName;
        DistrictId = districtId;
    }

    private String DivId;

    private String DistrictName;

    private String DistrictId;

    public String getDivId ()
    {
        return DivId;
    }

    public void setDivId (String DivId)
    {
        this.DivId = DivId;
    }

    public String getDistrictName ()
    {
        return DistrictName;
    }

    public void setDistrictName (String DistrictName)
    {
        this.DistrictName = DistrictName;
    }

    public String getDistrictId ()
    {
        return DistrictId;
    }

    public void setDistrictId (String DistrictId)
    {
        this.DistrictId = DistrictId;
    }

    @Override
    public String toString()
    {
        return DistrictName;
    }
}