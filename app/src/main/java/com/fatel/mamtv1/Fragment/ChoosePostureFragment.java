package com.fatel.mamtv1.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.Toast;

import com.fatel.mamtv1.Adapter.PostureTypeAdapter;
import com.fatel.mamtv1.Model.PostureType;
import com.fatel.mamtv1.PostureActivity;
import com.fatel.mamtv1.R;

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
    String[] title  = {"คอ", "ไหล่", "อกและหลัง"
            , "ข้อมือ", "เอว", "สะโพก ขาและน่อง"};
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


}
