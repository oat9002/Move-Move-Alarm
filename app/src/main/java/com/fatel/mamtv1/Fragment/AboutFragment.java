package com.fatel.mamtv1.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fatel.mamtv1.MainActivity;
import com.fatel.mamtv1.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends android.support.v4.app.Fragment {


    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false);
    }
    @Override
    public void onResume() {
        super.onResume();
        ActionBar mActionBar = ((MainActivity) getActivity()).getsupportactionbar();
        if(mActionBar !=null)
            mActionBar .setTitle("เกี่ยวกับ");
    }

}
