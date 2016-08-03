package com.fatel.mamtv1.Fragment;


import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fatel.mamtv1.Helper.HistoryHelper;
import com.fatel.mamtv1.Model.History;
import com.fatel.mamtv1.Model.User;
import com.fatel.mamtv1.R;
import com.fatel.mamtv1.Service.UserManage;
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
    private ProgressBar timeProgress,timeProgressweek;
    private int mProgressStatus = 0,mProgressStatusweek = 0;
    private Handler mHandler = new Handler();

    TextView timeFrac, timeFracWeek;
    private int cirProgressstatus = 0;
    private int timePerPic = 1,timePerPicweek = 1;
    private int cirProgressstatusweek = 0;
    // statistic exercise
    private int numberofneck = 1,numberofshoulder = 1,numberofbreastback = 1,numberofwrist = 1,numberofwaist = 1,numberofleg = 1;
    private int numberofneckweek = 1,numberofshoulderweek = 1,numberofbreastbackweek = 1,numberofwristweek = 1,numberofwaistweek = 1,numberoflegweek = 1;
    private int barNeck = 0, barShoulder = 0, barChestBack = 0, barWrist = 0,barwaist = 0, barHipLegCalfWeek = 0;
    private int barNeckWeek = 0, barShoulderWeek = 0, barChestBackWeek = 0, barWristWeek = 0, barWaistWeek = 0,barlegweek = 0;
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

        //
        HistoryHelper mhistoryHelper = new HistoryHelper(getActivity());
        History history = mhistoryHelper.getHistoryUser(UserManage.getInstance(getActivity()).getCurrentUser().getId());
        //History history = (History) Cache.getInstance().getData("userHistory");
        User user = UserManage.getCurrentUser();
        if(user!=null){
            int totalExerciseTime_daily =  user.getDailyProgress().getNeck() + user.getDailyProgress().getShoulder() +
                                           user.getDailyProgress().getChestBack() + user.getDailyProgress().getWrist() +
                                           user.getDailyProgress().getWaist() + user.getDailyProgress().getHipLegCalf();
            int totalExerciseTime_week = user.getWeeklyProgress().getNeck() + user.getWeeklyProgress().getShoulder() +
                                         user.getWeeklyProgress().getChestBack() + user.getWeeklyProgress().getWrist() +
                                         user.getWeeklyProgress().getWaist() + user.getWeeklyProgress().getHipLegCalf();
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
            barChestBack = (user.getDailyProgress().getChestBack() * 100) / totalExerciseTime_daily;
            barWrist = (user.getDailyProgress().getWrist() * 100) / totalExerciseTime_daily;
            barwaist = (user.getDailyProgress().getWaist() * 100) / totalExerciseTime_daily;
            barHipLegCalfWeek = (user.getDailyProgress().getHipLegCalf() * 100) / totalExerciseTime_daily;
            barNeckWeek = (user.getWeeklyProgress().getNeck() * 100) / totalExerciseTime_week;
            barShoulderWeek = (user.getWeeklyProgress().getShoulder() * 100) / totalExerciseTime_week;
            barChestBackWeek = (user.getWeeklyProgress().getChestBack() * 100) / totalExerciseTime_week;
            barWristWeek = (user.getWeeklyProgress().getWrist() * 100) / totalExerciseTime_week;
            barWaistWeek = (user.getWeeklyProgress().getWaist() * 100) / totalExerciseTime_week;
            barHipLegCalfWeek = (user.getWeeklyProgress().getWaist() * 100) / totalExerciseTime_week;

        }
        else{
            cirProgressstatus = 0;
            mProgressStatus = 0;
            cirProgressstatusweek = 0;
            mProgressStatusweek = 0;
            barNeck = 0;
            barShoulder = 0;
            barChestBack = 0;
            barWrist = 0;
            barwaist = 0;
            barHipLegCalfWeek = 0;
            barNeckWeek = 0;
            barShoulderWeek = 0;
            barChestBackWeek = 0;
            barWristWeek = 0;
            barWaistWeek = 0;
            barlegweek = 0;
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
        timeProgress = (ProgressBar) view.findViewById(R.id.barTime);
        timeFrac = (TextView) view.findViewById(R.id.timeFraction);
        timeProgressweek = (ProgressBar) view.findViewById(R.id.barTime1);
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
        timeProgress.setProgress(mProgressStatus);
        timeProgressweek.setProgress(mProgressStatusweek);
        progresstimeofneck.setProgress(barNeck);
        progresstimeofshoulder.setProgress(barShoulder);
        progresstimeofbreastback.setProgress(barChestBack);
        progresstimeofwrist.setProgress(barWrist);
        progresstimeofwaist.setProgress(barwaist);
        progresstimeofleg.setProgress(barHipLegCalfWeek);
        progresstimeofneckweek.setProgress(barNeckWeek);
        progresstimeofshoulderweek.setProgress(barShoulderWeek);
        progresstimeofbreastbackweek.setProgress(barChestBackWeek);
        progresstimeofwristweek.setProgress(barWristWeek);
        progresstimeofwaistweek.setProgress(barWaistWeek);
        progresstimeoflegweek.setProgress(barlegweek);

        // Show the progress on TextView
        //timeFrac.setText((history.getNumberOfAccept()*timePerPic)+"/"+(history.getTotal()*timePerPic)+"min");
        timeFrac.setText((user.getDailyProgress().getAcceptation())+" นาที");
        timeFracWeek.setText((user.getWeeklyProgress().getAcceptation())+" นาที");
        numberFracNeck.setText((user.getDailyProgress().getNeck())+" ครั้ง");
        numberFracShoulder.setText((user.getDailyProgress().getShoulder())+" ครั้ง");
        numberFracChestBack.setText((user.getDailyProgress().getChestBack())+" ครั้ง");
        numberFracWrist.setText((user.getDailyProgress().getWrist())+" ครั้ง");
        numberFracWaist.setText((user.getDailyProgress().getWaist())+" ครั้ง");
        numberFracHipLegCalf.setText((user.getDailyProgress().getHipLegCalf())+" ครั้ง");
        numberFracNeckWeek.setText((user.getWeeklyProgress().getNeck())+" ครั้ง");
        numberFracShoulderWeek.setText((user.getWeeklyProgress().getShoulder())+" ครั้ง");
        numberFracChestBackWeek.setText((user.getWeeklyProgress().getChestBack())+" ครั้ง");
        numberFracWristWeek.setText((user.getWeeklyProgress().getWrist())+" ครั้ง");
        numberFracWaistWeek.setText((user.getWeeklyProgress().getWaist())+" ครั้ง");
        numberFracHipLegCalfWeek.setText((user.getWeeklyProgress().getHipLegCalf())+" ครั้ง");
    return view;
    }


}
