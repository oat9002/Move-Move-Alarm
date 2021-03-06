package com.kmitl.movealarm.Fragment;


import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
//import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.kmitl.movealarm.Service.AlarmReceiver;
import com.kmitl.movealarm.Service.Cache;
import com.kmitl.movealarm.Helper.DBAlarmHelper;
import com.kmitl.movealarm.MainActivity;
import com.kmitl.movealarm.Model.Alarm;
import com.kmitl.movealarm.R;
import com.kmitl.movealarm.Service.UserManage;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlarmFragment extends android.support.v4.app.Fragment {
    private Spinner mStartHr;
    private Spinner mStartMin;
    private Spinner mStartAP;
    private Spinner mFinishHr;
    private Spinner mFinishMin;
    private Spinner mFinishAP;
    private CheckBox mChkboxMon;
    private CheckBox mChkboxTue;
    private CheckBox mChkboxWed;
    private CheckBox mChkboxThu;
    private CheckBox mChkboxFri;
    private CheckBox mChkboxSat;
    private CheckBox mChkboxSun;
    private CheckBox mChkboxDA;
    private CheckBox mChkboxM1;
    private CheckBox mChkboxM2;
    private CheckBox mChkboxM3;
    private CheckBox mChkboxM4;
    private CheckBox mChkboxM5;
    private CheckBox mChkboxM6;
    private CheckBox mChkboxMA;
    private String mdays="";
    private String mModes="";
    private Spinner mFreq;
    private BootstrapButton buttonSet;
    private Switch mySwitch;
    private boolean checkswitch = false;
    Context context;
    private DBAlarmHelper mAlarmHelper;
    private int ID = -1;

    private PendingIntent pendingIntent;
    private AlarmManager manager;
    public AlarmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);

        mAlarmHelper = new DBAlarmHelper(getActivity());

        mStartHr = createSpinner(12, R.id.start_hr,true,view,mAlarmHelper,true);
        mStartMin = createSpinner(60, R.id.start_min,false,view,mAlarmHelper,true);
        mFinishHr = createSpinner(12, R.id.fin_hr, true, view, mAlarmHelper, false);
        mFinishMin = createSpinner(60, R.id.fin_min, false, view, mAlarmHelper, false);
        mStartAP = createSpinnerAmPm(R.id.start_AP, view, mAlarmHelper, true);
        mFinishAP = createSpinnerAmPm(R.id.fin_AP, view,mAlarmHelper,false);
        mChkboxSun = (CheckBox)view.findViewById(R.id.chkboxSun);
        mChkboxMon = (CheckBox)view.findViewById(R.id.chkboxMon);
        mChkboxTue = (CheckBox)view.findViewById(R.id.chkboxTue);
        mChkboxWed = (CheckBox)view.findViewById(R.id.chkboxWed);
        mChkboxThu = (CheckBox)view.findViewById(R.id.chkboxThu);
        mChkboxFri = (CheckBox)view.findViewById(R.id.chkboxFri);
        mChkboxSat = (CheckBox)view.findViewById(R.id.chkboxSat);
        mChkboxDA = (CheckBox)view.findViewById(R.id.chkboxDA);
        mChkboxM1 = (CheckBox)view.findViewById(R.id.chkboxM1);
        mChkboxM2 = (CheckBox)view.findViewById(R.id.chkboxM2);
        mChkboxM3 = (CheckBox)view.findViewById(R.id.chkboxM3);
        mChkboxM4 = (CheckBox)view.findViewById(R.id.chkboxM4);
        mChkboxM5 = (CheckBox)view.findViewById(R.id.chkboxM5);
        mChkboxM6 = (CheckBox)view.findViewById(R.id.chkboxM6);
        mChkboxMA = (CheckBox)view.findViewById(R.id.chkboxMA);
        buttonSet = (BootstrapButton)view.findViewById(R.id.buttonSet);
        mFreq = createSpinnerFrq(R.id.frq_min, view,mAlarmHelper);
        mySwitch = (Switch) view.findViewById(R.id.mySwitch);
        //check checkbox tick
        if(mAlarmHelper.checkdata()==1) {
            Alarm alarm = mAlarmHelper.getAlarm();
            String day = alarm.getDay();
            String mode = alarm.getMode();
            if(day.equals("1111111")){
                mChkboxDA.setChecked(true);
                mChkboxSun.setChecked(true);
                mChkboxMon.setChecked(true);
                mChkboxTue.setChecked(true);
                mChkboxWed.setChecked(true);
                mChkboxThu.setChecked(true);
                mChkboxFri.setChecked(true);
                mChkboxSat.setChecked(true);
                mChkboxSun.setEnabled(false);
                mChkboxMon.setEnabled(false);
                mChkboxTue.setEnabled(false);
                mChkboxWed.setEnabled(false);
                mChkboxThu.setEnabled(false);
                mChkboxFri.setEnabled(false);
                mChkboxSat.setEnabled(false);

            }
            else {
                if (day.substring(0, 1).equals("1"))
                    mChkboxSun.setChecked(true);
                if (day.substring(1, 2).equals("1"))
                    mChkboxMon.setChecked(true);
                if (day.substring(2, 3).equals("1"))
                    mChkboxTue.setChecked(true);
                if (day.substring(3, 4).equals("1"))
                    mChkboxWed.setChecked(true);
                if (day.substring(4, 5).equals("1"))
                    mChkboxThu.setChecked(true);
                if (day.substring(5, 6).equals("1"))
                    mChkboxFri.setChecked(true);
                if (day.substring(6, 7).equals("1"))
                    mChkboxSat.setChecked(true);
            }
            if(mode.equals("111111")){
                mChkboxMA.setChecked(true);
                mChkboxM1.setChecked(true);
                mChkboxM2.setChecked(true);
                mChkboxM3.setChecked(true);
                mChkboxM4.setChecked(true);
                mChkboxM5.setChecked(true);
                mChkboxM6.setChecked(true);
                mChkboxM1.setEnabled(false);
                mChkboxM2.setEnabled(false);
                mChkboxM3.setEnabled(false);
                mChkboxM4.setEnabled(false);
                mChkboxM5.setEnabled(false);
                mChkboxM6.setEnabled(false);
            }
            else {
                if (mode.substring(0, 1).equals("1"))
                    mChkboxM1.setChecked(true);
                if (mode.substring(1, 2).equals("1"))
                    mChkboxM2.setChecked(true);
                if (mode.substring(2, 3).equals("1"))
                    mChkboxM3.setChecked(true);
                if (mode.substring(3, 4).equals("1"))
                    mChkboxM4.setChecked(true);
                if (mode.substring(4, 5).equals("1"))
                    mChkboxM5.setChecked(true);
                if (mode.substring(5, 6).equals("1"))
                    mChkboxM6.setChecked(true);
            }
        }
        View rootView = inflater.inflate(R.layout.fragment_alarm, container, false);
        context = rootView.getContext();

        mChkboxDA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mChkboxDA.isChecked()){
                    mChkboxSun.setChecked(true);
                    mChkboxMon.setChecked(true);
                    mChkboxTue.setChecked(true);
                    mChkboxWed.setChecked(true);
                    mChkboxThu.setChecked(true);
                    mChkboxFri.setChecked(true);
                    mChkboxSat.setChecked(true);
                    mChkboxSun.setEnabled(false);
                    mChkboxMon.setEnabled(false);
                    mChkboxTue.setEnabled(false);
                    mChkboxWed.setEnabled(false);
                    mChkboxThu.setEnabled(false);
                    mChkboxFri.setEnabled(false);
                    mChkboxSat.setEnabled(false);

                }
                else {
                    mChkboxSun.setChecked(false);
                    mChkboxMon.setChecked(false);
                    mChkboxTue.setChecked(false);
                    mChkboxWed.setChecked(false);
                    mChkboxThu.setChecked(false);
                    mChkboxFri.setChecked(false);
                    mChkboxSat.setChecked(false);
                    mChkboxSun.setEnabled(true);
                    mChkboxMon.setEnabled(true);
                    mChkboxTue.setEnabled(true);
                    mChkboxWed.setEnabled(true);
                    mChkboxThu.setEnabled(true);
                    mChkboxFri.setEnabled(true);
                    mChkboxSat.setEnabled(true);
                }
            }
        });

        mChkboxMA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mChkboxMA.isChecked()){
                    mChkboxMA.setChecked(true);
                    mChkboxM1.setChecked(true);
                    mChkboxM2.setChecked(true);
                    mChkboxM3.setChecked(true);
                    mChkboxM4.setChecked(true);
                    mChkboxM5.setChecked(true);
                    mChkboxM6.setChecked(true);
                    mChkboxM1.setEnabled(false);
                    mChkboxM2.setEnabled(false);
                    mChkboxM3.setEnabled(false);
                    mChkboxM4.setEnabled(false);
                    mChkboxM5.setEnabled(false);
                    mChkboxM6.setEnabled(false);
                }
                else {
                    mChkboxMA.setChecked(false);
                    mChkboxM1.setChecked(false);
                    mChkboxM2.setChecked(false);
                    mChkboxM3.setChecked(false);
                    mChkboxM4.setChecked(false);
                    mChkboxM5.setChecked(false);
                    mChkboxM6.setChecked(false);
                    mChkboxM1.setEnabled(true);
                    mChkboxM2.setEnabled(true);
                    mChkboxM3.setEnabled(true);
                    mChkboxM4.setEnabled(true);
                    mChkboxM5.setEnabled(true);
                    mChkboxM6.setEnabled(true);
                }
            }
        });

        BootstrapButton bt = (BootstrapButton) view.findViewById(R.id.buttonSet);
        bt.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View view) {


                                      if(mChkboxDA.isChecked()){
                                          mdays = "1111111";
                                      }
                                      else {
                                          if (mChkboxSun.isChecked()) {
                                              mdays += "1";
                                          } else {
                                              mdays += "0";
                                          }
                                          if (mChkboxMon.isChecked()) {
                                              mdays += "1";
                                          } else {
                                              mdays += "0";
                                          }
                                          if (mChkboxTue.isChecked()) {
                                              mdays += "1";
                                          } else {
                                              mdays += "0";
                                          }
                                          if (mChkboxWed.isChecked()) {
                                              mdays += "1";
                                          } else {
                                              mdays += "0";
                                          }
                                          if (mChkboxThu.isChecked()) {
                                              mdays += "1";
                                          } else {
                                              mdays += "0";
                                          }
                                          if (mChkboxFri.isChecked()) {
                                              mdays += "1";
                                          } else {
                                              mdays += "0";
                                          }
                                          if (mChkboxSat.isChecked()) {
                                              mdays += "1";
                                          } else {
                                              mdays += "0";
                                          }
                                      }
                                      if(mChkboxMA.isChecked()){
                                          mModes = "111111";
                                      }
                                      else {

                                          if (mChkboxM1.isChecked()) {
                                              mModes += "1";
                                          } else {
                                              mModes += "0";
                                          }
                                          if (mChkboxM2.isChecked()) {
                                              mModes += "1";
                                          } else {
                                              mModes += "0";
                                          }
                                          if (mChkboxM3.isChecked()) {
                                              mModes += "1";
                                          } else {
                                              mModes += "0";
                                          }
                                          if (mChkboxM4.isChecked()) {
                                              mModes += "1";
                                          } else {
                                              mModes += "0";
                                          }
                                          if (mChkboxM5.isChecked()) {
                                              mModes += "1";
                                          } else {
                                              mModes += "0";
                                          }
                                          if (mChkboxM6.isChecked()) {
                                              mModes += "1";
                                          } else {
                                              mModes += "0";
                                          }
                                      }
                                      //keep data

                                      if (Integer.parseInt(mdays) == 0) {
                                          Toast.makeText(getActivity(), "กรุณาเลือกวันทำงาน", Toast.LENGTH_SHORT).show();
                                      }
                                      else if (Integer.parseInt(mModes) == 0) {
                                          Toast.makeText(getActivity(), "กรุณาเลือกหมวดท่าบริหาร", Toast.LENGTH_SHORT).show();
                                      }
                                      else {

                                          Alarm alarm = new Alarm();
                                          alarm.setId(1);
                                          alarm.setStarthr(mStartHr.getSelectedItem().toString());
                                          alarm.setStartmin(mStartMin.getSelectedItem().toString());
                                          alarm.setStophr(mFinishHr.getSelectedItem().toString());
                                          alarm.setStopmin(mFinishMin.getSelectedItem().toString());
                                          alarm.setStartinterval(mStartAP.getSelectedItem().toString());
                                          alarm.setStopinterval(mFinishAP.getSelectedItem().toString());
                                          alarm.setDay(mdays.toString());
                                          alarm.setFrq(mFreq.getSelectedItem().toString());
                                          alarm.setMode(mModes.toString());
                                          if (ID == -1 && (mAlarmHelper.checkdata() == 0)) {
                                              mAlarmHelper.addAlarm(alarm);
                                          } else {
                                              mAlarmHelper.UpdateAlarm(alarm);
                                          }
                                          mdays = "";
                                          Intent mServiceIntent = new Intent(getActivity(), AlarmReceiver.class);
                                          pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, mServiceIntent, 0);
                                          start();

                                          Toast.makeText(getActivity(), "ตั้งค่าสำเร็จ", Toast.LENGTH_SHORT).show();

                                          Intent intent = new Intent(context, MainActivity.class);
                                          intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                          startActivity(intent);

                                      }


                                  }
                              }
        );

        BootstrapButton bt2 = (BootstrapButton) view.findViewById(R.id.reset);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linktoreset();

            }
        });

        if (UserManage.getInstance(getActivity()).getCurrentUser().getStatesw() == 1) {
            mySwitch.setChecked(true);
        } else {
            mySwitch.setChecked(false);
        }
        //attach a listener to check for changes in state
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    //switchStatus.setText("Switch is currently ON");
                    checkswitch = true;
                    mySwitch.setChecked(true);
                    UserManage.getInstance(getActivity()).getCurrentUser().setStatesw(1);
