package com.fatel.mamtv1;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.GregorianCalendar;
import java.util.Calendar;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends android.support.v4.app.Fragment {
    CircleImageView propic;
    TextView name;
    TextView birth;
    TextView age;
    TextView height;
    TextView weight;
    TextView waist;
    TextView bmi;
    Button edit;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        propic = (CircleImageView)view.findViewById(R.id.profile_image_p);
        name = (TextView)view.findViewById(R.id.editFB);
        birth = (TextView)view.findViewById(R.id.editbirthday);
        age = (TextView)view.findViewById(R.id.editage);
        height = (TextView)view.findViewById(R.id.editheight);
        weight = (TextView)view.findViewById(R.id.editweight);
        waist = (TextView)view.findViewById(R.id.editwaistline);
        bmi = (TextView)view.findViewById(R.id.editbmi);
        edit = (Button)view.findViewById(R.id.edit);
        String tempid = UserManage.getInstance(getActivity()).getCurrentUser().getFacebookID();
        if(!tempid.equals("0.0")) {
            if(!tempid.equals("0")) {
                if(!(tempid.equals("fb0.0"))) {
                    Glide.with(this).load("https://graph.facebook.com/" + tempid + "/picture?type=large").into(propic);
                }
            }
        }

        if(!(UserManage.getInstance(getActivity()).getCurrentUser().getFacebookFirstName()+"").equals("null"))
            name.setText(UserManage.getInstance(getActivity()).getCurrentUser().getFacebookFirstName()+UserManage.getInstance(getActivity()).getCurrentUser().getFacebookLastName());
        if(!(UserManage.getInstance(getActivity()).getCurrentUser().getBirthDay()+"").equals("null"))
            birth.setText(UserManage.getInstance(getActivity()).getCurrentUser().getBirthDay());
        if(UserManage.getInstance(getActivity()).getCurrentUser().getAge()>=0)
            age.setText(Integer.toString(UserManage.getInstance(getActivity()).getCurrentUser().getAge()));
        if(UserManage.getInstance(getActivity()).getCurrentUser().getHeight()>0)
            height.setText(Integer.toString(UserManage.getInstance(getActivity()).getCurrentUser().getHeight()));
        if(UserManage.getInstance(getActivity()).getCurrentUser().getWeight()>0)
            weight.setText(Integer.toString(UserManage.getInstance(getActivity()).getCurrentUser().getWeight()));
        if(UserManage.getInstance(getActivity()).getCurrentUser().getWaistline()>0)
            waist.setText(Integer.toString(UserManage.getInstance(getActivity()).getCurrentUser().getWaistline()));
        if(UserManage.getInstance(getActivity()).getCurrentUser().getBmi()>0)
            bmi.setText(String.format("%.2f", UserManage.getInstance(getActivity()).getCurrentUser().getBmi()));

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final EditText h = new EditText(getActivity());
                final EditText w = new EditText(getActivity());
                final EditText waist = new EditText(getActivity());
                final TextView txtB = new TextView(getActivity());
                final TextView txtH = new TextView(getActivity());
                final TextView txtW = new TextView(getActivity());
                final TextView txtWa = new TextView(getActivity());
                final DatePicker birthday = new DatePicker(getActivity());



                h.setText("");
                w.setText("");
                waist.setText("");
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                               // if (birthday.isFocusable()) {

                                    int   day  = birthday.getDayOfMonth();
                                    int   month= birthday.getMonth() + 1;
                                    int   year = birthday.getYear();
                                    String date=checkDigit(day)+"/"+checkDigit(month)+"/"+year;
                                    UserManage.getInstance(getActivity()).getCurrentUser().setBirthDay(date);
                                    int Calage = getAge(year,month,day);
                                    UserManage.getInstance(getActivity()).getCurrentUser().setAge(Calage);
                                //}
                                if (!isEmpty(h)) {
                                    UserManage.getInstance(getActivity()).getCurrentUser().setHeight(Integer.valueOf(h.getText().toString()));
                                    int currentW =UserManage.getInstance(getActivity()).getCurrentUser().getWeight();
                                    if(currentW>0) {
                                        UserManage.getInstance(getActivity()).getCurrentUser().setBmi(BmiCal(0.0+currentW,Double.valueOf(h.getText().toString())));
                                    }
                                }

                                if (!isEmpty(w)) {
                                    UserManage.getInstance(getActivity()).getCurrentUser().setWeight(Integer.valueOf(w.getText().toString()));
                                    int currentH =UserManage.getInstance(getActivity()).getCurrentUser().getHeight();
                                    if(currentH>0) UserManage.getInstance(getActivity()).getCurrentUser().setBmi(BmiCal(Double.valueOf(w.getText().toString()),currentH));
                                }
                                if (!isEmpty(w)&&!isEmpty(h)){
                                    UserManage.getInstance(getActivity()).getCurrentUser().setBmi(BmiCal(Double.valueOf(w.getText().toString()),Double.valueOf(h.getText().toString())));
                                }
                                if (!isEmpty(waist)) {
                                    UserManage.getInstance(getActivity()).getCurrentUser().setWaistline(Integer.valueOf(waist.getText().toString()));
                                }
                                UserManage.getInstance(getActivity()).getCurrentUser().save(getActivity());
                                UserManage.getInstance(getActivity()).updateUser(getActivity());
                                getFragmentManager().popBackStack();
                                Fragment fragment = null;
                                Class fragmentClass;
                                fragmentClass = ProfileFragment.class;
                                try {
                                    fragment = (Fragment) fragmentClass.newInstance();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                FragmentTransaction tx = getFragmentManager().beginTransaction();
                                tx.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                                tx.addToBackStack(null);
                                tx.replace(R.id.container, fragment).commit();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };
                LinearLayout layout = new LinearLayout(getActivity());
                layout.setOrientation(LinearLayout.VERTICAL);
                LinearLayout layh =new LinearLayout(getActivity());
                layh.setOrientation(LinearLayout.HORIZONTAL);

                layout.setPadding(20,10,20,10);
                txtH.setText(Html.fromHtml("<font color='#FF9900'>ส่วนสูง</font>"));
                txtH.setTextSize(20);
                layh.addView(txtH);
                h.setHint(Html.fromHtml("<font color='#B0B0B0'>เซนติเมตร</font>"));
                h.setLayoutParams(new LinearLayout.LayoutParams(250,120));
                layh.addView(h);
                //layout.addView(layh);
                LinearLayout layw =new LinearLayout(getActivity());
                layw.setOrientation(LinearLayout.HORIZONTAL);
                txtW.setText(Html.fromHtml("<font color='#FF9900'>น้ำหนัก</font>"));
                txtW.setTextSize(20);
                txtW.setPadding(10,0,0,0);
                layh.addView(txtW);
                w.setHint(Html.fromHtml("<font color='#B0B0B0'>กิโลกรัม</font>"));
                w.setLayoutParams(new LinearLayout.LayoutParams(250,120));
                layh.addView(w);
                layout.addView(layh);
                LinearLayout layWa =new LinearLayout(getActivity());
                layWa.setOrientation(LinearLayout.HORIZONTAL);
                txtWa.setText(Html.fromHtml("<font color='#FF9900'>รอบเอว</font>"));
                txtWa.setTextSize(20);
                layWa.addView(txtWa);
                waist.setHint(Html.fromHtml("<font color='#B0B0B0'>นิ้ว</font>"));
                waist.setLayoutParams(new LinearLayout.LayoutParams(300,120));
                layWa.addView(waist);
                layout.addView(layWa);
                txtB.setText(Html.fromHtml("<font color='#FF9900'>วันเกิด</font>"));
                txtB.setTextSize(20);
                layout.addView(txtB);
                layout.addView(birthday);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
                builder.setTitle("แก้ไขข้อมูล");
                builder.setView(layout);
                builder.setPositiveButton("ตกลง", dialogClickListener)
                        .setNegativeButton("ยกเลิก", dialogClickListener).show();

            }
        });

        return view;
    }
    private boolean isEmpty(EditText myeditText) {
        return myeditText.getText().toString().trim().length() == 0;
    }
    private Double BmiCal(double weight,double height){
        Double hM = height/100;
        Double Calbmi = weight/(hM*hM);
        return Calbmi;
    }
    private String checkDigit(int number)
    {
        return number<=9?"0"+number:String.valueOf(number);
    }
    private int getAge (int _year, int _month, int _day) {

        GregorianCalendar cal = new GregorianCalendar();
        int y, m, d, a;

        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        d = cal.get(Calendar.DAY_OF_MONTH);
        cal.set(_year, _month, _day);
        a = y - cal.get(Calendar.YEAR);
        if ((m < cal.get(Calendar.MONTH))
                || ((m == cal.get(Calendar.MONTH)) && (d < cal
                .get(Calendar.DAY_OF_MONTH)))) {
            --a;
        }
        if(a < 0)
            a=0;
        return a;
    }



}
