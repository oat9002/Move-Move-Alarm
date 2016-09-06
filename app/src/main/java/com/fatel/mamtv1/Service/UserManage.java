package com.fatel.mamtv1.Service;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.fatel.mamtv1.Helper.UserHelper;
import com.fatel.mamtv1.MainActivity;
import com.fatel.mamtv1.Model.Event;
import com.fatel.mamtv1.Model.Group;
import com.fatel.mamtv1.Model.StatusDescription;
import com.fatel.mamtv1.Model.User;
import com.fatel.mamtv1.RESTService.Implement.EventServiceImp;
import com.fatel.mamtv1.RESTService.Implement.GroupServiceImp;
import com.fatel.mamtv1.RESTService.Implement.UserServiceImp;

import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


@Getter
@Setter
public class UserManage {

    @Getter
    @Setter
    private static User currentUser = null;
    private static UserManage instance = null;
    private UserHelper userHelper = new UserHelper();

    private UserManage() {

    }

    public static UserManage getInstance(Context context) {
        if (instance == null) {
            instance = new UserManage();
            User user = UserHelper.checkLoginUser();
            if (user != null)
                currentUser = user;
        }
        return instance;
    }

//    public void loginFBUser(String facebookId, String facebookFirstName, final Context context) {
//
//    }

    public void logoutUser() {
        currentUser.setLogin(0);
        currentUser.save();
        currentUser = null;
    }

    public boolean checkCurrentLogin() {
        User user = UserHelper.checkLoginUser();
        if (user != null)
            return true;

        return false;
    }

    public void updateUser() {
        UserServiceImp.getInstance().update(currentUser, new Callback<User>() {
            @Override
            public void onResponse(retrofit.Response<User> response, Retrofit retrofit) {
            }

            @Override
            public void onFailure(Throwable t) {
                Log.i("error", t.getMessage());
            }
        });
    }
}