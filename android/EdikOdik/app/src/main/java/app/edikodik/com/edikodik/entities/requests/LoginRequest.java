package app.edikodik.com.edikodik.entities.requests;

/**
 * Created by farhadrubel on 6/15/16.
 */

public class LoginRequest {
    String username, password, udid;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }
}
