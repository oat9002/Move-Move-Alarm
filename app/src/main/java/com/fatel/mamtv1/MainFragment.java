package com.fatel.mamtv1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.Calendar;


import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends android.support.v4.app.Fragment {
    private DBAlarmHelper mAlarmHelper;
    CircleImageView propic;
    TextView score;
    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mAlarmHelper = new DBAlarmHelper(getActivity());
        setUser(view);
        setTextAlarm(view, mAlarmHelper);
        propic = (CircleImageView)view.findViewById(R.id.profile_image_f);
        String tempid = UserManage.getInstance((Context) Cache.getInstance().getData("MainActivityContext")).getCurrentUser().getFacebookID();
        if(!tempid.equals("0.0")) {
            if (!tempid.equals("0")) {
                if(!(tempid.equals("fb0.0"))) {
                    Glide.with(this).load("https://graph.facebook.com/" + tempid + "/picture?type=large").into(propic);
                }
            }
        }
        score = (TextView)view.findViewById(R.id.scoreNum);
        score.setText(UserManage.getInstance((Context) Cache.getInstance().getData("MainActivityContext")).getCurrentUser().getScore() + "");
        return view;
    }
    public void setUser(View view){
        TextView user = (TextView) view.findViewById(R.id.userinmain);
        user.setText(UserManage.getInstance((Context) Cache.getInstance().getData("MainActivityContext")).getCurrentUser().getFacebookFirstName());
    }

    public void setTextAlarm(View view,DBAlarmHelper alarmHelper){
        TextView hrstart = (TextView)view.findViewById(R.id.starthr);
        TextView minstart = (TextView)view.findViewById(R.id.startmin);
        TextView hrstop = (TextView)view.findViewById(R.id.hrstop);
        TextView minstop = (TextView)view.findViewById(R.id.minstop);
        TextView intervalstart = (TextView)view.findViewById(R.id.intervalstart);
        TextView intervalstop = (TextView)view.findViewById(R.id.intervalstop);
        TextView freq = (TextView) view.findViewById(R.id.freqinmain);
        if(alarmHelper.checkdata()==1){
            Alarm alarm = new Alarm();
            alarm = alarmHelper.getAlarm();
            hrstart.setText(alarm.getStarthr());
            minstart.setText(alarm.getStartmin());
            hrstop.setText(alarm.getStophr());
            minstop.setText(alarm.getStopmin());
            intervalstart.setText(alarm.getStartinterval());
            intervalstop.setText(alarm.getStopinterval());
            freq.setText(alarm.getFrq() + " นาที");
            freq.setTextSize(20);
        }
        else{

            hrstart.setText("--");
            minstart.setText("--");
            hrstop.setText("--");
            minstop.setText("--");
            intervalstart.setText("");
            intervalstop.setText("");
            freq.setText("ไม่ได้ตั้งค่า");
        }
    }
}
