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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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
            name.setText(UserManage.getInstance(getActivity()).getCurrentUser().getFacebookFirstName());
        if(!(UserManage.getInstance(getActivity()).getCurrentUser().getBirthDay()+"").equals("null"))
            birth.setText(UserManage.getInstance(getActivity()).getCurrentUser().getBirthDay());
        if(UserManage.getInstance(getActivity()).getCurrentUser().getAge()>0)
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

                final EditText birthday = new EditText(getActivity());
                final EditText h = new EditText(getActivity());
                final EditText w = new EditText(getActivity());
                final EditText waist = new EditText(getActivity());
                birthday.setText("");
                h.setText("");
                w.setText("");
                waist.setText("");
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                if (!isEmpty(birthday)) {
                                    UserManage.getInstance(getActivity()).getCurrentUser().setBirthDay(birthday.getText().toString());
                                    int Calage = 0;
                                    UserManage.getInstance(getActivity()).getCurrentUser().setAge(Calage);
                                }
                                if (!isEmpty(h)) {
                                    UserManage.getInstance(getActivity()).getCurrentUser().setHeight(Integer.valueOf(h.getText().toString()));
                                }
                                if (!isEmpty(w)) {
                                    UserManage.getInstance(getActivity()).getCurrentUser().setWeight(Integer.valueOf(w.getText().toString()));
                                }
                                if (!isEmpty(h) && isEmpty(w)) {
                                    int Calbmi = 0;
                                    UserManage.getInstance(getActivity()).getCurrentUser().setBmi(Calbmi);
                                }
                                if (!isEmpty(waist)) {
                                    UserManage.getInstance(getActivity()).getCurrentUser().setWaistline(Integer.valueOf(waist.getText().toString()));
                                }
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
                birthday.setTextColor(Color.WHITE);
                h.setTextColor(Color.WHITE);
                w.setTextColor(Color.WHITE);
                waist.setTextColor(Color.WHITE);
                LinearLayout layout = new LinearLayout(getActivity());
                layout.setOrientation(LinearLayout.VERTICAL);
                birthday.setHint(Html.fromHtml("<font color='#B0B0B0'>วันเกิด</font>"));
                layout.addView(birthday);
                h.setHint(Html.fromHtml("<font color='#B0B0B0'>ส่วนสูง</font>"));
                layout.addView(h);
                w.setHint(Html.fromHtml("<font color='#B0B0B0'>น้ำหนัก</font>"));
                layout.addView(w);
                waist.setHint(Html.fromHtml("<font color='#B0B0B0'>รอบเอว</font>"));
                layout.addView(waist);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_DARK);
                builder.setTitle("แก้ไขข้อมูล");
                builder.setView(layout);
                builder.setMessage("แก้ไขข้อมูลส่วนตัว").setPositiveButton("ตกลง", dialogClickListener)
                        .setNegativeButton("ยกเลิก", dialogClickListener).show();

            }
        });

        return view;
    }
    private boolean isEmpty(EditText myeditText) {
        return myeditText.getText().toString().trim().length() == 0;
    }


}
