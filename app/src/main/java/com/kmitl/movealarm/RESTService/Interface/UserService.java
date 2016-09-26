package com.kmitl.movealarm.RESTService.Interface;

import com.kmitl.movealarm.Model.StatusDescription;
import com.kmitl.movealarm.Model.User;


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
    Call<User> login(@Field("facebook_id") String facebookID, @Field("facebook_firstName") String facebookFirstName);

    @POST("/user/update")
    Call<StatusDescription> update(@Header("Content-Type") String content, @Body User user);

    @GET("/user/topRank")
    Call<List<User>> getTopRank(@Query("max") int max);

    @POST("/user/rank")
    Call<Integer> getRank(@Header("Content-Type") String content, @Body User group);
}
