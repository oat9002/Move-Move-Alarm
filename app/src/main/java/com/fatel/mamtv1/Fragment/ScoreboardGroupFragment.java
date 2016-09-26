package com.fatel.mamtv1.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
//import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fatel.mamtv1.Model.Group;
import com.fatel.mamtv1.RESTService.Implement.GroupServiceImp;
import com.fatel.mamtv1.Service.Cache;
import com.fatel.mamtv1.R;
import com.fatel.mamtv1.Service.UserManage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.Retrofit;

public class ScoreboardGroupFragment extends Fragment {
    @BindView(R.id.current_group_name) TextView currentGroupName;
    @BindView(R.id.current_group_score) TextView currentGroupScore;
    @BindView(R.id.current_group_rank) TextView currentGroupRank;

    public static ScoreboardGroupFragment newInstance() {
        ScoreboardGroupFragment fragment = new ScoreboardGroupFragment();
        return fragment;
    }

    public ScoreboardGroupFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_scoreboard_group, container, false);
        ButterKnife.bind(this, rootView);
        GroupServiceImp.getInstance().getTopRank(10, new Callback<List<Group>>() {
            @Override
            public void onResponse(retrofit.Response<List<Group>> response, Retrofit retrofit) {
                for (int i = 1; i <= response.body().size(); i++) {
                    int groupNameId = getResources().getIdentifier("group_no" + i + "_name", "id", getActivity().getPackageName());
                    int groupScoreId = getResources().getIdentifier("group_no" + i + "_score", "id", getActivity().getPackageName());
                    ((TextView) rootView.findViewById(groupNameId)).setText(response.body().get(i - 1).getName());
                    ((TextView) rootView.findViewById(groupScoreId)).setText("" + response.body().get(i - 1).getScore());
                }
            }

            @Override
            public void onFailure(Throwable t) {
//                Log.i("error", t.toString());
            }
        });

        if(UserManage.getCurrentUser().getGroupId() != 0) {
            GroupServiceImp.getInstance().getRank((Group) Cache.getInstance().getData("groupData"), new Callback<Integer>() {
                @Override
                public void onResponse(retrofit.Response<Integer> response, Retrofit retrofit) {
                    Group groupData = (Group) Cache.getInstance().getData("groupData");
                    currentGroupName.setText(groupData.getName());
                    currentGroupScore.setText("" + groupData.getScore());
                    currentGroupRank.setText("" + response.body());
                }

                @Override
                public void onFailure(Throwable t) {
//                    Log.i("error", t.toString());
                }
            });
        }
        return rootView;
    }

}
