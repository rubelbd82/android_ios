package app.edikodik.com.edikodik.dao;

import java.util.List;

import app.edikodik.com.edikodik.entities.custom.UserModel;

/**
 * Created by farhadrubel on 6/18/16.
 */

public class UserDao {

    public UserModel getUser() {
        List<UserModel> users = UserModel.listAll(UserModel.class);

        if (users.size() < 1) {
            return null;
        }
        UserModel user = users.get(0);
        return user;
    }

    public void saveUser(UserModel user) {
        this.removeUsers();
        user.save();
    }

    public void removeUsers() {
        UserModel.deleteAll(UserModel.class);
    }

}
