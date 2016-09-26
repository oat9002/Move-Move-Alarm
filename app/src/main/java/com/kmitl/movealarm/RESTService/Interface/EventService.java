package com.kmitl.movealarm.RESTService.Interface;

import com.kmitl.movealarm.Model.Event;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by Moobi on 18-Jul-16.
 */
public interface EventService {
    @GET("event/getEvent")
    Call<Event> getEvent();
}
