package com.kmitl.movealarm.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kmitl.movealarm.Model.GroupProgress;


public class GroupProgressHelper extends SQLiteOpenHelper {
    private final String TAG = getClass().getSimpleName();
    private SQLiteDatabase sqLiteDatabase;

    public GroupProgressHelper(Context context){
        super(context, "fatel_GProgress.db", null, GroupProgress.DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        //not sure %s int for image
        String CREATE_USER_TABLE = String.format("CREATE TABLE %s " +
                        "(%s INTEGER PRIMARY KEY  AUTOINCREMENT,%s INTEGER,%s INTEGER,%s INTEGER,%s INTEGER,%s INTEGER,%s INTEGER,%s INTEGER,%s INTEGER,%s INTEGER,%s INTEGER,%s INTEGER" +
                        ")",
                GroupProgress.TABLE,
                GroupProgress.Column.ID,
                GroupProgress.Column.GROUPID,
                GroupProgress.Column.EXERCISETIME,
                GroupProgress.Column.ACCEPT,
                GroupProgress.Column.CANCEL,
                GroupProgress.Column.TOTAL,
                GroupProgress.Column.NECK,
                GroupProgress.Column.SHOULDER,
                GroupProgress.Column.BODY,
                GroupProgress.Column.ARM,
                GroupProgress.Column.BREASTBELLYBACK,
                GroupProgress.Column.FEETLEGSHINCALF);
        db.execSQL(CREATE_USER_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        String DROP_USER_TABLE = "DROP TABLE IF EXISTS"+ GroupProgress.TABLE;
        db.execSQL(DROP_USER_TABLE);
        onCreate(db);
    }
    public int addGroupProgress(GroupProgress groupProgress) {
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GroupProgress.Column.GROUPID, groupProgress.getGroupId());
        values.put(GroupProgress.Column.EXERCISETIME, groupProgress.getExerciseTime());
        values.put(GroupProgress.Column.ACCEPT, groupProgress.getAcceptation());
        values.put(GroupProgress.Column.CANCEL, groupProgress.getDeclination());
        values.put(GroupProgress.Column.TOTAL, groupProgress.getTotalActivity());
        values.put(GroupProgress.Column.NECK, groupProgress.getNeck());
        values.put(GroupProgress.Column.SHOULDER, groupProgress.getShoulder());
        values.put(GroupProgress.Column.BODY, groupProgress.getBody());
        values.put(GroupProgress.Column.ARM, groupProgress.getArm());
        values.put(GroupProgress.Column.BREASTBELLYBACK, groupProgress.getBreast_belly_back());
        values.put(GroupProgress.Column.FEETLEGSHINCALF, groupProgress.getFeet_leg_shin_calf());

        long id = sqLiteDatabase.insert(GroupProgress.TABLE, null, values);
        sqLiteDatabase.close();
        return ((int)id);
    }
    public void updateGroupProgress(GroupProgress groupProgress) {
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GroupProgress.Column.GROUPID, groupProgress.getGroupId());
        values.put(GroupProgress.Column.EXERCISETIME, groupProgress.getExerciseTime());
        values.put(GroupProgress.Column.ACCEPT, groupProgress.getAcceptation());
        values.put(GroupProgress.Column.CANCEL, groupProgress.getDeclination());
        values.put(GroupProgress.Column.TOTAL, groupProgress.getTotalActivity());
        values.put(GroupProgress.Column.NECK, groupProgress.getNeck());
        values.put(GroupProgress.Column.SHOULDER, groupProgress.getShoulder());
        values.put(GroupProgress.Column.BODY, groupProgress.getBody());
        values.put(GroupProgress.Column.ARM, groupProgress.getArm());
        values.put(GroupProgress.Column.BREASTBELLYBACK, groupProgress.getBreast_belly_back());
        values.put(GroupProgress.Column.FEETLEGSHINCALF, groupProgress.getFeet_leg_shin_calf());
        int row = sqLiteDatabase.update(GroupProgress.TABLE,
                values,
                GroupProgress.Column.ID + " = ? ",
                new String[] { String.valueOf(groupProgress.getId())});
        sqLiteDatabase.close();
    }
    public GroupProgress getGroupProgress(int groupId){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(GroupProgress.TABLE, new String[]{ GroupProgress.Column.ID,
                        GroupProgress.Column.GROUPID,
                        GroupProgress.Column.EXERCISETIME,
                        GroupProgress.Column.ACCEPT,
                        GroupProgress.Column.CANCEL,
                        GroupProgress.Column.TOTAL,
                        GroupProgress.Column.NECK,
                        GroupProgress.Column.SHOULDER,
                        GroupProgress.Column.BODY,
                        GroupProgress.Column.ARM,
                        GroupProgress.Column.BREASTBELLYBACK,
                        GroupProgress.Column.FEETLEGSHINCALF
                },GroupProgress.Column.GROUPID + " = ? ",
                new String[]{String.valueOf(groupId)}, null, null, null, null);
        GroupProgress groupProgress;
        boolean check=false;
        if (cursor != null) {
            check = cursor.moveToFirst();
        }
        if(check){

            groupProgress = new GroupProgress(cursor.getInt(0), cursor.getInt(1),
                cursor.getInt(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5),
                cursor.getInt(6), cursor.getInt(7), cursor.getInt(8),
                cursor.getInt(9), cursor.getInt(10), cursor.getInt(10));


        cursor.close();
            db.close();
            return groupProgress;
        }
        else {
            db.close();
            return null;
        }

    }
}