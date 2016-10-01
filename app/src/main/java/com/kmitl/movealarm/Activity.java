package com.kmitl.movealarm;


import android.content.Context;

import android.content.Intent;

import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.media.MediaPlayer;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import android.widget.ImageView;
import android.widget.VideoView;

import com.kmitl.movealarm.Model.Posture;
import com.kmitl.movealarm.Model.StatusDescription;
import com.kmitl.movealarm.Model.User;
import com.kmitl.movealarm.RESTService.Implement.UserServiceImp;
import com.kmitl.movealarm.Service.AlarmReceiver;
import com.kmitl.movealarm.Service.Camera;
import com.kmitl.movealarm.Service.Cache;
import com.kmitl.movealarm.Service.UserManage;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.Retrofit;


public class Activity extends AppCompatActivity {

    @BindView(R.id.rtime) TextView txtR;
    @BindView(R.id.atime) TextView txtA;
    @BindView(R.id.des) TextView txtDes;
    @BindView(R.id.imgname) TextView txtName;
//    @BindView(R.id.img) ImageView imgView;
    @BindView(R.id.vdo) VideoView vdoView;
//    AnimationDrawable frameAnimation;
    int count=0;
    ArrayList<Posture> img ;
    int exerciseImg;
    String exerciseName;
    String exerciseDes;
    int exerciseVdo;
    CountDownTimer time1;
    CountDownTimer time2;
    static ActivityHandle activityHandle;

    private static final String FORMAT = "%02d:%02d";
    public static Context context;
    private MediaPlayer mediaPlayer;
    private MediaPlayer songPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
        ButterKnife.bind(this);

        final Window win= getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        img = new ArrayList<>();
        activityHandle=new ActivityHandle(this);
        context=getApplicationContext();
        img = activityHandle.getRandomPosture();

        exerciseImg=(img.get(count)).getImage();
        exerciseName=(img.get(count)).getName();
        exerciseDes=(img.get(count)).getDescription();
        exerciseVdo=(img.get(count)).getVdo();

        txtDes.setText(exerciseDes);
        txtName.setText(exerciseName);

//        if(exerciseVdo != -1){
            vdoView.setVisibility(View.VISIBLE);
//            imgView.setVisibility(View.INVISIBLE);
            vdoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+exerciseVdo));
            vdoView.setOnPreparedListener (new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(true);
                }
            });
            vdoView.start();
//        }
//        else{
//
//            vdoView.setBackgroundResource(0);
//            vdoView.setVisibility(View.INVISIBLE);
//            imgView.setVisibility(View.VISIBLE);
//            imgView.setBackgroundResource(exerciseImg);
            // Get the background, which has been compiled to an AnimationDrawable object.
//            frameAnimation = (AnimationDrawable) imgView.getBackground();
            // Start the animation (looped playback by default).
//            frameAnimation.start();

//        }
        playSong();
        time1 = new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                txtR.setText("เวลาท่าบริหาร  :    " + String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))) + " นาที");

            }

            public void onFinish() {
                playBell();
                txtR.setText("เสร็จสิ้น!");
//                if(exerciseVdo == -1)
//                    frameAnimation.stop();
//                else
                    vdoView.stopPlayback();
                count++;
                if(count<img.size()) {

                    exerciseImg=(img.get(count)).getImage();
                    exerciseName=(img.get(count)).getName();
                    exerciseDes=(img.get(count)).getDescription();
                    exerciseVdo=(img.get(count)).getVdo();

                    txtDes.setText(exerciseDes);
                    txtName.setText(exerciseName);
//                    if(exerciseVdo != -1){
                        vdoView.setVisibility(View.VISIBLE);
//                        imgView.setVisibility(View.INVISIBLE);
                        vdoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+exerciseVdo));
                        vdoView.setOnPreparedListener (new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                mp.setLooping(true);
                            }
                        });
                        vdoView.start();
//                    }
//                    else{
//
//                        vdoView.setBackgroundResource(0);
//                        vdoView.setVisibility(View.INVISIBLE);
//                        imgView.setVisibility(View.VISIBLE);
//                        imgView.setBackgroundResource(exerciseImg);
//                        // Get the background, which has been compiled to an AnimationDrawable object.
//                        frameAnimation = (AnimationDrawable) imgView.getBackground();
//                        // Start the animation (looped playback by default).
//                        frameAnimation.start();

