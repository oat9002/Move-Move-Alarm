package com.fatel.mamtv1.RESTService.Implement;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.fatel.mamtv1.MainActivity;
import com.fatel.mamtv1.Model.User;
import com.fatel.mamtv1.Service.UserManage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Moobi on 17-Jul-16.
 */
public class UserServiceImp {
    private static Retrofit retrofit;
    private static com.fatel.mamtv1.RESTService.Interface.UserService service;
    private static UserServiceImp userService;
    public static final String BASE_URL = "http://enceladusiapetus.me:8001";

    private UserServiceImp(){};

    public static UserServiceImp getInstance() {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
            service = retrofit.create(com.fatel.mamtv1.RESTService.Interface.UserService.class);
            userService = new UserServiceImp();
        }

        return userService;
    }

    public void login(String facebookId, String facebookFirstName, Callback callback) {
        Call call = service.login(facebookId, facebookFirstName);
        call.enqueue(callback);
    }

    public void update(final User user, Callback callback) {
        Call call = service.update("application/json", user);
        call.enqueue(callback);
    }

    public void getTopRank(int max, Callback callback) {
        service.getTopRank(max).enqueue(callback);
    }

    public void getRank(User user, Callback callback) {
        service.getRank("applicatipn/json", user).enqueue(callback);
    }
}
