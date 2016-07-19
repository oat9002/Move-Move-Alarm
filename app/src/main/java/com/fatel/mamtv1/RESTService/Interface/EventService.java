package com.fatel.mamtv1.RESTService.Interface;

import com.fatel.mamtv1.Model.Event;

import java.util.Date;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by Moobi on 18-Jul-16.
 */
public interface EventService {
    @GET("event/getEvent")
    Call<Event> getEvent();
}
