package com.kmitl.movealarm.RESTService.Implement;

import com.kmitl.movealarm.RESTService.Interface.EventService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Moobi on 18-Jul-16.
 */
public class EventServiceImp {
    private static Retrofit retrofit;
    private static EventService service;
    private static EventServiceImp eventService;
    public static final String BASE_URL = "http://enceladusiapetus.me:8001";

    private EventServiceImp(){};

    public static EventServiceImp getInstance() {
        if(retrofit == null) {
            Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy hh:mm:ss").create();
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
            service = retrofit.create(com.kmitl.movealarm.RESTService.Interface.EventService.class);
            eventService = new EventServiceImp();
        }

        return eventService;
    }

    public void getEvent(Callback callback) {
        Call call = service.getEvent();
        call.enqueue(callback);
    }
}
