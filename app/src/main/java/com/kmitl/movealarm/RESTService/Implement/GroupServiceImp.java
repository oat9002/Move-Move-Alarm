package com.kmitl.movealarm.RESTService.Implement;

import com.kmitl.movealarm.Model.Group;
import com.kmitl.movealarm.Model.User;
import com.kmitl.movealarm.RESTService.Interface.GroupService;

import java.util.HashMap;

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

    public void createGroup(Group group, Callback callback) {
        service.create("application/json", group).enqueue(callback);
    }

    public void updateGroup(Group group, Callback callback) {
        service.update("application/json", group).enqueue(callback);
    }

    public void joinGroup(int groupCode, User user, Callback callback) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("code", groupCode);
        service.join("applicatipn/json", map).enqueue(callback);
    }

    public void getTopRank(int max, Callback callback) {
        service.getTopRank(max).enqueue(callback);
    }

    public void getRank(Group group, Callback callback) {
        service.getRank("applicatipn/json", group).enqueue(callback);
    }

    public void getRankByUser(User user, Callback callback) {
        service.getRank("applicatipn/json", user).enqueue(callback);
    }

    public void leaveGroup(User user, Callback callback) {
        service.leaveGroup("application/json", user).enqueue(callback);
    }

    public void deleteGroup(User user, Callback callback) {
        service.deleteGroup("application/json", user).enqueue(callback);
    }
}
