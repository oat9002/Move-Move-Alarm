package com.kmitl.movealarm.Model;

import android.content.Context;
import android.provider.BaseColumns;

import com.kmitl.movealarm.Helper.UserHelper;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Monthon on 3/11/2558.
 */
@Getter
@Setter
public class User implements Serializable{

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Expose
    private int id;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private int internalId;

    @Expose private String birthdate;
    @Expose private int age;
    @Expose private int score;
    @Expose private int height;
    @Expose private int weight;
    @Expose private int waistline;
    @Expose private Double bmi;
    @Expose private String email;
    @SerializedName("facebook_id") public String facebookId;
    @Expose private String facebookFirstName;
    @Expose private String facebookLastName;
    @Expose private int profileImage;
    @SerializedName("group_id") private int groupId;
    @SerializedName("daily_progress") private UserProgress dailyProgress;
    @SerializedName("weekly_progress") private UserProgress weeklyProgress;

    private int login;
    private int statesw;
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE = "user";

    public class Column {
        public static final String ID = BaseColumns._ID;
        public static final String IDUSER = "iduser";
        public static final String BIRTHDAY = "birthday";
        public static final String AGE = "age";
        public static final String SCORE = "score";
        public static final String HEIGHT = "height";
        public static final String WEIGHT = "weight";
        public static final String WAISTLINE = "waistline";
        public static final String BMI = "bmi";
        public static final String EMAIL = "email";
        public static final String FACEBOOKID = "facebookid";
        public static final String FACEBOOKFIRSTNAME = "facebookfirstname";
        public static final String FACEBOOKLASTNAME = "facebooklastname";
        public static final String PROFILEIMAGE = "profileimage";
        public static final String LOGIN = "login";
        public static final String IDGROUP = "idgroup";
        public static final String STATESW = "statesw";
    }

    public User() {
        dailyProgress = new UserProgress();
        weeklyProgress = new UserProgress();
    }

    public User(int userId, String facebookId, String facebookFirstName) {
        this.internalId = -1;
        this.score = 0;
        this.login = 0;
        this.id = userId;
        this.facebookId = facebookId;
        this.facebookFirstName = facebookFirstName;
        this.groupId = 0;
        this.statesw = 1;
        dailyProgress = new UserProgress();
        weeklyProgress = new UserProgress();
    }

    public User(int id, int idUser, String birthdate, int age, int score
            , int height, int weight, int waistline, double bmi, String email, String facebookId, String facebookFirstName,
                String facebookLastName, int profileImage, int login, int groupId, int statesw) {
        this.internalId = id;
        this.id = idUser;
        this.birthdate = birthdate;
        this.age = age;
        this.score = score;
        this.height = height;
        this.weight = weight;
        this.waistline = waistline;
        this.bmi = bmi;
        this.email = email;
        this.facebookId = facebookId;
        this.facebookFirstName = facebookFirstName;
        this.facebookLastName = facebookLastName;
        this.profileImage = profileImage;
        this.login = login;
        this.groupId = groupId;
        this.statesw = statesw;
        dailyProgress = new UserProgress();
        weeklyProgress = new UserProgress();
    }

    public void save(Context context) {
        UserHelper userHelper = new UserHelper(context);
        User checkUser = userHelper.getUser(id);
        if (checkUser == null) {
            this.internalId = userHelper.addUser(this);
        } else {
            this.internalId = checkUser.getInternalId();
            userHelper.updateUser(this);
        }
    }

    public static User find(int id, Context context) {
        UserHelper userHelper = new UserHelper(context);
        User user = userHelper.getUser(id);
        if (user == null) {
            return null;
        } else
            return user;
    }

    public static User checkLogin(Context context) {
        UserHelper userHelper = new UserHelper(context);
        return userHelper.checkLoginUser();
    }

    public HashMap<String, Object> getGeneralValues() {
        HashMap<String, Object> userData = new HashMap<>();
        userData.put("id", this.getId());
        userData.put("birthday", this.getBirthdate());
        userData.put("age", this.getAge());
        userData.put("score", this.getScore());
        userData.put("height", this.getHeight());
        userData.put("weight", this.getWeight());
        userData.put("waistline", this.getWaistline());
        userData.put("bmi", this.getBmi());
        userData.put("profileImage", this.getProfileImage());
        userData.put("facebookId", "fb" + this.getFacebookId());
        userData.put("facebookFirstName", this.getFacebookFirstName());
        userData.put("facebookLastName", this.getFacebookLastName());
        userData.put("email", this.getEmail());

        return userData;
    }

    public void addScore(int score){
        this.score+=score;
    }

    public int getInternalId() {
        return internalId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}