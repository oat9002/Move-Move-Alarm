package com.kmitl.movealarm.Service;

import android.content.Context;
import android.util.Log;

import com.kmitl.movealarm.Model.User;
import com.kmitl.movealarm.Model.UserProgress;
import com.kmitl.movealarm.RESTService.Implement.UserServiceImp;

import lombok.Getter;
import lombok.Setter;
import retrofit.Callback;
import retrofit.Retrofit;


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
            if (user != null){
                currentUser = user;
                if(UserProgress.getProgressByUserId(context,currentUser.getId(),1)!=null)
                    currentUser.setDailyProgress(UserProgress.getProgressByUserId(context,currentUser.getId(),1));
                if(UserProgress.getProgressByUserId(context,currentUser.getId(),0)!=null)
                    currentUser.setWeeklyProgress(UserProgress.getProgressByUserId(context,currentUser.getId(),0));

            }

        }
        return instance;
    }

    public void loginFBUser(String facebookId, String facebookFirstName, final Context context) {

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