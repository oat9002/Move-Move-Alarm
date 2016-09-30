package com.kmitl.movealarm.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
//import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;

import com.kmitl.movealarm.Adapter.PostureTypeAdapter;
import com.kmitl.movealarm.MainActivity;
import com.kmitl.movealarm.Model.PostureType;
import com.kmitl.movealarm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChoosePostureFragment extends android.support.v4.app.Fragment  {

    Context context;
    private List<PostureType> imageList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PostureTypeAdapter mAdapter;
    int[] image = {R.drawable.picneck, R.drawable.picelbow,
            R.drawable.picbreast, R.drawable.picneckhand,
            R.drawable.piceaw, R.drawable.picleg};
    String[] title  = {"คอ", "ไหล่", "ลำตัว"
            , "แขน", "หน้าอก หน้าท้อง และ หลัง", "เท้า ขา หน้าแข้ง และน่อง"};
    public ChoosePostureFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_posture, container, false);
        context = view.getContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(this.getContext(),
                        LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new PostureTypeAdapter(imageList,context);

        for (int i = 0 ; i < image.length ; i ++){
            PostureType image = new PostureType(title[i], this.image[i]);
            imageList.add(image);
        }
        recyclerView.setAdapter(mAdapter);

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        ActionBar mActionBar = ((MainActivity) getActivity()).getsupportactionbar();
        if(mActionBar !=null)
            mActionBar .setTitle("หมวดท่าบริหาร");
    }
}
