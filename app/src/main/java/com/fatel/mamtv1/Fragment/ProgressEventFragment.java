package com.fatel.mamtv1.Fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fatel.mamtv1.Historygroup;
import com.fatel.mamtv1.R;
import com.fatel.mamtv1.UserManage;
import com.lylc.widget.circularprogressbar.CircularProgressBar;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProgressEventFragment extends Fragment {
    //add for progress bar
    // private static final int PROGRESS = 0x1;
    private ProgressBar timeProgressE;
    private int mProgressStatusE = 0;
    private Handler mHandler = new Handler();

    TextView timeFracE;
    private int cirProgressstatusE = 0;
    private int timePerPicE = 1;
    // statistic exercise
    private int numberofneckweek = 1,numberofshoulderweek = 1,numberofbreastbackweek = 1,numberofwristweek = 1,numberofwaistweek = 1,numberoflegweek = 1;
    private int barneckweek = 0,barshoulerweek = 0,barbreastbackweek = 0,barwristweek = 0,barwaistweek = 0,barlegweek = 0;
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
        //add for progressbar
        timeProgressE = (ProgressBar) view.findViewById(R.id.barTimeE);
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
        //cal % of circular progress
        //edit
        //HistorygroupHelper mhistorygroupHelper = new HistorygroupHelper(getActivity());
        //Log.i("numgroup",""+UserManage.getInstance(getActivity()).getCurrentIdGroup());
        Historygroup historygroup = Historygroup.findHistorygroup(UserManage.getInstance(getActivity()).getCurrentUser().getIdGroup(),getActivity());
        if(historygroup==null){
            cirProgressstatusE = 50;
            mProgressStatusE = 0;
            barneckweek = 70;
            barshoulerweek = 80;
            barbreastbackweek = 90;
            barwristweek = 90;
            barwaistweek = 90;
            barlegweek = 90;
            //set progress bar in each days
            timeProgressE.setProgress(mProgressStatusE);
            // Show the progress on TextView
            timeFracE.setText((0 * timePerPicE) + "/" + (0 * timePerPicE) + " นาที");
            progresstimeofneckweek.setProgress(barneckweek);
            progresstimeofshoulderweek.setProgress(barshoulerweek);
            progresstimeofbreastbackweek.setProgress(barbreastbackweek);
            progresstimeofwristweek.setProgress(barwristweek);
            progresstimeofwaistweek.setProgress(barwaistweek);
            progresstimeoflegweek.setProgress(barlegweek);
            numberfracneckweek.setText((1 * numberofneckweek) + " ครั้ง");
            numberfracshoulderweek.setText((1 * numberofshoulderweek) + " ครั้ง");
            numberfracbreastbackweek.setText((1 * numberofbreastbackweek) + " ครั้ง");
            numberfracwristweek.setText((1 * numberofwristweek) + " ครั้ง");
            numberfracwaistweek.setText((1 * numberofwaistweek) + " ครั้ง");
            numberfraclegweek.setText((1 * numberoflegweek) + " ครั้ง");

        }
        else if(historygroup!=null&&historygroup.gettotal()==0){
            cirProgressstatusE = 0;
            mProgressStatusE = 0;
            barneckweek = 70;
            barshoulerweek = 80;
            barbreastbackweek = 90;
            barwristweek = 90;
            barwaistweek = 90;
            barlegweek = 90;
            //set progress bar in each days
            timeProgressE.setProgress(mProgressStatusE);
            // Show the progress on TextView
            timeFracE.setText((0 * timePerPicE) + "/" + (0 * timePerPicE) + " นาที");
            progresstimeofneckweek.setProgress(barneckweek);
            progresstimeofshoulderweek.setProgress(barshoulerweek);
            progresstimeofbreastbackweek.setProgress(barbreastbackweek);
            progresstimeofwristweek.setProgress(barwristweek);
            progresstimeofwaistweek.setProgress(barwaistweek);
            progresstimeoflegweek.setProgress(barlegweek);
            numberfracneckweek.setText((1 * numberofneckweek) + " ครั้ง");
            numberfracshoulderweek.setText((1 * numberofshoulderweek) + " ครั้ง");
            numberfracbreastbackweek.setText((1 * numberofbreastbackweek) + " ครั้ง");
            numberfracwristweek.setText((1 * numberofwristweek) + " ครั้ง");
            numberfracwaistweek.setText((1 * numberofwaistweek) + " ครั้ง");
            numberfraclegweek.setText((1 * numberoflegweek) + " ครั้ง");
        }
        else{
            cirProgressstatusE = (historygroup.getNumberOfAccept()*100)/historygroup.gettotal();
            mProgressStatusE = ((historygroup.getNumberOfAccept() * timePerPicE) * 100) / (historygroup.gettotal() * timePerPicE);
            barneckweek = 70;
            barshoulerweek = 80;
            barbreastbackweek = 90;
            barwristweek = 90;
            barwaistweek = 90;
            barlegweek = 90;
            //set progress bar in each days
            timeProgressE.setProgress(mProgressStatusE);
            // Show the progress on TextView
            timeFracE.setText((historygroup.getNumberOfAccept() * timePerPicE) + "/" + (historygroup.gettotal() * timePerPicE) + " นาที");
            progresstimeofneckweek.setProgress(barneckweek);
            progresstimeofshoulderweek.setProgress(barshoulerweek);
            progresstimeofbreastbackweek.setProgress(barbreastbackweek);
            progresstimeofwristweek.setProgress(barwristweek);
            progresstimeofwaistweek.setProgress(barwaistweek);
            progresstimeoflegweek.setProgress(barlegweek);
            numberfracneckweek.setText((1*numberofneckweek)+" ครั้ง");
            numberfracshoulderweek.setText((1*numberofshoulderweek)+" ครั้ง");
            numberfracbreastbackweek.setText((1*numberofbreastbackweek)+" ครั้ง");
            numberfracwristweek.setText((1*numberofwristweek)+" ครั้ง");
            numberfracwaistweek.setText((1*numberofwaistweek)+" ครั้ง");
            numberfraclegweek.setText((1*numberoflegweek)+" ครั้ง");
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





        return view;
    }


}
