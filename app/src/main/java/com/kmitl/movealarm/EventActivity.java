package com.kmitl.movealarm;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.kmitl.movealarm.Model.Group;
import com.kmitl.movealarm.Model.Posture;
import com.kmitl.movealarm.Model.StatusDescription;
import com.kmitl.movealarm.RESTService.Implement.GroupServiceImp;
import com.kmitl.movealarm.Service.Cache;
import com.kmitl.movealarm.Service.MyApplication;
import com.kmitl.movealarm.Service.UserManage;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.Retrofit;

public class EventActivity extends AppCompatActivity {

    @BindView(R.id.rtime) TextView txtR;
    @BindView(R.id.atime) TextView txtA;
    @BindView(R.id.des) TextView txtDes;
    @BindView(R.id.imgname) TextView txtName;
    @BindView(R.id.img) ImageView imgView;
    @BindView(R.id.vdo) VideoView vdoView;
    AnimationDrawable frameAnimation;
    int count=0;
    ArrayList<Posture> img ;
    int exerciseImg;
    String exerciseName;
    String exerciseDes;
    int exerciseVdo;

    CountDownTimer time1;
    CountDownTimer time2;

    private static final String FORMAT = "%02d:%02d";
    public static Context context;
    private MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
        ButterKnife.bind(this);

        final Window win= getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        img = new ArrayList<>();
        ActivityHandle activityHandle=new ActivityHandle(this);
        context=getApplicationContext();
        img = activityHandle.getRandomPosture();
        exerciseImg=(img.get(count)).getImage();
        exerciseName=(img.get(count)).getName();
        exerciseDes=(img.get(count)).getDescription();
        exerciseVdo=(img.get(count)).getVdo();

        txtDes.setText(exerciseDes);
        txtName.setText(exerciseName);

