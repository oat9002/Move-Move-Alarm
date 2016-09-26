package com.kmitl.movealarm.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
//import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kmitl.movealarm.RESTService.Implement.UserServiceImp;
import com.kmitl.movealarm.Model.User;
import com.kmitl.movealarm.R;
import com.kmitl.movealarm.Service.UserManage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.Retrofit;


public class ScoreboardUserFragment extends Fragment {

    @BindView(R.id.current_user_name) TextView currentUserName;
    @BindView(R.id.current_user_score) TextView currentUserScore;
    @BindView(R.id.current_user_rank) TextView currentUserRank;

    public static ScoreboardUserFragment newInstance() {
        ScoreboardUserFragment fragment = new ScoreboardUserFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_scoreboard_user, container, false);
        ButterKnife.bind(this, rootView);
        currentUserScore.setText("" + UserManage.getCurrentUser().getScore());
        currentUserName.setText(UserManage.getCurrentUser().getFacebookFirstName());

        UserServiceImp.getInstance().getTopRank(10, new Callback<List<User>>() {
            @Override
            public void onResponse(retrofit.Response<List<User>> response, Retrofit retrofit) {
                for (int i = 1; i <= response.body().size(); i++) {
                    int groupNameId = getResources().getIdentifier("user_no" + i + "_name", "id", getActivity().getPackageName());
                    int groupScoreId = getResources().getIdentifier("user_no" + i + "_score", "id", getActivity().getPackageName());
                    ((TextView) rootView.findViewById(groupNameId)).setText(response.body().get(i - 1).getFacebookFirstName());
                    ((TextView) rootView.findViewById(groupScoreId)).setText("" + response.body().get(i - 1).getScore());
                }
            }

            @Override
            public void onFailure(Throwable t) {
//                Log.i("error", t.toString());
            }
        });

        UserServiceImp.getInstance().getRank(UserManage.getCurrentUser(), new Callback<Integer>() {
            @Override
            public void onResponse(retrofit.Response<Integer> response, Retrofit retrofit) {
                currentUserRank.setText("" + response.body());
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
        return rootView;
    }


}