//                    Log.i("state sw", "Switch on");
                } else {
                    //switchStatus.setText("Switch is currently OFF");
                    checkswitch = false;
                    mySwitch.setChecked(false);
                    UserManage.getInstance(getActivity()).getCurrentUser().setStatesw(0);
//                    Log.i("state sw", "Switch off");
                }
                Cache.getInstance().putData("switch", checkswitch);
            }
        });

        return view;
    }

    public Spinner createSpinner(int num,int id,boolean isHr,View view,DBAlarmHelper mAlarmHelper,boolean isStart)
    {
        Spinner spinner = (Spinner)view.findViewById(id);
        String[] numm = new String[num];
        ArrayAdapter<String> adapter;
        if(isHr) {
            for (int i = 1; i <= num; i++) {
                if(i<10)
                    numm[i - 1] = "0" + i;
                else
                    numm[i - 1] = "" + i;
            }
            adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, numm);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            if(mAlarmHelper.checkdata()==0)
                spinner.setSelection(11);
            else
            {
                Alarm alarm = mAlarmHelper.getAlarm();
                if(isStart) {
                    String hr = alarm.getStarthr();
                    spinner.setSelection(Integer.parseInt(hr) - 1);
                }
                else
                {
                    String hr = alarm.getStophr();
                    spinner.setSelection(Integer.parseInt(hr)-1);
                }
            }
        }
        else {
            for (int i = 0; i < num; i++) {
                if(i<10)
                    numm[i] = "0" + i;
                else
                    numm[i] = "" + i;
            }
            adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, numm);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            if(mAlarmHelper.checkdata()==1)
            {
                Alarm alarm = mAlarmHelper.getAlarm();
                if(isStart) {
                    String min = alarm.getStartmin();
                    spinner.setSelection(Integer.parseInt(min));
                }
                else
                {
                    String min = alarm.getStopmin();
                    spinner.setSelection(Integer.parseInt(min));
                }
            }
        }
        return spinner;
    }

    public Spinner createSpinnerAmPm(int id,View view,DBAlarmHelper mAlarmHelper,boolean isStart)
    {
        Spinner spinner = (Spinner)view.findViewById(id);
        String[] numm = new String[]{"AM","PM"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, numm);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        if(mAlarmHelper.checkdata()==1)
        {
            Alarm alarm = mAlarmHelper.getAlarm();
            if(isStart) {
                String ap = alarm.getStartinterval();
                if(ap.equals("AM"))
                    spinner.setSelection(0);
                else
                    spinner.setSelection(1);
            }
            else
            {
                String ap = alarm.getStopinterval();
                if(ap.equals("AM"))
                    spinner.setSelection(0);
                else
                    spinner.setSelection(1);
            }
        }
        return spinner;
    }

    public Spinner createSpinnerFrq(int id,View view,DBAlarmHelper mAlarmHelper)
    {
        Spinner spinner = (Spinner)view.findViewById(id);
        String[] numm = new String[]{"15","30","45","60","75","90"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, numm);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        if(mAlarmHelper.checkdata()==1)
        {
            Alarm alarm = mAlarmHelper.getAlarm();
            String frq = alarm.getFrq();
            int frqint = -1;
            if(frq.equals("15"))
                frqint = 0;
            else if(frq.equals("30"))
                frqint = 1;
            else if(frq.equals("45"))
                frqint = 2;
            else if(frq.equals("60"))
                frqint = 3;
            else if(frq.equals("75"))
                frqint = 4;
            else if(frq.equals("90"))
                frqint = 5;
            spinner.setSelection(frqint);
        }
        return spinner;
    }
    public void start() {

        Intent i = new Intent(getActivity(), AlarmReceiver.class);
        Bundle b = new Bundle();
        b.putString("key", "set");
        i.putExtras(b);
        getActivity().sendBroadcast(i);
    }
    public void linktoreset(){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        mAlarmHelper =  new DBAlarmHelper(getActivity());
                        mAlarmHelper.deleteSetAlarm("1");
                        Toast.makeText(getActivity(), "รีเซตการตั้งค่าการแจ้งเตือน", Toast.LENGTH_SHORT).show();

                        getFragmentManager().popBackStack();

                        Fragment fragment = null;
                        Class fragmentClass;
                        fragmentClass = AlarmFragment.class;
                        try {
                            fragment = (Fragment) fragmentClass.newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        FragmentTransaction tr = getFragmentManager().beginTransaction();
                        tr.addToBackStack(null);
                        tr.replace(R.id.container, fragment);
                        tr.commit();

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_DARK);
        builder.setTitle("รีเซตการตั้งค่าการแจ้งเตือน");
        builder.setMessage("ยืนยันการรีเซต?").setPositiveButton("ตกลง", dialogClickListener)
                .setNegativeButton("ยกเลิก", dialogClickListener).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        ActionBar mActionBar = ((MainActivity) getActivity()).getsupportactionbar();
        if(mActionBar !=null)
            mActionBar .setTitle("ตั้งค่าการเตือน");
    }
}
