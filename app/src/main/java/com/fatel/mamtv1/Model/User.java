package com.fatel.mamtv1.Model;

/**
 * Created by Forunh on 02-Sep-16.
 */


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "Book")
public class User extends Model implements Serializable {

//    @Getter(AccessLevel.NONE)
//    @Setter(AccessLevel.NONE)
    @SerializedName("id")
    @Column(name = "userId")
    private int userId;
//    @Getter(AccessLevel.NONE)
//    @Setter(AccessLevel.NONE)
//    private int internalId;
    @Column(name = "birthdate")
    @Expose private String birthdate;
    @Column(name = "age")
    @Expose private int age;
    @Column(name = "score")
    @Expose private int score;
    @Column(name = "height")
    @Expose private int height;
    @Column(name = "weight")
    @Expose private int weight;
    @Column(name = "waistline")
    @Expose private int waistline;
    @Column(name = "bmi")
    @Expose private Double bmi;
    @Column(name = "email")
    @Expose private String email;
    @Column(name = "facebookId")
    @SerializedName("facebook_id") public String facebookId;
    @Column(name = "facebookFirstName")
    @Expose private String facebookFirstName;
    @Column(name = "facebookLastName")
    @Expose private String facebookLastName;
    @Column(name = "profileImage")
    @Expose private int profileImage;
    @Column(name = "groupId")
    @SerializedName("group_id") private int groupId;
    @Column(name = "dailyProgress")
    @SerializedName("daily_progress") private UserProgress dailyProgress;
    @Column(name = "weeklyProgress")
    @SerializedName("weekly_progress") private UserProgress weeklyProgress;
    @Column(name = "login")
    private int login;
    @Column(name = "statesw")
    private int statesw;

    public User() {
        super();
        dailyProgress = new UserProgress();
        weeklyProgress = new UserProgress();
    }

    public User(String facebookId, String facebookFirstName) {
        super();
//        this.internalId = -1;
        this.score = 0;
        this.login = 0;
        this.userId = -1;
        this.facebookId = facebookId;
        this.facebookFirstName = facebookFirstName;
        this.groupId = 0;
        this.statesw = 1;
        dailyProgress = new UserProgress();
        weeklyProgress = new UserProgress();
    }

    public User(int id, String birthdate, int age, int score
            , int height, int weight, int waistline, double bmi, String email, String facebookId, String facebookFirstName,
                String facebookLastName, int profileImage, int login, int groupId, int statesw) {
        super();
        this.userId = id;
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
        this.save();
    }

}