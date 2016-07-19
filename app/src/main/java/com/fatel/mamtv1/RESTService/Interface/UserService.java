package com.fatel.mamtv1.RESTService.Interface;

import com.fatel.mamtv1.Model.User;


import org.json.JSONArray;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.POST;

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
}
