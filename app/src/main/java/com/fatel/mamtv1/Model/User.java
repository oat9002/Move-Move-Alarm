package com.fatel.mamtv1.Model;

import android.content.Context;
import android.provider.BaseColumns;
import android.util.Log;

import com.fatel.mamtv1.Converter;
import com.fatel.mamtv1.UserHelper;

import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Monthon on 3/11/2558.
 */
@Getter
@Setter
public class User {
    private int id;
    private int idUser;
    private String birthDay;
    private int age;
    private int score;
    private int height;
    private int weight;
    private int waistline;
    private Double bmi;
    private String email;
    private String facebookID;
    private String facebookFirstName;
    private String facebookLastName;
    private int profileImage;
    private int login;
    private int idGroup;
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
    }

    public User(int idUser, String facebookID, String facebookFirstName) {
        this.id = -1;
        this.score = 0;
        this.login = 0;
        this.idUser = idUser;
        this.facebookID = facebookID;
        this.facebookFirstName = facebookFirstName;
        this.idGroup = 0;
        this.statesw = 1;
    }

    public User(int id, int idUser, String birthDay, int age, int score
            , int height, int weight, int waistline, double bmi, String email, String facebookID, String facebookFirstName,
                String facebookLastName, int profileImage, int login, int idGroup, int statesw) {
        this.id = id;
        this.idUser = idUser;
        this.birthDay = birthDay;
        this.age = age;
        this.score = score;
        this.height = height;
        this.weight = weight;
        this.waistline = waistline;
        this.bmi = bmi;
        this.email = email;
        this.facebookID = facebookID;
        this.facebookFirstName = facebookFirstName;
        this.facebookLastName = facebookLastName;
        this.profileImage = profileImage;
        this.login = login;
        this.idGroup = idGroup;
        this.statesw = statesw;
    }

    public void save(Context context) {

        UserHelper userHelper = new UserHelper(context);
        if (this.id == -1) {
            this.id = userHelper.addUser(this);
            Log.i("User", "funh savenew :" + id);
        } else {
            userHelper.updateUser(this);
            Log.i("User", "funh saveold :" + id);
        }
    }

    public static User find(int idUser, Context context) {
        UserHelper userHelper = new UserHelper(context);
        if (userHelper.getUser(idUser) == null) {
            return null;
        } else
            return userHelper.getUser(idUser);
    }

    public static User checkLogin(Context context) {
        UserHelper userHelper = new UserHelper(context);
        return userHelper.checkLoginUser();
    }

    public HashMap<String, Object> getGeneralValues() {
        HashMap<String, Object> userData = new HashMap<>();
        userData.put("id", this.getIdUser());
        userData.put("birthday", this.getBirthDay());
        userData.put("age", this.getAge());
        userData.put("score", this.getScore());
        userData.put("height", this.getHeight());
        userData.put("weight", this.getWeight());
        userData.put("waistline", this.getWaistline());
        userData.put("bmi", this.getBmi());
        userData.put("profileImage", this.getProfileImage());
        userData.put("facebookID", "fb" + this.getFacebookID());
        userData.put("facebookFirstName", this.getFacebookFirstName());
        userData.put("facebookLastName", this.getFacebookLastName());
        userData.put("email", this.getEmail());

        return userData;
    }

    public User setData(HashMap<String, Object> userData, Context context) {
        Converter converter = Converter.getInstance();
        this.setBirthDay(converter.toString(userData.get("birthDay")));
        this.setAge(converter.toInt(userData.get("age")));
        this.setScore(converter.toInt(userData.get("score")));
        this.setHeight(converter.toInt(userData.get("height")));
        this.setWeight(converter.toInt(userData.get("weight")));
        this.setWaistline(converter.toInt(userData.get("waistline")));
        this.setBmi(converter.toDouble(userData.get("bmi")));
        this.setEmail(converter.toString(userData.get("email")));
        this.setIdGroup(converter.toInt(userData.get("groupId")));
        this.setFacebookID(converter.toString(userData.get("facebookID")).substring(2));
        this.setFacebookFirstName(converter.toString(userData.get("facebookFirstName")));
        this.setFacebookLastName(converter.toString(userData.get("facebookLastName ")));
        this.setLogin(1);
        this.save(context);
        return this;
    }
}