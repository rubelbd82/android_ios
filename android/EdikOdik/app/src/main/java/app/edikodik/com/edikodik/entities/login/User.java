package app.edikodik.com.edikodik.entities.login;

/**
 * Created by farhadrubel on 6/18/16.
 */

public class User
{
    private String phone;

    private String profile_id;

    private String last_ip;

    private String role_id;

    private String lastname;

    private String firstname;

    private String ban_reason;

    private String password;

    private String newpass_time;

    private String banned;

    private String modified;

    private String last_login;

    private String id;

    private String username;

    private String created;

    private String email;

    private String newpass;

    private String district;

    private String newpass_key;

    private String provider_name;

    public String getPhone ()
    {
        return phone;
    }

    public void setPhone (String phone)
    {
        this.phone = phone;
    }

    public String getProfile_id ()
    {
        return profile_id;
    }

    public void setProfile_id (String profile_id)
    {
        this.profile_id = profile_id;
    }

    public String getLast_ip ()
    {
        return last_ip;
    }

    public void setLast_ip (String last_ip)
    {
        this.last_ip = last_ip;
    }

    public String getRole_id ()
    {
        return role_id;
    }

    public void setRole_id (String role_id)
    {
        this.role_id = role_id;
    }

    public String getLastname ()
    {
        return lastname;
    }

    public void setLastname (String lastname)
    {
        this.lastname = lastname;
    }

    public String getFirstname ()
    {
        return firstname;
    }

    public void setFirstname (String firstname)
    {
        this.firstname = firstname;
    }

    public String getBan_reason ()
{
    return ban_reason;
}

    public void setBan_reason (String ban_reason)
    {
        this.ban_reason = ban_reason;
    }

    public String getPassword ()
    {
        return password;
    }

    public void setPassword (String password)
    {
        this.password = password;
    }

    public String getNewpass_time ()
{
    return newpass_time;
}

    public void setNewpass_time (String newpass_time)
    {
        this.newpass_time = newpass_time;
    }

    public String getBanned ()
    {
        return banned;
    }

    public void setBanned (String banned)
    {
        this.banned = banned;
    }

    public String getModified ()
    {
        return modified;
    }

    public void setModified (String modified)
    {
        this.modified = modified;
    }

    public String getLast_login ()
    {
        return last_login;
    }

    public void setLast_login (String last_login)
    {
        this.last_login = last_login;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getUsername ()
    {
        return username;
    }

    public void setUsername (String username)
    {
        this.username = username;
    }

    public String getCreated ()
    {
        return created;
    }

    public void setCreated (String created)
    {
        this.created = created;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getNewpass ()
{
    return newpass;
}

    public void setNewpass (String newpass)
    {
        this.newpass = newpass;
    }

    public String getDistrict ()
    {
        return district;
    }

    public void setDistrict (String district)
    {
        this.district = district;
    }

    public String getNewpass_key ()
{
    return newpass_key;
}

    public void setNewpass_key (String newpass_key)
    {
        this.newpass_key = newpass_key;
    }

    public String getProvider_name ()
    {
        return provider_name;
    }

    public void setProvider_name (String provider_name)
    {
        this.provider_name = provider_name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [phone = "+phone+", profile_id = "+profile_id+", last_ip = "+last_ip+", role_id = "+role_id+", lastname = "+lastname+", firstname = "+firstname+", ban_reason = "+ban_reason+", password = "+password+", newpass_time = "+newpass_time+", banned = "+banned+", modified = "+modified+", last_login = "+last_login+", id = "+id+", username = "+username+", created = "+created+", email = "+email+", newpass = "+newpass+", district = "+district+", newpass_key = "+newpass_key+", provider_name = "+provider_name+"]";
    }
}