//                    }

                    start();
                }

            }
        }.start();

        time2 = new CountDownTimer(120000, 1000) {
            public void onTick(long millisUntilFinished) {
                txtA.setText("เวลากิจกรรม    :    "+String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))) + " นาที");
            }

            public void onFinish() {
                playBell();
                txtA.setText("เสร็จสิ้น!");

//                if(exerciseVdo == -1)
//                    frameAnimation.stop();
//                else
                    vdoView.stopPlayback();
                count++;
                Intent i1 = new Intent(Activity.this, Camera.class);
                i1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i1);
                User currentUser = UserManage.getCurrentUser();
                currentUser.addScore(1);

                updateActivity(img, currentUser);

                currentUser.save(context);
                currentUser.getDailyProgress().save(context,1);
                currentUser.getWeeklyProgress().save(context,0);

                makeSnackbar("ทำกิจกรรมสำเร็จ รับ 1 คะแนน!");
                UserServiceImp.getInstance().update(currentUser, new Callback<StatusDescription>() {
                    @Override
                    public void onResponse(retrofit.Response<StatusDescription> response, Retrofit retrofit) {
//                        Toast.makeText(getApplicationContext(), "สามารถอัปเดตข้อมูลไปยังเซิร์ฟเวอร์ได้", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        makeSnackbar("ไม่สามารถอัปเดตข้อมูลไปยังเซิร์ฟเวอร์ได้");
                    }
                });
            }
        }.start();

    }

    public void linkHome(View view)
    {
//        if(exerciseVdo == -1)
//            frameAnimation.stop();
//        else
            vdoView.stopPlayback();
        if(time1!=null){
            time1.cancel();
            time1=null;
        }
        if(time2!=null){
            time2.cancel();
            time2=null;
        }
        if(songPlayer.isPlaying()){
            songPlayer.stop();
        }
        // TODO update progress
        updatecancel();

        Intent i1 = new Intent(Activity.this, MainActivity.class);
        i1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i1);
        //sendBroadcast(i1);
        Intent i = new Intent(getBaseContext(), AlarmReceiver.class);

        Bundle b = new Bundle();
        b.putString("key", "first");
        i.putExtras(b);
        sendBroadcast(i);
        Cache.getInstance().putData("isCancelActivity", true);
        finish();
    }

    public void makeSnackbar(String text)
    {
        Snackbar.make(txtR, text, Snackbar.LENGTH_SHORT).show();
    }

    public static ActivityHandle getCurrentActivityHandle (){
        if (activityHandle!=null)
            return activityHandle;
        return null;
    }
    public void updatecancel(){
        User user = UserManage.getCurrentUser();
        if(user!=null){
            int cancel = user.getDailyProgress().getDeclination()+1;
            int total = user.getDailyProgress().getTotalActivity()+1;
            user.getDailyProgress().setDeclination(cancel);
            user.getDailyProgress().setTotalActivity(total);
            int cancelweek = user.getWeeklyProgress().getDeclination()+1;
            int totalweek = user.getWeeklyProgress().getTotalActivity()+1;
            user.getWeeklyProgress().setDeclination(cancelweek);
            user.getWeeklyProgress().setTotalActivity(totalweek);

            user.getDailyProgress().setExerciseTime(user.getDailyProgress().getExerciseTime()+(count));
            user.getWeeklyProgress().setExerciseTime(user.getWeeklyProgress().getExerciseTime()+(count));

            user.save(context);
            user.getDailyProgress().save(context,1);
            user.getWeeklyProgress().save(context,0);
            UserServiceImp.getInstance().update(user, new Callback<StatusDescription>() {
                @Override
                public void onResponse(retrofit.Response<StatusDescription> response, Retrofit retrofit) {
//                    Toast.makeText(getApplicationContext(), "อัปเดตข้อมูลไปยังเซิร์ฟเวอร์สำเร็จ", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Throwable t) {
                    makeSnackbar("ไม่สามารถอัปเดตข้อมูลไปยังเซิร์ฟเวอร์ได้");
                }
            });
        }
    }
    public void updateActivity(ArrayList<Posture> mode,User user){
        int numberofimg = mode.size();
        if(user!=null) {
            for (int i = 0; i < numberofimg; i++) {
                if ((mode.get(i)).getMode()==1){
                    user.getDailyProgress().setNeck(user.getDailyProgress().getNeck() + 1);
                    user.getWeeklyProgress().setNeck(user.getWeeklyProgress().getNeck() + 1);}
                else if((mode.get(i)).getMode()==2){
                    user.getDailyProgress().setShoulder(user.getDailyProgress().getShoulder() + 1);
                    user.getWeeklyProgress().setShoulder(user.getWeeklyProgress().getShoulder() + 1);}
                else if((mode.get(i)).getMode()==3){
                    user.getDailyProgress().setChestBack(user.getDailyProgress().getChestBack() + 1);
                    user.getWeeklyProgress().setChestBack(user.getWeeklyProgress().getChestBack() + 1);}
                else if((mode.get(i)).getMode() == 4) {
                    user.getDailyProgress().setWrist(user.getDailyProgress().getWrist() + 1);
                    user.getWeeklyProgress().setWrist(user.getWeeklyProgress().getWrist() + 1);}
                else if((mode.get(i)).getMode() == 5) {
                    user.getDailyProgress().setWaist(user.getDailyProgress().getWaist() + 1);
                    user.getWeeklyProgress().setWaist(user.getWeeklyProgress().getWaist() + 1);}
                else if((mode.get(i)).getMode()==6){
                    user.getDailyProgress().setHipLegCalf(user.getDailyProgress().getHipLegCalf() + 1);
                    user.getWeeklyProgress().setHipLegCalf(user.getWeeklyProgress().getHipLegCalf() + 1);}

            }
            user.getDailyProgress().setAcceptation(user.getDailyProgress().getAcceptation()+1);
            user.getDailyProgress().setTotalActivity(user.getDailyProgress().getTotalActivity()+1);
            user.getDailyProgress().setExerciseTime(user.getDailyProgress().getExerciseTime()+(count));
            user.getWeeklyProgress().setAcceptation(user.getWeeklyProgress().getAcceptation()+1);
            user.getWeeklyProgress().setTotalActivity(user.getWeeklyProgress().getTotalActivity()+1);
            user.getWeeklyProgress().setExerciseTime(user.getWeeklyProgress().getExerciseTime()+(count));
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
    public void playSong(){

        if(songPlayer != null){
            songPlayer.stop();
            songPlayer.release();
        }
        songPlayer = MediaPlayer.create(this,R.raw.exercise_sound);
        songPlayer.start();
    }
}
