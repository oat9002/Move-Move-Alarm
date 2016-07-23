package com.fatel.mamtv1.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProgress implements Serializable {
    int id;
    @SerializedName("user_id")
    int userId;
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
