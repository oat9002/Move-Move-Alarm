package com.fatel.mamtv1.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupProgress implements Serializable{
    int id;
    @SerializedName("group_id")
    int groupId;
    @SerializedName("exercise_time")
    int exerciseTime;
    int acceptation;
    int declination;
    int totalActivity;
    int neck;
    int shoulder;
    int chestBack;
    int wrist;
    int waist;
    int hipLegCalf;
}
