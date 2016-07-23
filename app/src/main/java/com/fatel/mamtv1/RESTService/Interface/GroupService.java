package com.fatel.mamtv1.RESTService.Interface;

import com.fatel.mamtv1.Model.Group;
import com.fatel.mamtv1.Model.StatusDescription;
import com.fatel.mamtv1.Model.User;
import com.fatel.mamtv1.Model.UserProgress;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;

public interface GroupService {

    @POST("/group/findByUser")
    Call<Group> findByUser(@Header("Content-Type") String content, @Body User user);

    @POST("/group/create")
    Call<StatusDescription> create(@Header("Content-Type") String content, @Body Group group);

    @POST("/group/update")
    Call<StatusDescription> update(@Header("Content-Type") String content, @Body Group group);

    @POST("/group/join")
    Call<Group> join(@Header("Content-Type") String content, @Body HashMap map);

    @GET("/group/topRank/")
    Call<List<Group>> getTopRank(@Query("max") int max);

    @POST("/group/rank")
    Call<Integer> getRank(@Header("Content-Type") String content, @Body Group group);

    @POST("/group/rankByUser")
    Call<Integer> getRank(@Header("Content-Type") String content, @Body User user);

    @POST("/group/leave")
    Call<StatusDescription> leaveGroup(@Header("Content-Type") String content, @Body User user);

    @POST("/group/delete")
    Call<StatusDescription> deleteGroup(@Header("Content-Type") String content, @Body User user);

    @POST("/group/progress/get")
    Call<UserProgress> getProgress(@Header("Content-Type") String content, @Body User user);

    @POST("/group/progress/get")
    Call<UserProgress> getProgress(@Header("Content-Type") String content, @Body Group group);

    @POST("/group/progress/update")
    Call<StatusDescription> updateProgress(@Header("Content-Type") String content, @Body UserProgress progress);
}
