package com.fatel.mamtv1.Model;

import android.content.Context;
import android.provider.BaseColumns;

import com.fatel.mamtv1.Helper.GroupProgressHelper;
import com.fatel.mamtv1.Helper.UserProgressHelper;
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
        public static final String CHESTBACK ="chestBack";
        public static final String WRIST = "wrist";
        public static final String WAIST = "waist";
        public static final String HIPLEGCALF = "hipLegCalf";
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
                         int chestBack,
                         int wrist,
                         int waist,
                         int hipLegCalf){
        this.id = id;
        this.userId = userId;
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

