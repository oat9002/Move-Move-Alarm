package com.fatel.mamtv1;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Administrator on 31/10/2558.
 */
public class ActivityHandle {

    int[] modeSelect = new int[] {-1,-1};
    int modeCount=0;


    public ActivityHandle(Context context){
        //use selected mode from alarm

        modeRandom(getMode(context));

    }
    private void modeRandom(int[] mode){

        int x;
        int i=0;
        while (i<2){
            x=(int)(Math.random() * (modeCount));
            modeSelect[i]=mode[x];
            i++;
        }

    }
    private int[] getMode(Context context){
        DBAlarmHelper mAlarmHelper= new DBAlarmHelper(context);
        Alarm alarm = mAlarmHelper.getAlarm();
        int modeDB[] = {0,0,0,0,0,0};
        for(int i=0;i<6;i++)
        {
            if(Integer.parseInt(alarm.getMode().substring(i,i+1))==1){
                modeDB[modeCount]=i+1;
                modeCount++;
            }
        }
        return modeDB;

    }
    public ArrayList<Posture> getRandomPosture(Context context){
        PostureCollection postureCollection= PostureCollection.getInstance(context);
        ArrayList<Posture> randomPosture = new ArrayList<>();
        for(int i=0;i<2;i++){
            ArrayList<Posture> modePosture = postureCollection.getPostureMode(modeSelect[i], context);
            int x;
            do {
                x = (int) (Math.random() * modePosture.size());

            }while (i>0&&modePosture.get(x).getIdPosture()==randomPosture.get(i-1).getIdPosture());
            randomPosture.add(modePosture.get(x));
        }

        return  randomPosture;
    }

}
