package com.kmitl.movealarm.Fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kmitl.movealarm.Model.Group;
import com.kmitl.movealarm.R;
import com.kmitl.movealarm.Service.Cache;
import com.kmitl.movealarm.Service.MyApplication;
import com.kmitl.movealarm.Service.UserManage;
import com.lylc.widget.circularprogressbar.CircularProgressBar;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProgressEventFragment extends Fragment {
    //add for progress bar
    // private static final int PROGRESS = 0x1;
    private Handler mHandler = new Handler();

    TextView timeFracE;
    private int cirProgressstatusE = 0;
    // statistic exercise
    private int timePerPicE = 0;
    private int numberofneckweek = 0,numberofshoulderweek = 0,numberofbreastbackweek = 0,numberofwristweek = 0,numberofwaistweek = 0,numberoflegweek = 0;
    private int barneckweek = 0, barshoulderweek = 0, barchestback = 0,barwristweek = 0,barwaistweek = 0, barHipLegCalfweek = 0;
    private ProgressBar progresstimeofneckweek,progresstimeofshoulderweek,progresstimeofbreastbackweek,progresstimeofwristweek,progresstimeofwaistweek,progresstimeoflegweek;
    TextView numberfracneckweek,numberfracshoulderweek,numberfracbreastbackweek,numberfracwristweek,numberfracwaistweek,numberfraclegweek;
    public static ProgressEventFragment newInstance() {
        // Required empty public constructor
        ProgressEventFragment fragment = new  ProgressEventFragment();
        return fragment;
    }
    public ProgressEventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_progress_event, container, false);
        //cal % of circular progress
        //edit
        //Group groupuser = (Group) Cache.getInstance().getData("groupData");
        Group groupuser = Group.find(UserManage.getCurrentUser().getGroupId(), MyApplication.getAppContext());

        if(groupuser != null){
            int totalExerciseTime_week = groupuser.getProgress().getNeck()+
                    groupuser.getProgress().getShoulder()+groupuser.getProgress().getChestBack()+
                    groupuser.getProgress().getWrist()+groupuser.getProgress().getWaist()+
                    groupuser.getProgress().getHipLegCalf();
            int totalActivity_week = groupuser.getProgress().getTotalActivity();
            if(totalExerciseTime_week == 0){
                totalExerciseTime_week = 1;
            }
            if(totalActivity_week == 0){
                totalActivity_week = 1;
            }
            cirProgressstatusE = (groupuser.getProgress().getAcceptation()*100)/totalActivity_week;
            barneckweek = (groupuser.getProgress().getNeck()*100)/totalExerciseTime_week;
            barshoulderweek = (groupuser.getProgress().getShoulder()*100)/totalExerciseTime_week;
            barchestback = (groupuser.getProgress().getChestBack()*100)/totalExerciseTime_week;;
            barwristweek = (groupuser.getProgress().getWrist()*100)/totalExerciseTime_week;
            barwaistweek = (groupuser.getProgress().getWaist()*100)/totalExerciseTime_week;
            barHipLegCalfweek = (groupuser.getProgress().getHipLegCalf()*100)/totalExerciseTime_week;
            timePerPicE = groupuser.getProgress().getExerciseTime();
            numberofneckweek = groupuser.getProgress().getNeck();
            numberofshoulderweek = groupuser.getProgress().getChestBack();
            numberofbreastbackweek = groupuser.getProgress().getChestBack();
            numberofwristweek = groupuser.getProgress().getWrist();
            numberofwaistweek = groupuser.getProgress().getWaist();
            numberoflegweek = groupuser.getProgress().getHipLegCalf();
        }

        final CircularProgressBar cE = (CircularProgressBar) view.findViewById(R.id.circularprogressbarE);
        cE.animateProgressTo(0, cirProgressstatusE, new CircularProgressBar.ProgressAnimationListener() {

            @Override
            public void onAnimationStart() {
            }

            @Override
            public void onAnimationProgress(int progress) {cE.setTitle(progress + "%");
            }

            @Override
            public void onAnimationFinish() {
                //cE.setSubTitle("accept of event");
            }
        });

        //add for progressbar
        timeFracE = (TextView) view.findViewById(R.id.timeFractionE);
        numberfracneckweek = (TextView) view.findViewById(R.id.etimeFraction2);
        numberfracshoulderweek = (TextView) view.findViewById(R.id.etimeFraction3);
        numberfracbreastbackweek = (TextView) view.findViewById(R.id.etimeFraction4);
        numberfracwristweek = (TextView) view.findViewById(R.id.etimeFraction5);
        numberfracwaistweek = (TextView) view.findViewById(R.id.etimeFraction6);
        numberfraclegweek = (TextView) view.findViewById(R.id.etimeFraction7);
        progresstimeofneckweek = (ProgressBar) view.findViewById(R.id.ebarTime2);
        progresstimeofshoulderweek = (ProgressBar) view.findViewById(R.id.ebarTime3);
        progresstimeofbreastbackweek = (ProgressBar) view.findViewById(R.id.ebarTime4);
        progresstimeofwristweek = (ProgressBar) view.findViewById(R.id.ebarTime5);
        progresstimeofwaistweek = (ProgressBar) view.findViewById(R.id.ebarTime6);
        progresstimeoflegweek = (ProgressBar) view.findViewById(R.id.ebarTime7);

        //set progress bar in each days
        // Show the progress on TextView

        progresstimeofneckweek.setProgress(barneckweek);
        progresstimeofshoulderweek.setProgress(barshoulderweek);
        progresstimeofbreastbackweek.setProgress(barchestback);
        progresstimeofwristweek.setProgress(barwristweek);
        progresstimeofwaistweek.setProgress(barwaistweek);
        progresstimeoflegweek.setProgress(barHipLegCalfweek);
        timeFracE.setText(timePerPicE + " นาที");
        numberfracneckweek.setText(numberofneckweek + " ครั้ง");
        numberfracshoulderweek.setText(numberofshoulderweek + " ครั้ง");
        numberfracbreastbackweek.setText(numberofbreastbackweek + " ครั้ง");
        numberfracwristweek.setText(numberofwristweek + " ครั้ง");
        numberfracwaistweek.setText(numberofwaistweek + " ครั้ง");
        numberfraclegweek.setText(numberoflegweek + " ครั้ง");
        return view;
    }
}
