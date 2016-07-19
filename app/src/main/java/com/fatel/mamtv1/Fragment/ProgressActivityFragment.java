package com.fatel.mamtv1.Fragment;


import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fatel.mamtv1.HistoryHelper;
import com.fatel.mamtv1.Model.History;
import com.fatel.mamtv1.R;
import com.fatel.mamtv1.UserManage;
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

    TextView timeFrac,timeFracweek;
    private int cirProgressstatus = 0;
    private int timePerPic = 1,timePerPicweek = 1;
    private int cirProgressstatusweek = 0;
    // statistic exercise
    private int numberofneck = 1,numberofshoulder = 1,numberofbreastback = 1,numberofwrist = 1,numberofwaist = 1,numberofleg = 1;
    private int numberofneckweek = 1,numberofshoulderweek = 1,numberofbreastbackweek = 1,numberofwristweek = 1,numberofwaistweek = 1,numberoflegweek = 1;
    private int barneck = 0,barshouler = 0,barbreastback = 0,barwrist = 0,barwaist = 0,barleg = 0;
    private int barneckweek = 0,barshoulerweek = 0,barbreastbackweek = 0,barwristweek = 0,barwaistweek = 0,barlegweek = 0;
    private ProgressBar progresstimeofneck,progresstimeofshoulder,progresstimeofbreastback,progresstimeofwrist,progresstimeofwaist,progresstimeofleg;
    private ProgressBar progresstimeofneckweek,progresstimeofshoulderweek,progresstimeofbreastbackweek,progresstimeofwristweek,progresstimeofwaistweek,progresstimeoflegweek;
    TextView numberfracneck,numberfracshoulder,numberfracbreastback,numberfracwrist,numberfracwaist,numberfracleg,
            numberfracneckweek,numberfracshoulderweek,numberfracbreastbackweek,numberfracwristweek,numberfracwaistweek,numberfraclegweek;
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
        History history = mhistoryHelper.getHistoryUser(UserManage.getInstance(getActivity()).getCurrentUser().getIdUser());
        //History history = (History) Cache.getInstance().getData("userHistory");
        if(history != null && history.gettotal()==0){
            cirProgressstatus = 10;
            cirProgressstatusweek = 70;
            mProgressStatus = 70;
            mProgressStatusweek = 50;
            barneck = 10;
            barshouler = 20;
            barbreastback = 30;
            barwrist = 40;
            barwaist = 50;
            barleg = 60;
            barneckweek = 70;
            barshoulerweek = 80;
            barbreastbackweek = 90;
            barwristweek = 90;
            barwaistweek = 90;
            barlegweek = 90;

        }
        else{
            cirProgressstatus = (history.getNumberOfAccept()*100)/history.gettotal();
            mProgressStatus = ((history.getNumberOfAccept() * timePerPic) * 100) / (history.gettotal() * timePerPic);
            cirProgressstatusweek = 99;
            mProgressStatusweek = 50;
            barneck = 100;
            barshouler = 0;
            barbreastback = 0;
            barwrist = 0;
            barwaist = 0;
            barleg = 0;
            barneckweek = 0;
            barshoulerweek = 0;
            barbreastbackweek = 0;
            barwristweek = 0;
            barwaistweek = 0;
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
        timeFracweek = (TextView) view.findViewById(R.id.timeFraction1);
        numberfracneck = (TextView) view.findViewById(R.id.timeFraction2);
        numberfracshoulder = (TextView) view.findViewById(R.id.timeFraction3);
        numberfracbreastback = (TextView) view.findViewById(R.id.timeFraction4);
        numberfracwrist = (TextView) view.findViewById(R.id.timeFraction5);
        numberfracwaist = (TextView) view.findViewById(R.id.timeFraction6);
        numberfracleg = (TextView) view.findViewById(R.id.timeFraction7);
        numberfracneckweek = (TextView) view.findViewById(R.id.timeFraction8);
        numberfracshoulderweek = (TextView) view.findViewById(R.id.timeFraction9);
        numberfracbreastbackweek = (TextView) view.findViewById(R.id.timeFraction10);
        numberfracwristweek = (TextView) view.findViewById(R.id.timeFraction11);
        numberfracwaistweek = (TextView) view.findViewById(R.id.timeFraction12);
        numberfraclegweek = (TextView) view.findViewById(R.id.timeFraction13);
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
        progresstimeofneck.setProgress(barneck);
        progresstimeofshoulder.setProgress(barshouler);
        progresstimeofbreastback.setProgress(barbreastback);
        progresstimeofwrist.setProgress(barwrist);
        progresstimeofwaist.setProgress(barwaist);
        progresstimeofleg.setProgress(barleg);
        progresstimeofneckweek.setProgress(barneckweek);
        progresstimeofshoulderweek.setProgress(barshoulerweek);
        progresstimeofbreastbackweek.setProgress(barbreastbackweek);
        progresstimeofwristweek.setProgress(barwristweek);
        progresstimeofwaistweek.setProgress(barwaistweek);
        progresstimeoflegweek.setProgress(barlegweek);

        // Show the progress on TextView
        //timeFrac.setText((history.getNumberOfAccept()*timePerPic)+"/"+(history.gettotal()*timePerPic)+"min");
        timeFrac.setText((10*timePerPic)+" นาที");
        timeFracweek.setText((10*timePerPicweek)+" นาที");
        numberfracneck.setText((1*numberofneck)+" ครั้ง");
        numberfracshoulder.setText((1*numberofshoulder)+" ครั้ง");
        numberfracbreastback.setText((1*numberofbreastback)+" ครั้ง");
        numberfracwrist.setText((1*numberofwrist)+" ครั้ง");
        numberfracwaist.setText((1*numberofwaist)+" ครั้ง");
        numberfracleg.setText((1*numberofleg)+" ครั้ง");
        numberfracneckweek.setText((1*numberofneckweek)+" ครั้ง");
        numberfracshoulderweek.setText((1*numberofshoulderweek)+" ครั้ง");
        numberfracbreastbackweek.setText((1*numberofbreastbackweek)+" ครั้ง");
        numberfracwristweek.setText((1*numberofwristweek)+" ครั้ง");
        numberfracwaistweek.setText((1*numberofwaistweek)+" ครั้ง");
        numberfraclegweek.setText((1*numberoflegweek)+" ครั้ง");
    return view;
    }


}
