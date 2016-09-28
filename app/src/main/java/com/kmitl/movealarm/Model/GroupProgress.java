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
    int chestBack;
    int wrist;
    int waist;
    @SerializedName("hip_leg_calf")
    int hipLegCalf;

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
        public static final String CHESTBACK ="chestBack";
        public static final String WRIST = "wrist";
        public static final String WAIST = "waist";
        public static final String HIPLEGCALF = "hipLegCalf";
    }

    public GroupProgress(int groupId) {
        this.groupId = groupId;
        this.exerciseTime = 0;
        this.acceptation = 0;
        this.declination = 0;
        this.totalActivity = 0;
        this.neck = 0;
        this.shoulder = 0;
        this.chestBack = 0;
        this.wrist =0;
        this.waist =0;
        this.hipLegCalf = 0;
    }

    public GroupProgress(int id,
                         int groupId,
                         int exerciseTime,
                         int acceptation,
                         int declination,
                         int totalActivity,
                         int neck,
                         int shoulder,
                         int chestBack,
                         int wrist,
                         int waist,
                         int hipLegCalf){
        this.id = id;
        this.groupId = groupId;
        this.exerciseTime = exerciseTime;
        this.acceptation = acceptation;
        this.declination = declination;
        this.totalActivity = totalActivity;
        this.neck = neck;
        this.shoulder =shoulder;
        this.chestBack =chestBack;
        this.wrist = wrist;
        this.waist =waist;
        this.hipLegCalf = hipLegCalf;
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


