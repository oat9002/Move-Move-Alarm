package com.fatel.mamtv1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChoosePostureFragment extends android.support.v4.app.Fragment  {

    Context context;
    public ChoosePostureFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_posture, container, false);
        Button mode1 = (Button)view.findViewById(R.id.mode1);
        Button mode2 = (Button)view.findViewById(R.id.mode2);
        Button mode3 = (Button)view.findViewById(R.id.mode3);
        Button mode4 = (Button)view.findViewById(R.id.mode4);
        Button mode5 = (Button)view.findViewById(R.id.mode5);
        Button mode6 = (Button)view.findViewById(R.id.mode6);
        context = view.getContext();

        mode1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, PostureActivity.class);
            intent.putExtra("value", 1);
            startActivity(intent);
        }
        });
        mode2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PostureActivity.class);
                intent.putExtra("value", 2);
                startActivity(intent);
            }
        });
        mode3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PostureActivity.class);
                intent.putExtra("value", 3);
                startActivity(intent);
            }
        });
        mode4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PostureActivity.class);
                intent.putExtra("value", 4);
                startActivity(intent);
            }
        });
        mode5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PostureActivity.class);
                intent.putExtra("value", 5);
                startActivity(intent);
            }
        });
        mode6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PostureActivity.class);
                intent.putExtra("value", 6);
                startActivity(intent);
            }
        });
        return view;

    }


}
