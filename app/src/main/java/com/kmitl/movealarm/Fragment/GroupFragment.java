package com.kmitl.movealarm.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kmitl.movealarm.MainActivity;
import com.kmitl.movealarm.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class GroupFragment extends android.support.v4.app.Fragment {

    public static GroupFragment instance = null;

    public GroupFragment() {
        instance = this;
    }

    public void finish()
    {
        getActivity().finish();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ActionBar mActionBar = ((MainActivity) getActivity()).getsupportactionbar();
        if(mActionBar !=null)
            mActionBar .setTitle("กลุ่ม");
    }
}
