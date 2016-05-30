package com.fatel.mamtv1;

import android.content.Context;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by Monthon on 3/11/2558.
 */
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
    public class Column{
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
    public User(){
    }
    public User(int idUser,String facebookID,String facebookFirstName){
        this.id=-1;
        this.score=0;
        this.login=0;
        this.idUser = idUser;
        this.facebookID = facebookID;
        this.facebookFirstName = facebookFirstName;
        this.idGroup=0;
        this.statesw =1;
    }
    public User(int id,int idUser,String birthDay,int age,int score
            ,int height,int weight,int waistline,double bmi,String email, String facebookID, String facebookFirstName,
                String facebookLastName, int profileImage,int login,int idGroup,int statesw){
        this.id=id;
        this.idUser = idUser;
        this.birthDay = birthDay;
        this.age = age;
        this.score = score;
        this.height = height;
        this.weight = weight;
        this.waistline = waistline;
        this.bmi = bmi;
        this.email = email;
        this.facebookID=facebookID;
        this.facebookFirstName=facebookFirstName;
        this.facebookLastName=facebookLastName;
        this.profileImage = profileImage;
        this.login=login;
        this.idGroup=idGroup;
        this.statesw = statesw;
    }
    public void save (Context context){

        UserHelper userHelper = new UserHelper(context);
        if(this.id == -1) {
            this.id = userHelper.addUser(this);
            Log.i("User", "funh savenew :" + id);
        }
        else {
            userHelper.updateUser(this);
            Log.i("User", "funh saveold :" + id);
        }
    }
    public static User find(int idUser,Context context){
        UserHelper userHelper = new UserHelper(context);
        if(userHelper.getUser(idUser)==null){
            return null;
        }
        else
            return userHelper.getUser(idUser);
    }
    public static User checkLogin(Context context){
        UserHelper userHelper = new UserHelper(context);
        return userHelper.checkLoginUser();
    }

    public int getId(){
        return id;
    }
    public int getIdUser(){
        return idUser;
    }
    public String getBirthDay(){
        return birthDay;
    }
    public int getAge(){
        return age;
    }
    public int getScore(){
        return score;
    }
    public int getHeight(){
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public int getWaistline() {
        return waistline;
    }

    public double getBmi() {
        return bmi;
    }

    public String getEmail(){
        return email;
    }
    public String getFacebookID(){
        return facebookID;
    }
    public String getFacebookFirstName(){
        return facebookFirstName;
    }
    public String getFacebookLastName(){
        return facebookLastName;
    }
    public int getStatesw(){return statesw;}
    public int getProfileImage(){
        return profileImage;
    }
    public int getLogin(){ return login; }
    public int getIdGroup(){return idGroup;}
    public void setId(int id){
        this.id = id;
    }
    public void setIdUser(int idUser){
        this.idUser = idUser;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setWaistline(int waistline) {
        this.waistline = waistline;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public void setAge(int age){
        this.age = age;
    }
    public void setScore(int score){
        this.score = score;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setFacebookID(String facebookID){
        this.facebookID = facebookID;
    }
    public  void setFacebookFirstName(String facebookFirstName){
        this.facebookFirstName = facebookFirstName;
    }
    public void setFacebookLastName(String facebookLastName){
        this.facebookLastName = facebookLastName;
    }
    public void setProfileImage(int profileImage) {
        this.profileImage = profileImage;
    }
    public void addScore(int score){
        this.score+=score;
    }

    public void setLogin(int login) {
        this.login = login;
    }

    public void setIdGroup(int idGroup){this.idGroup = idGroup;}
    public void setStatesw(int statesw){
        this.statesw = statesw;
    }
    
    public HashMap<String, Object> getGeneralValues()
    {
        HashMap<String, Object> userData = new HashMap<>();
        userData.put("id", this.getIdUser());
        userData.put("birthday",this.getBirthDay());
        userData.put("age",this.getAge());
        userData.put("score",this.getScore());
        userData.put("height",this.getHeight());
        userData.put("weight",this.getWeight());
        userData.put("waistline",this.getWaistline());
        userData.put("bmi",this.getBmi());
        userData.put("profileImage",this.getProfileImage());
        userData.put("facebookID", "fb" + this.getFacebookID());
        userData.put("facebookFirstName",this.getFacebookFirstName());
        userData.put("facebookLastName",this.getFacebookLastName());
        userData.put("email",this.getEmail());

        return userData;
    }
}