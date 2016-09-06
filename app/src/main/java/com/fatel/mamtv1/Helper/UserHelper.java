package com.fatel.mamtv1.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.activeandroid.query.Select;
import com.fatel.mamtv1.Model.User;

/**
 * Created by Monthon on 3/11/2558.
 */
public class UserHelper {

    public static User findUser(int userId){
        return  new Select()
                .from(User.class)
                .where("userId = ?",String.valueOf(userId))
                .executeSingle();
    }

    public void addUser(int id, String birthdate, int age, int score
            , int height, int weight, int waistline, double bmi, String email, String facebookId, String facebookFirstName,
                        String facebookLastName, int profileImage, int login, int groupId, int statesw) {
        User user = new User (id,birthdate,age,score,height,weight,waistline,bmi,email,facebookId,
                                facebookFirstName,facebookLastName,profileImage,login,groupId,statesw);
        user.save();
    }

    public static void addUser(User user) {
        user.save();
    }

    public void updateUser(User user){
        User foundUser =this.findUser(user.getUserId());
        foundUser = user;
        foundUser.save();
    }
    public static User checkLoginUser(){

        return  new Select()
                .from(User.class)
                .where("login = ?",String.valueOf(1))
                .executeSingle();

    }
}