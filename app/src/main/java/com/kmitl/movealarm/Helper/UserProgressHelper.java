package com.kmitl.movealarm.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kmitl.movealarm.Model.UserProgress;


public class UserProgressHelper extends SQLiteOpenHelper {
    private final String TAG = getClass().getSimpleName();
    private SQLiteDatabase sqLiteDatabase;

    public UserProgressHelper(Context context){
        super(context, "fatel_UProgress.db", null, UserProgress.DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        //not sure %s int for image
        String CREATE_USER_TABLE = String.format("CREATE TABLE %s " +
                        "(%s INTEGER PRIMARY KEY  AUTOINCREMENT,%s INTEGER,%s INTEGER,%s INTEGER,%s INTEGER,%s INTEGER,%s INTEGER,%s INTEGER,%s INTEGER,%s INTEGER,%s INTEGER,%s INTEGER,%s INTEGER" +
                        ")",
                UserProgress.TABLE,
                UserProgress.Column.ID,
                UserProgress.Column.USERID,
                UserProgress.Column.EXERCISETIME,
                UserProgress.Column.ACCEPT,
                UserProgress.Column.CANCEL,
                UserProgress.Column.TOTAL,
                UserProgress.Column.NECK,
                UserProgress.Column.SHOULDER,
                UserProgress.Column.BODY,
                UserProgress.Column.ARM,
                UserProgress.Column.BREASTBELLYBACK,
                UserProgress.Column.FEETLEGSHINCALF,
                UserProgress.Column.DAILY);
        db.execSQL(CREATE_USER_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        String DROP_USER_TABLE = "DROP TABLE IF EXISTS"+ UserProgress.TABLE;
        db.execSQL(DROP_USER_TABLE);
        onCreate(db);
    }
    public int addUserProgress(UserProgress userProgress,int daily) {
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserProgress.Column.USERID, userProgress.getUserId());
        values.put(UserProgress.Column.EXERCISETIME, userProgress.getExerciseTime());
        values.put(UserProgress.Column.ACCEPT, userProgress.getAcceptation());
        values.put(UserProgress.Column.CANCEL, userProgress.getDeclination());
        values.put(UserProgress.Column.TOTAL, userProgress.getTotalActivity());
        values.put(UserProgress.Column.NECK, userProgress.getNeck());
        values.put(UserProgress.Column.SHOULDER, userProgress.getShoulder());
        values.put(UserProgress.Column.BODY, userProgress.getBody());
        values.put(UserProgress.Column.ARM, userProgress.getArm());
        values.put(UserProgress.Column.BREASTBELLYBACK, userProgress.getBreast_belly_back());
        values.put(UserProgress.Column.FEETLEGSHINCALF, userProgress.getFeet_leg_shin_calf());
        values.put(UserProgress.Column.DAILY, daily);

        long id = sqLiteDatabase.insert(UserProgress.TABLE, null, values);
        sqLiteDatabase.close();
        return ((int)id);
    }
    public void updateUserProgress(UserProgress userProgress,int daily) {
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(UserProgress.Column.USERID, userProgress.getUserId());
        values.put(UserProgress.Column.EXERCISETIME, userProgress.getExerciseTime());
        values.put(UserProgress.Column.ACCEPT, userProgress.getAcceptation());
        values.put(UserProgress.Column.CANCEL, userProgress.getDeclination());
        values.put(UserProgress.Column.TOTAL, userProgress.getTotalActivity());
        values.put(UserProgress.Column.NECK, userProgress.getNeck());
        values.put(UserProgress.Column.SHOULDER, userProgress.getShoulder());
        values.put(UserProgress.Column.BODY, userProgress.getBody());
        values.put(UserProgress.Column.ARM, userProgress.getArm());
        values.put(UserProgress.Column.BREASTBELLYBACK, userProgress.getBreast_belly_back());
        values.put(UserProgress.Column.FEETLEGSHINCALF, userProgress.getFeet_leg_shin_calf());
        values.put(UserProgress.Column.DAILY, daily);

        int row = sqLiteDatabase.update(UserProgress.TABLE,
                values,
                UserProgress.Column.ID + " = ? ",
                new String[] { String.valueOf(userProgress.getId())});
        sqLiteDatabase.close();
    }
    public UserProgress getUserProgress(int UserId,int daily){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(UserProgress.TABLE, new String[]{ UserProgress.Column.ID,
                        UserProgress.Column.USERID,
                        UserProgress.Column.EXERCISETIME,
                        UserProgress.Column.ACCEPT,
                        UserProgress.Column.CANCEL,
                        UserProgress.Column.TOTAL,
                        UserProgress.Column.NECK,
                        UserProgress.Column.SHOULDER,
                        UserProgress.Column.BODY,
                        UserProgress.Column.ARM,
                        UserProgress.Column.BREASTBELLYBACK,
                        UserProgress.Column.FEETLEGSHINCALF,
                        UserProgress.Column.DAILY
                },UserProgress.Column.USERID + " = ? AND "+UserProgress.Column.DAILY+" = ?",
                new String[]{String.valueOf(UserId),String.valueOf(daily)}, null, null, null, null);
        UserProgress userProgress;
        boolean check=false;
        if (cursor != null) {
            check = cursor.moveToFirst();
        }
        if(check){

            userProgress = new UserProgress(cursor.getInt(0), cursor.getInt(1),
                cursor.getInt(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5),
                cursor.getInt(6), cursor.getInt(7), cursor.getInt(8),
                cursor.getInt(9), cursor.getInt(10), cursor.getInt(10));


        cursor.close();
            db.close();
            return userProgress;
        }
        else {
            db.close();
            return null;
        }

    }
}