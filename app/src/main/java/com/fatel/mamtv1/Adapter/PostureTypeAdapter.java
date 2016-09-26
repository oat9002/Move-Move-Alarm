package com.fatel.mamtv1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.AppCompatImageView;
//import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fatel.mamtv1.Model.PostureType;
import com.fatel.mamtv1.PostureActivity;
import com.fatel.mamtv1.R;

import java.util.List;

/**
 * Created by kid14 on 8/17/2016.
 */
public class PostureTypeAdapter extends
        RecyclerView.Adapter<PostureTypeAdapter.MyViewHolder> {

    private List<PostureType> imageList;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView title;
        public AppCompatImageView image;
        Context context;
        public MyViewHolder(View view,Context context) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            image = (AppCompatImageView) view.findViewById(R.id.image);
            this.context = context;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition() + 1;
//            Log.i("position",position+"");
            Intent intent = new Intent(context, PostureActivity.class);
            intent.putExtra("value", position);
            this.context.startActivity(intent);
        }
    }

    public PostureTypeAdapter(List<PostureType> imageList,Context context) {
        this.imageList = imageList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.choose_posture_view, parent, false);

        return new MyViewHolder(itemView,context);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {
        PostureType image = imageList.get(position);
        holder.title.setText(image.getTitle());
        holder.image.setBackgroundResource(image.getImage());
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }
}