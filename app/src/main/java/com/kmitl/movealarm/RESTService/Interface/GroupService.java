package com.kmitl.movealarm.RESTService.Interface;

import com.kmitl.movealarm.Model.Group;
import com.kmitl.movealarm.Model.StatusDescription;
import com.kmitl.movealarm.Model.User;
import com.kmitl.movealarm.Model.UserProgress;

import java.util.HashMap;
import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
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