        if(exerciseVdo != -1){
            vdoView.setVisibility(View.VISIBLE);
            imgView.setVisibility(View.INVISIBLE);
            vdoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+exerciseVdo));
            vdoView.start();
        }
        else{

            vdoView.setBackgroundResource(0);
            vdoView.setVisibility(View.INVISIBLE);
            imgView.setVisibility(View.VISIBLE);
            imgView.setBackgroundResource(exerciseImg);
            // Get the background, which has been compiled to an AnimationDrawable object.
            frameAnimation = (AnimationDrawable) imgView.getBackground();
            // Start the animation (looped playback by default).
            frameAnimation.start();

        }

        time1 =new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                txtR.setText("เวลาท่าบริหาร   " + String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));

            }

            public void onFinish() {
                playBell();
                txtR.setText("เสร็จสิ้น!");
                if(exerciseVdo == -1)
                    frameAnimation.stop();
                else
                    vdoView.stopPlayback();
                count++;
                if(count<img.size()) {
                    exerciseName=(img.get(count)).getName();
                    exerciseImg=(img.get(count)).getImage();
                    exerciseDes=(img.get(count)).getDescription();
                    txtDes.setText(exerciseDes);
                    txtName.setText(exerciseName);
                    if(exerciseVdo != -1){
                        vdoView.setVisibility(View.VISIBLE);
                        imgView.setVisibility(View.INVISIBLE);
                        vdoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+exerciseVdo));
                        vdoView.start();
                    }
                    else{

                        vdoView.setBackgroundResource(0);
                        vdoView.setVisibility(View.INVISIBLE);
                        imgView.setVisibility(View.VISIBLE);
                        imgView.setBackgroundResource(exerciseImg);
                        // Get the background, which has been compiled to an AnimationDrawable object.
                        frameAnimation = (AnimationDrawable) imgView.getBackground();
                        // Start the animation (looped playback by default).
                        frameAnimation.start();

                    }
                    start();
                }

            }
        }.start();



        time2 =new CountDownTimer(120000, 1000) {

            public void onTick(long millisUntilFinished) {
                txtA.setText("เวลากิจกรรม   "+String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                playBell();
                txtA.setText("เสร็จสิ้น!");

                if(exerciseVdo == -1)
                    frameAnimation.stop();
                else
                    vdoView.stopPlayback();
                //sent data to serve

                count++;
                //history
                // TODO update progress
                //
                //go to main
                Intent i1 = new Intent(EventActivity.this, MainActivity.class);
                i1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i1);
                //send score to back

                final Group group = Group.find(UserManage.getCurrentUser().getGroupId(),EventActivity.this);
                //Group group = (Group) Cache.getInstance().getData("groupData");
                group.addScore(2);
                updateEvent(img,group);
                group.save(MyApplication.getAppContext());
                group.getProgress().save(MyApplication.getAppContext());

                GroupServiceImp.getInstance().updateGroup(group, new Callback<StatusDescription>() {
                    @Override
                    public void onResponse(retrofit.Response<StatusDescription> response, Retrofit retrofit) {
//                        Log.i("response from server", response.body().toString());

//                        Toast.makeText(getApplicationContext(), "สามารถอัปเดตข้อมูลไปยังเซิร์ฟเวอร์ได้", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        makeSnackbar("ไม่สามารถอัปเดตข้อมูลกับเซิร์ฟเวอร์ได้");
                    }
                });
                finish();
            }
        }.start();

    }


    public void linkHome(View view)
    {
        if(exerciseVdo == -1)
            frameAnimation.stop();
        else
            vdoView.stopPlayback();
        if(time1!=null){
            time1.cancel();
            time1=null;
        }
        if(time2!=null){
            time2.cancel();
            time2=null;
        }
        //history

        // TODO update progress
        updatecancel();
        //

        Intent i1 = new Intent(EventActivity.this, MainActivity.class);
        i1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i1);
    }

    public void makeSnackbar(String text)
    {
        Snackbar.make(txtA, text, Snackbar.LENGTH_LONG).show();
    }
    public void updatecancel(){
        final Group groupuser = Group.find(UserManage.getCurrentUser().getGroupId(),EventActivity.this);
//        Group groupuser = (Group) Cache.getInstance().getData("groupData");
        if(groupuser!=null){
            int cancelweek = groupuser.getProgress().getDeclination()+1;
            int totalweek = groupuser.getProgress().getTotalActivity()+1;
            groupuser.getProgress().setDeclination(cancelweek);
            groupuser.getProgress().setTotalActivity(totalweek);
            groupuser.getProgress().setExerciseTime(groupuser.getProgress().getExerciseTime()+(count));
            groupuser.getProgress().save(MyApplication.getAppContext());

            GroupServiceImp.getInstance().updateGroup(groupuser, new Callback<StatusDescription>() {
                @Override
                public void onResponse(retrofit.Response<StatusDescription> response, Retrofit retrofit) {
//                    Toast.makeText(getApplicationContext(), "สามารถอัปเดตข้อมูลไปยังเซิร์ฟเวอร์ได้", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Throwable t) {
                    makeSnackbar("ไม่สามารถอัปเดตข้อมูลไปยังเซิร์ฟเวอร์ได้");
                }
            });
        }
    }
    public void updateEvent(ArrayList<Posture> mode,Group group){
        int numberofimg = mode.size();
        if(group!=null) {
            for (int i = 0; i < numberofimg; i++) {
                if ((mode.get(i)).getMode()==1){
                    group.getProgress().setNeck(group.getProgress().getNeck() + 1);}
                else if((mode.get(i)).getMode()==2){
                    group.getProgress().setShoulder(group.getProgress().getShoulder() + 1);}
                else if((mode.get(i)).getMode()==3){
                    group.getProgress().setChestBack(group.getProgress().getChestBack() + 1);}
                else if((mode.get(i)).getMode() == 4) {
                    group.getProgress().setWrist(group.getProgress().getWrist() + 1);}
                else if((mode.get(i)).getMode() == 5) {
                    group.getProgress().setWaist(group.getProgress().getWaist() + 1);}
                else if((mode.get(i)).getMode()==6){
                    group.getProgress().setHipLegCalf(group.getProgress().getHipLegCalf() + 1);}
            }
            group.getProgress().setAcceptation(group.getProgress().getAcceptation()+1);
            group.getProgress().setTotalActivity(group.getProgress().getTotalActivity()+1);
            group.getProgress().setExerciseTime(group.getProgress().getExerciseTime()+(count));
        }
    }
    public void playBell(){

        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(this,R.raw.bell);
        mediaPlayer.start();
    }
}
