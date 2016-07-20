package com.fatel.mamtv1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.fatel.mamtv1.Model.Event;
import com.fatel.mamtv1.Model.Group;
import com.fatel.mamtv1.RESTService.Implement.EventServiceImp;
import com.fatel.mamtv1.Service.Cache;
import com.fatel.mamtv1.Service.Converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class GroupMainActivity extends AppCompatActivity {
    @BindView(R.id.groupMain_groupCode) TextView groupCode;
    @BindView(R.id.groupMain_adminName) TextView adminName;
    @BindView(R.id.groupMain_groupName) TextView groupName;
    @BindView(R.id.groupMain_amountMember) TextView amountMember;
    @BindView(R.id.groupMain_groupScore) TextView groupScore;
    @BindView(R.id.groupMain_event) TextView event;
    private Group groupData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_main);
        ButterKnife.bind(this);
        groupData = (Group) getIntent().getSerializableExtra("groupData");
        EventServiceImp.getInstance().getEvent(new Callback<Event>() {
            @Override
            public void onResponse(Response<Event> response, Retrofit retrofit) {
                DateFormat dateFormat = new SimpleDateFormat("hh : mm  aa");
                event.setText(dateFormat.format(response.body().getTime().getTime()));
                Cache.getInstance().putData("eventData", response.body());
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
        try {
            String code = String.format("%0" + (4 - Converter.toString(groupData.getId()).length()) + "d%s", 0, groupData.getId());
            groupCode.setText(code);
            groupName.setText(groupData.getName());
            adminName.setText(groupData.getAdmin().getFacebookFirstName());
            amountMember.setText("" + groupData.getAmountMember());
            groupScore.setText("" + groupData.getScore());
        } catch (Exception e) {
            Log.i("setup group", e.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_group_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void linkMember(View view)
    {
        Intent intent = new Intent(this, MemberGroupActivity.class);
        intent.putExtra("groupData", groupData);
        startActivity(intent);
    }
}
