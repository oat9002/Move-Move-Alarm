package com.fatel.mamtv1.RESTService.Implement;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.fatel.mamtv1.Fragment.LoadingFragment;
import com.fatel.mamtv1.Fragment.MainFragment;
import com.fatel.mamtv1.Model.Group;
import com.fatel.mamtv1.Model.User;
import com.fatel.mamtv1.R;
import com.fatel.mamtv1.RESTService.Interface.GroupService;
import com.fatel.mamtv1.Service.Cache;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Moobi on 17-Jul-16.
 */
public class GroupServiceImp {

    private static Retrofit retrofit;
    private static GroupService service;
    private static GroupServiceImp groupService;
    public static final String BASE_URL = "http://enceladusiapetus.me:8001";

    private GroupServiceImp() {};

    public static GroupServiceImp getInstance() {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
            service = retrofit.create(GroupService.class);
            groupService = new GroupServiceImp();
        }

        return groupService;
    }

    public void findByUser(User user, Callback callback) {
        Call call = service.findByUser("applicatipn/json", user);
        call.enqueue(callback);
    }
}
