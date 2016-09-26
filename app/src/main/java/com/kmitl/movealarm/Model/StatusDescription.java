package com.kmitl.movealarm.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusDescription {
    @SerializedName("status")
    private boolean success;
    @Expose
    private String description;
    @Expose
    private Map<String, Object> data;
}
