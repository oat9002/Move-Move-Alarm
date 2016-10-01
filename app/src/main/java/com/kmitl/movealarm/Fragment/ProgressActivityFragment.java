package com.kmitl.movealarm.Fragment;


import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kmitl.movealarm.Model.User;
import com.kmitl.movealarm.R;
import com.kmitl.movealarm.Service.UserManage;
import com.lylc.widget.circularprogressbar.CircularProgressBar;
import com.lylc.widget.circularprogressbar.CircularProgressBar.ProgressAnimationListener;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProgressActivityFragment extends Fragment {
    //add for progress bar
    //private static final int PROGRESS = 0x1;
    //private ProgressBar timeProgress,timeProgressweek;
    //private int mProgressStatus = 0,mProgressStatusweek = 0;
    //private Handler mHandler = new Handler();

    TextView timeFrac, timeFracWeek;
    private int cirProgressstatus = 0;
    private int timePerPic = 0,timePerPicweek = 0;
    private int cirProgressstatusweek = 0;
    // statistic exercise
    private int numberofneck = 0,numberofshoulder = 0,numberofbreastback = 0,numberofwrist = 0,numberofwaist = 0,numberofleg = 0;
    private int numberofneckweek = 0,numberofshoulderweek = 0,numberofbreastbackweek = 0,numberofwristweek = 0,numberofwaistweek = 0,numberoflegweek = 0;
    private int barNeck = 0, barShoulder = 0, barChestBack = 0, barWrist = 0,barwaist = 0, barHipLegCalf = 0;
    private int barNeckWeek = 0, barShoulderWeek = 0, barChestBackWeek = 0, barWristWeek = 0, barWaistWeek = 0,barHipLegCalfWeek = 0;
    private ProgressBar progresstimeofneck,progresstimeofshoulder,progresstimeofbreastback,progresstimeofwrist,progresstimeofwaist,progresstimeofleg;
    private ProgressBar progresstimeofneckweek,progresstimeofshoulderweek,progresstimeofbreastbackweek,progresstimeofwristweek,progresstimeofwaistweek,progresstimeoflegweek;
    TextView numberFracNeck, numberFracShoulder, numberFracChestBack, numberFracWrist, numberFracWaist, numberFracHipLegCalf,
            numberFracNeckWeek, numberFracShoulderWeek, numberFracChestBackWeek, numberFracWristWeek, numberFracWaistWeek, numberFracHipLegCalfWeek;
    public static ProgressActivityFragment newInstance() {
        // Required empty public constructor
        ProgressActivityFragment fragment = new ProgressActivityFragment();
        return fragment;
    }
    public ProgressActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_progress_activity, container, false);
        //cal % of circular progress
        //edit
        //call volley get data
        User user = UserManage.getCurrentUser();
        if(user!=null){
            int totalExerciseTime_daily =  user.getDailyProgress().getNeck() + user.getDailyProgress().getShoulder() +
                                           user.getDailyProgress().getBody() + user.getDailyProgress().getArm() +
                                           user.getDailyProgress().getBreast_belly_back() + user.getDailyProgress().getFeet_leg_shin_calf();
            int totalExerciseTime_week = user.getWeeklyProgress().getNeck() + user.getWeeklyProgress().getShoulder() +
                                         user.getWeeklyProgress().getBody() + user.getWeeklyProgress().getArm() +
                                         user.getWeeklyProgress().getBreast_belly_back() + user.getWeeklyProgress().getFeet_leg_shin_calf();
            int totalActivity_daily = user.getDailyProgress().getTotalActivity();
            int totalActivity_week = user.getWeeklyProgress().getTotalActivity();
            if(totalExerciseTime_daily == 0  ) {
                totalExerciseTime_daily = 1;
            }
            if(totalExerciseTime_week == 0) {
                totalExerciseTime_week = 1;
            }
            if(totalActivity_daily == 0) {
                totalActivity_daily = 1;
            }
            if(totalActivity_week == 0) {
                totalActivity_week = 1;
            }
            cirProgressstatus = (user.getDailyProgress().getAcceptation() * 100) / totalActivity_daily;
            cirProgressstatusweek = (user.getWeeklyProgress().getAcceptation() * 100) / totalActivity_week;
            barNeck = (user.getDailyProgress().getNeck() * 100) / totalExerciseTime_daily;
            barShoulder = (user.getDailyProgress().getShoulder() * 100) / totalExerciseTime_daily;
            barChestBack = (user.getDailyProgress().getBody() * 100) / totalExerciseTime_daily;
            barWrist = (user.getDailyProgress().getArm() * 100) / totalExerciseTime_daily;
            barwaist = (user.getDailyProgress().getBreast_belly_back() * 100) / totalExerciseTime_daily;
            barHipLegCalf = (user.getDailyProgress().getFeet_leg_shin_calf() * 100) / totalExerciseTime_daily;
            barNeckWeek = (user.getWeeklyProgress().getNeck() * 100) / totalExerciseTime_week;
            barShoulderWeek = (user.getWeeklyProgress().getShoulder() * 100) / totalExerciseTime_week;
            barChestBackWeek = (user.getWeeklyProgress().getBody() * 100) / totalExerciseTime_week;
            barWristWeek = (user.getWeeklyProgress().getArm() * 100) / totalExerciseTime_week;
            barWaistWeek = (user.getWeeklyProgress().getBreast_belly_back() * 100) / totalExerciseTime_week;
            barHipLegCalfWeek = (user.getWeeklyProgress().getFeet_leg_shin_calf() * 100) / totalExerciseTime_week;
            timePerPic = user.getDailyProgress().getExerciseTime();
            timePerPicweek = user.getWeeklyProgress().getExerciseTime();
            numberofneck = user.getDailyProgress().getNeck();
            numberofshoulder = user.getDailyProgress().getShoulder();
            numberofbreastback = user.getDailyProgress().getBody();
            numberofwrist = user.getDailyProgress().getArm();
            numberofwaist = user.getDailyProgress().getBreast_belly_back();
            numberofleg = user.getDailyProgress().getFeet_leg_shin_calf();
            numberofneckweek = user.getWeeklyProgress().getNeck();
            numberofshoulderweek = user.getWeeklyProgress().getShoulder();
            numberofbreastbackweek = user.getWeeklyProgress().getBody();
            numberofwristweek = user.getWeeklyProgress().getArm();
            numberofwaistweek = user.getWeeklyProgress().getBreast_belly_back();
            numberoflegweek = user.getWeeklyProgress().getFeet_leg_shin_calf();
        }

        final CircularProgressBar c2 = (CircularProgressBar) view.findViewById(R.id.circularprogressbar2);
        c2.animateProgressTo(0, cirProgressstatus, new ProgressAnimationListener() {

            @Override
            public void onAnimationStart() {
            }

            @Override
            public void onAnimationProgress(int progress) {
                c2.setTitle(progress + "%");
            }

            @Override
            public void onAnimationFinish() {
                //c2.setSubTitle("accept of activity per day");
            }
        });

        final CircularProgressBar c3 = (CircularProgressBar) view.findViewById(R.id.circularprogressbar3);
        c3.animateProgressTo(0, cirProgressstatusweek, new ProgressAnimationListener() {

            @Override
            public void onAnimationStart() {
            }

            @Override
            public void onAnimationProgress(int progress) {
                c3.setTitle(progress + "%");
            }

            @Override
            public void onAnimationFinish() {
                //c3.setSubTitle("accept of activity per week");
            }
        });

        //add for progressbar
        timeFrac = (TextView) view.findViewById(R.id.timeFraction);
        timeFracWeek = (TextView) view.findViewById(R.id.timeFraction1);
        numberFracNeck = (TextView) view.findViewById(R.id.timeFraction2);
        numberFracShoulder = (TextView) view.findViewById(R.id.timeFraction3);
        numberFracChestBack = (TextView) view.findViewById(R.id.timeFraction4);
        numberFracWrist = (TextView) view.findViewById(R.id.timeFraction5);
        numberFracWaist = (TextView) view.findViewById(R.id.timeFraction6);
        numberFracHipLegCalf = (TextView) view.findViewById(R.id.timeFraction7);
        numberFracNeckWeek = (TextView) view.findViewById(R.id.timeFraction8);
        numberFracShoulderWeek = (TextView) view.findViewById(R.id.timeFraction9);
        numberFracChestBackWeek = (TextView) view.findViewById(R.id.timeFraction10);
        numberFracWristWeek = (TextView) view.findViewById(R.id.timeFraction11);
        numberFracWaistWeek = (TextView) view.findViewById(R.id.timeFraction12);
        numberFracHipLegCalfWeek = (TextView) view.findViewById(R.id.timeFraction13);
        progresstimeofneck = (ProgressBar) view.findViewById(R.id.barTime2);
        progresstimeofshoulder = (ProgressBar) view.findViewById(R.id.barTime3);
        progresstimeofbreastback = (ProgressBar) view.findViewById(R.id.barTime4);
        progresstimeofwrist = (ProgressBar) view.findViewById(R.id.barTime5);
        progresstimeofwaist = (ProgressBar) view.findViewById(R.id.barTime6);
        progresstimeofleg = (ProgressBar) view.findViewById(R.id.barTime7);
        progresstimeofneckweek = (ProgressBar) view.findViewById(R.id.barTime8);
        progresstimeofshoulderweek = (ProgressBar) view.findViewById(R.id.barTime9);
        progresstimeofbreastbackweek = (ProgressBar) view.findViewById(R.id.barTime10);
        progresstimeofwristweek = (ProgressBar) view.findViewById(R.id.barTime11);
        progresstimeofwaistweek = (ProgressBar) view.findViewById(R.id.barTime12);
        progresstimeoflegweek = (ProgressBar) view.findViewById(R.id.barTime13);

        //set progress bar in each days
        progresstimeofneck.setProgress(barNeck);
        progresstimeofshoulder.setProgress(barShoulder);
        progresstimeofbreastback.setProgress(barChestBack);
        progresstimeofwrist.setProgress(barWrist);
        progresstimeofwaist.setProgress(barwaist);
        progresstimeofleg.setProgress(barHipLegCalf);
        progresstimeofneckweek.setProgress(barNeckWeek);
        progresstimeofshoulderweek.setProgress(barShoulderWeek);
        progresstimeofbreastbackweek.setProgress(barChestBackWeek);
        progresstimeofwristweek.setProgress(barWristWeek);
        progresstimeofwaistweek.setProgress(barWaistWeek);
        progresstimeoflegweek.setProgress(barHipLegCalfWeek);

        // Show the progress on TextView
        timeFrac.setText(timePerPic+" นาที");
        timeFracWeek.setText(timePerPicweek +" นาที");
        numberFracNeck.setText(numberofneck+" ครั้ง");
        numberFracShoulder.setText(numberofshoulder+" ครั้ง");
        numberFracChestBack.setText(numberofbreastback+" ครั้ง");
        numberFracWrist.setText(numberofwrist+" ครั้ง");
        numberFracWaist.setText(numberofwaist+" ครั้ง");
        numberFracHipLegCalf.setText(numberofleg+" ครั้ง");
        numberFracNeckWeek.setText(numberofneckweek+" ครั้ง");
        numberFracShoulderWeek.setText(numberofshoulderweek+" ครั้ง");
        numberFracChestBackWeek.setText(numberofbreastbackweek+" ครั้ง");
        numberFracWristWeek.setText(numberofwristweek+" ครั้ง");
        numberFracWaistWeek.setText(numberofwaistweek+" ครั้ง");
        numberFracHipLegCalfWeek.setText(numberoflegweek+" ครั้ง");
    return view;
    }


}
