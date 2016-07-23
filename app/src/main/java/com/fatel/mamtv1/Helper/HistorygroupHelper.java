package com.fatel.mamtv1.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fatel.mamtv1.Model.GroupHistory;
import com.fatel.mamtv1.Model.History;

/**
 * Created by Monthon on 17/11/2558.
 */
public class HistorygroupHelper extends SQLiteOpenHelper{
    private final String TAG = getClass().getSimpleName();
    private SQLiteDatabase sqLiteDatabase;
    public HistorygroupHelper(Context context){
        super(context,"fatel_historygroup.db",null, History.DATABASE_VerSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_HISTORYGROUP_TEBLE = String.format("CREATE TABLE %s"+
                        "(%s INTEGER PRIMARY KEY AUTOINCREMENT,%s INTEGER,%s INTEGER,%s INTEGER)",
                GroupHistory.TABLE,
                GroupHistory.Column.ID,
                GroupHistory.Column.IDGROUP,
                GroupHistory.Column.NUMACCEPT,
                GroupHistory.Column.CANCEL);
        db.execSQL(CREATE_HISTORYGROUP_TEBLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        String DROP_HISTORYGROUP_TABLE ="DROP TABLE IF EXISTS"+ GroupHistory.TABLE;
        db.execSQL(DROP_HISTORYGROUP_TABLE);
        onCreate(db);
    }
    public int addHistoryGroup(GroupHistory history){
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GroupHistory.Column.IDGROUP,history.getIdGroup());
        values.put(GroupHistory.Column.NUMACCEPT,history.getNumberOfAccept());
        values.put(GroupHistory.Column.CANCEL, history.getCancelEvent());
        long id = sqLiteDatabase.insert(GroupHistory.TABLE,null,values);
        sqLiteDatabase.close();
        return ((int)id);
    }
    public void updateHistoryGroup(GroupHistory history){
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GroupHistory.Column.IDGROUP,history.getIdGroup());
        values.put(GroupHistory.Column.NUMACCEPT,history.getNumberOfAccept());
        values.put(GroupHistory.Column.CANCEL, history.getCancelEvent());
        int row = sqLiteDatabase.update(GroupHistory.TABLE,
                values,
                GroupHistory.Column.ID + " = ? ",
                new String[] {String.valueOf(history.getId())});
        sqLiteDatabase.close();
    }
    public boolean checkdata(){
        sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query
                (GroupHistory.TABLE, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            return true;
        }
        sqLiteDatabase.close();
        //Log.d("temp", temp + "");
        return false;
    }
    public GroupHistory getHistoryGroup(int idGroup){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(GroupHistory.TABLE, new String[]{GroupHistory.Column.ID,
                GroupHistory.Column.IDGROUP, GroupHistory.Column.NUMACCEPT, GroupHistory.Column.CANCEL
        }, GroupHistory.Column.IDGROUP + " = ? ", new String[]
                {String.valueOf(idGroup)}, null, null, null, null);
        GroupHistory history = null;
        boolean check = false;
        if(cursor != null){
            check = cursor.moveToFirst();
        }
        if(check){
            history = new GroupHistory(cursor.getInt(0),cursor.getInt(1),cursor.getInt(2),cursor.getInt(3));
            cursor.close();
        }
        db.close();
        return history;
    }
    public void deleteGroup(int id) {

        sqLiteDatabase = this.getWritableDatabase();

        sqLiteDatabase.delete(GroupHistory.TABLE, GroupHistory.Column.ID + " = ? ",
                new String[] { String.valueOf(id) });
        //  sqLiteDatabase.delete(History.TABLE, History.Column.ID + " = " + id, null);

        sqLiteDatabase.close();
    }
}
