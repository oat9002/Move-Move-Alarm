package com.kmitl.movealarm.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kmitl.movealarm.Model.Group;


public class GroupHelper extends SQLiteOpenHelper {
    private final String TAG = getClass().getSimpleName();
    private SQLiteDatabase sqLiteDatabase;

    public GroupHelper(Context context){
        super(context, "fatel_Group.db", null, Group.DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        //not sure %s int for image
        String CREATE_TABLE = String.format("CREATE TABLE %s " +
                        "(%s INTEGER PRIMARY KEY  AUTOINCREMENT,%s INTEGER,%s TEXT,%s TEXT,%s INTEGER,%s INTEGER" +
                        ")",
                Group.TABLE,
                Group.Column.ID,
                Group.Column.GROUPID,
                Group.Column.NAME,
                Group.Column.STATUS,
                Group.Column.SCORE,
                Group.Column.ADMINID);
        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        String DROP_GROUP_TABLE = "DROP TABLE IF EXISTS"+ Group.TABLE;
        db.execSQL(DROP_GROUP_TABLE);
        onCreate(db);
    }
    public int addGroup(Group group) {
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Group.Column.GROUPID, group.getGroupId());
        values.put(Group.Column.NAME, group.getName());
        values.put(Group.Column.STATUS, group.getStatus());
        values.put(Group.Column.SCORE, group.getScore());
        values.put(Group.Column.ADMINID, group.getAdmin().getId());

        long id = sqLiteDatabase.insert(Group.TABLE, null, values);
        sqLiteDatabase.close();
        return ((int)id);
    }
    public void updateGroup(Group group) {
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
            values.put(Group.Column.GROUPID, group.getGroupId());
            values.put(Group.Column.NAME, group.getName());
            values.put(Group.Column.STATUS, group.getStatus());
            values.put(Group.Column.SCORE, group.getScore());
            values.put(Group.Column.ADMINID, group.getAdmin().getId());

            int row = sqLiteDatabase.update(Group.TABLE,
                values,
                Group.Column.ID + " = ? ",
                new String[] { String.valueOf(group.getInternalId())});
        sqLiteDatabase.close();
    }
    public Group getGroup (int groupId){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Group.TABLE, new String[]{ Group.Column.ID,
                        Group.Column.GROUPID,
                        Group.Column.NAME,
                        Group.Column.STATUS,
                        Group.Column.SCORE,
                        Group.Column.ADMINID
                },Group.Column.GROUPID + " = ? ",
                new String[]{String.valueOf(groupId)}, null, null, null, null);
        Group group;
        boolean check=false;
        if (cursor != null) {
            check = cursor.moveToFirst();
        }
        if(check){

            group = new Group(cursor.getInt(0), cursor.getInt(1),
                    cursor.getString(2), cursor.getString(3), cursor.getInt(4), cursor.getInt(5));


            cursor.close();
            db.close();
            return group;
        }
        else {
            db.close();
            return null;
        }

    }
}