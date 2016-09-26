package com.kmitl.movealarm;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.kmitl.movealarm.Model.Group;
import com.kmitl.movealarm.Model.StatusDescription;
import com.kmitl.movealarm.RESTService.Implement.GroupServiceImp;
import com.kmitl.movealarm.Service.Cache;
import com.kmitl.movealarm.Service.UserManage;

import retrofit.Callback;
import retrofit.Retrofit;

public class EventActAlarm extends AppCompatActivity {

    private Vibrator v;
    private MediaPlayer m;
    private Ringtone r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventact_alarm);
        final Window win= getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        long[] pattern = {0, 500, 1000};
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if(UserManage.getInstance(EventActAlarm.this).getCurrentUser().getStatesw() == 1) {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            m = MediaPlayer.create(this, notification);
            m.setLooping(true);
            m.start();
        }
        v.vibrate(pattern, 0);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_act_alarm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        Intent i1 = new Intent(EventActAlarm.this, MainActivity.class);
        i1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i1);
        finish();
        v.cancel();
        if(UserManage.getInstance(EventActAlarm.this).getCurrentUser().getGroupId() == 1) {
            m.reset();
        }
        updatecancel();
    }
    @Override
    protected void onStop() {
        v.cancel();
        if(UserManage.getInstance(EventActAlarm.this).getCurrentUser().getGroupId() == 1) {
            m.reset();
        }
        super.onStop();
    }
    public void linkActivity(View view){
        //history
//        Log.i("linkacti",""+UserManage.getInstance(this).getCurrentUser().getGroupId());
        // update progress call volley


        Intent intent = new Intent(this, EventActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
        v.cancel();
        if(UserManage.getInstance(EventActAlarm.this).getCurrentUser().getStatesw() == 1) {
            m.reset();
        }
    }

    public void linkHome(View view){

        Intent i1 = new Intent(EventActAlarm.this, MainActivity.class);
        i1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i1);
        finish();
        v.cancel();
        if(UserManage.getInstance(EventActAlarm.this).getCurrentUser().getGroupId() == 1) {
            m.reset();
        }
        updatecancel();
    }
    public void updatecancel(){
        Group groupuser = (Group) Cache.getInstance().getData("groupData");
        if(groupuser!=null){
            int cancelweek = groupuser.getProgress().getDeclination()+1;
            int totalweek = groupuser.getProgress().getTotalActivity()+1;
            groupuser.getProgress().setDeclination(cancelweek);
            groupuser.getProgress().setTotalActivity(totalweek);
            GroupServiceImp.getInstance().updateGroup(groupuser, new Callback<StatusDescription>() {
                @Override
                public void onResponse(retrofit.Response<StatusDescription> response, Retrofit retrofit) {
//                    Log.i("response from server", response.body().toString());
//                    makeSnackbar("สามารถอัปเดตข้อมูลไปยังเซิร์ฟเวอร์ได้");
                }

                @Override
                public void onFailure(Throwable t) {
                    makeSnackbar("ไม่สามารถอัปเดตข้อมูลไปยังเซิร์ฟเวอร์ได้");
                }
            });
        }
    }
    public void makeSnackbar(String text)
    {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }
}