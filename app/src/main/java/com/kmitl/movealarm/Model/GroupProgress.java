package com.kmitl.movealarm.Model;

import android.content.Context;
import android.provider.BaseColumns;

import com.kmitl.movealarm.Helper.GroupProgressHelper;
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
    public static final String TABLE = "groupProgress";

    public class  Column{
        public static final String ID = BaseColumns._ID;
        public static final String GROUPID = "groupId";
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
    }

    public GroupProgress(int groupId) {
        this.groupId = groupId;
        this.exerciseTime = 0;
        this.acceptation = 0;
        this.declination = 0;
        this.totalActivity = 0;
        this.neck = 0;
        this.shoulder = 0;
        this.body = 0;
        this.arm =0;
        this.breast_belly_back =0;
        this.feet_leg_shin_calf = 0;
    }

    public GroupProgress(int id,
                         int groupId,
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
        this.groupId = groupId;
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

    public void save(Context context) {
        GroupProgressHelper groupProgressHelper = new GroupProgressHelper(context);
        GroupProgress groupProgress = groupProgressHelper.getGroupProgress(groupId);
        if (groupProgress == null) {
            this.id = groupProgressHelper.addGroupProgress(this);
        } else {
            this.id = groupProgress.getId();
            groupProgressHelper.updateGroupProgress(this);
        }
    }
    public static GroupProgress getProgressByGroupId(Context context,int groupId){
        GroupProgressHelper groupProgressHelper = new GroupProgressHelper(context);
        return groupProgressHelper.getGroupProgress(groupId);
    }
}


