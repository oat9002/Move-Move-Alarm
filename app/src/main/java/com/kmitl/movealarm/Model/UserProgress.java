package com.kmitl.movealarm.Model;

import android.content.Context;
import android.provider.BaseColumns;

import com.kmitl.movealarm.Helper.UserProgressHelper;
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
    @SerializedName("accept")
    int acceptation;
    int declination;
    @SerializedName("total_activity")
    int totalActivity;
    int neck;
    int shoulder;
    @SerializedName("chest_back")
    int body;
    @SerializedName("wrist")
    int arm;
    @SerializedName("waist")
    int breast_belly_back;
    @SerializedName("hip_leg_calf")
    int feet_leg_shin_calf;


    public static final int DATABASE_VERSION = 1;
    public static final String TABLE = "userProgress";

    public class  Column{
        public static final String ID = BaseColumns._ID;
        public static final String USERID = "userId";
        public static final String EXERCISETIME = "exerciseTime";
        public static final String ACCEPT = "accept";
        public static final String CANCEL = "cancel";
        public static final String TOTAL = "total";
        public static final String NECK = "neck";
        public static final String SHOULDER = "shoulder";
        public static final String BODY ="body";
        public static final String ARM = "arm";
        public static final String BREASTBELLYBACK = "breast_belly_back";
        public static final String FEETLEGSHINCALF = "feet_leg_shin_calf";
        public static final String DAILY = "daily";
    }

    public UserProgress() {
    }

    public UserProgress(int id,
                         int userId,
                         int exerciseTime,
                         int acceptation,
                         int declination,
                         int totalActivity,
                         int neck,
                         int shoulder,
                         int body,
                         int arm,
                         int breast_belly_back,
                         int feet_leg_shin_calf){
        this.id = id;
        this.userId = userId;
        this.exerciseTime = exerciseTime;
        this.acceptation = acceptation;
        this.declination = declination;
        this.totalActivity = totalActivity;
        this.neck = neck;
        this.shoulder =shoulder;
        this.body = body;
        this.arm =arm;
        this.breast_belly_back =breast_belly_back;
        this.feet_leg_shin_calf = feet_leg_shin_calf;
    }

    public void save(Context context,int daily) {
        UserProgressHelper userProgressHelper = new UserProgressHelper(context);
        UserProgress userProgress = userProgressHelper.getUserProgress(userId,daily);
        if (userProgress == null) {
            this.id = userProgressHelper.addUserProgress(this,daily);
        } else {
            this.id = userProgress.getId();
            userProgressHelper.updateUserProgress(this,daily);
        }
    }

    public static UserProgress getProgressByUserId(Context context,int userId,int daily){
        UserProgressHelper userProgressHelper = new UserProgressHelper(context);
        return userProgressHelper.getUserProgress(userId,daily);
    }
}

