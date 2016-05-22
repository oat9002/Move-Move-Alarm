package com.fatel.mamtv1;


import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
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
    private int mProgressStatus,mProgressStatusweek = 0;
    private Handler mHandler = new Handler();

    TextView timeFrac,timeFracweek;
    //private int countAccept = 6;
    //private int countTotal = 10;
    private int cirProgressstatus = 0;
    private int timePerPic,timePerPicweek = 1;
    private int cirProgressstatusweek = 0;
    private int timeperweek = 1;
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
        HistoryHelper mhistoryHelper = new HistoryHelper(getActivity());
        History history = mhistoryHelper.getHistoryUser(UserManage.getInstance(getActivity()).getCurrentIdUser());
        //History history = (History) Cache.getInstance().getData("userHistory");
        if(history != null && history.gettotal()==0){
            cirProgressstatus = 0;
            mProgressStatus = 0;
            mProgressStatusweek = 0;
            cirProgressstatusweek = 0;
        }
        else{
            cirProgressstatus = (history.getNumberOfAccept()*100)/history.gettotal();
            mProgressStatus = ((history.getNumberOfAccept() * timePerPic) * 100) / (history.gettotal() * timePerPic);
            cirProgressstatusweek = 99;
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


        //set progress bar in each days
        timeProgress.setProgress(mProgressStatus);
        timeProgressweek.setProgress(mProgressStatusweek);
        // Show the progress on TextView
        timeFrac.setText((history.getNumberOfAccept()*timePerPic)+"/"+(history.gettotal()*timePerPic)+"min");
        timeFrac.setText((0*timePerPic)+"/"+(0*timePerPic)+"min");
        timeFracweek.setText((0*timePerPicweek)+"/"+(0*timePerPicweek)+"min");
    return view;
    }


}
