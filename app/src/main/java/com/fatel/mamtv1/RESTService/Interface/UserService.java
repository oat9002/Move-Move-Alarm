package com.fatel.mamtv1.RESTService.Interface;

import com.fatel.mamtv1.Model.User;


import org.json.JSONArray;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by Moobi on 16-Jul-16.
 */
public interface UserService {
    @FormUrlEncoded
    @POST("/login")
    Call<User> login(@Field("facebookId") String facebookID, @Field("facebookFirstName") String facebookFirstName);

    @POST("/user/update")
    Call<User> update(@Header("Content-Type") String content, @Body User user);

    @POST("/user/getGroup")
    Call<JSONArray> getGroupMember(@Header("Content-Type") String content, @Body User user);

    @GET("/user/topRank")
    Call<List<User>> getTopRank(@Query("max") int max);

    @POST("/user/rank")
    Call<Integer> getRank(@Header("Content-Type") String content, @Body User group);
}
