package com.fatel.mamtv1.Service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.fatel.mamtv1.MainActivity;
import com.fatel.mamtv1.Model.Event;
import com.fatel.mamtv1.Model.Group;
import com.fatel.mamtv1.Model.User;
import com.fatel.mamtv1.RESTService.Implement.EventServiceImp;
import com.fatel.mamtv1.RESTService.Implement.GroupServiceImp;
import com.fatel.mamtv1.RESTService.Implement.UserServiceImp;

import java.util.HashMap;

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
            if (user != null)
                currentUser = user;
        }
        return instance;
    }

    public void loginFBUser(String facebookId, String facebookFirstName, final Context context) {
        UserServiceImp.getInstance().login(facebookId, facebookFirstName, new Callback<User>() {
            @Override
            public void onResponse(retrofit.Response<User> response, Retrofit retrofit) {
                Log.i("response", "" + response.body().getFacebookFirstName());
                response.body().setLogin(1);
                response.body().save(context);
                UserManage.getInstance(context).setCurrentUser(response.body());
                Intent intent = new Intent(context, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
                GroupServiceImp.getInstance().findByUser(currentUser, new Callback<Group>() {
                    @Override
                    public void onResponse(retrofit.Response<Group> response, Retrofit retrofit) {
                        Cache.getInstance().putData("groupData", response.body());
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.i("error", t.getMessage());
                    }
                });

                EventServiceImp.getInstance().getEvent(new Callback<Event>() {
                    @Override
                    public void onResponse(retrofit.Response<Event> response, Retrofit retrofit) {
                        Cache.getInstance().putData("eventData", response.body());
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.i("error", t.getMessage());
                    }
                });
            }

            @Override
            public void onFailure(Throwable t) {
                Log.i("error", t.getMessage());
            }
        });
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

    public void addScore(int score, Context context) {
        if (currentUser != null) {
            currentUser.addScore(score);
            currentUser.save(context);
            updateUser();
        }
    }
}