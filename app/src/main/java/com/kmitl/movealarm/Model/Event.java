package com.kmitl.movealarm.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Moobi on 18-Jul-16.
 */
@Getter
@Setter
public class Event implements Serializable{
    @Expose
    private Date time;
    @SerializedName("posture_list")
    private String postureList;
}
