package com.fatel.mamtv1.Service;

import android.content.Context;

import com.fatel.mamtv1.Model.User;
import com.fatel.mamtv1.Service.Service.HttpConnector;

import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserManage {

    @Getter
    @Setter
    private static User currentUser = null;
    private static UserManage instance = null;

    private UserManage() {

    }

    public static UserManage getInstance(Context context) {
        if (instance == null) {
            instance = new UserManage();
            User user = User.checkLogin(context);
            if (user != null)
                currentUser = user;
        }
        return instance;
    }

    public void loginFBUser(String facebookID, String facebookFirstName, Context context) {
        HttpConnector.getInstance(context).loginFBUser(facebookID, facebookFirstName);
    }

    public void logoutUser(Context context) {
        currentUser.setLogin(0);
        currentUser.save(context);
        currentUser = null;
    }

    public boolean checkCurrentLogin(Context context) {
        User user = User.checkLogin(context);
        if (user != null)
            return true;

        return false;
    }

    public void updateUser(final Context context) {
        HttpConnector.getInstance(context).updateUserInfo(currentUser);
    }

    public void addScore(int score, Context context) {
        if (currentUser != null) {
            currentUser.addScore(score);
            currentUser.save(context);
            updateUser(context);
        }
    }

    public void setCurrentUserData(HashMap<String, Object> userData, Context context) {
        if(currentUser == null)
            currentUser = new User();
        currentUser.setData(userData, context);
    }
}