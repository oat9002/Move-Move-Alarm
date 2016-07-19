package com.fatel.mamtv1.RESTService.Interface;

import com.fatel.mamtv1.Model.Group;
import com.fatel.mamtv1.Model.User;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by Moobi on 17-Jul-16.
 */
public interface GroupService {

    @POST("/group/findByUser")
    Call<Group> findByUser(@Header("Content-Type") String content, @Body User user);
}
