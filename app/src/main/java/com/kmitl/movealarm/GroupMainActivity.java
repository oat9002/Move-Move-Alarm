package com.kmitl.movealarm;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.util.Log;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.kmitl.movealarm.Model.Event;
import com.kmitl.movealarm.Model.Group;
import com.kmitl.movealarm.Model.User;
import com.kmitl.movealarm.RESTService.Implement.EventServiceImp;
import com.kmitl.movealarm.RESTService.Implement.GroupServiceImp;
import com.kmitl.movealarm.Service.Cache;
import com.kmitl.movealarm.Service.Converter;
import com.kmitl.movealarm.Service.MyApplication;
import com.kmitl.movealarm.Service.UserManage;

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
    @BindView(R.id.exitGroup)
    BootstrapButton exitGroup;
    private Group groupData;
    UserManage userManage ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_main);
        ButterKnife.bind(this);
//        groupData = (Group) getIntent().getSerializableExtra("groupData");
        User user = UserManage.getCurrentUser();


        GroupServiceImp.getInstance().findByUser(user, new Callback<Group>() {
            @Override
            public void onResponse(retrofit.Response<Group> response, Retrofit retrofit) {
                Group group = response.body();
                if(group == null)
                    return;
                groupData = group;

                groupData.save(MyApplication.getAppContext());
                groupData.getProgress().save(MyApplication.getAppContext());


                String code = String.format("%0" + (4 - Converter.toString(groupData.getGroupId()).length()) + "d%s", 0, groupData.getGroupId());
                groupCode.setText(code);
                groupName.setText(groupData.getName());
                adminName.setText(groupData.getAdmin().getFacebookFirstName());
                groupScore.setText("" + groupData.getScore());


                if(groupData.getAdmin().getId() == userManage.getCurrentUser().getId()){
                    exitGroup.setText("ลบกลุ่ม");
                }
                else {
                    exitGroup.setText("ออกจากกลุ่ม");
                }
//                amountMember.setText("0");

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

            }

            @Override
            public void onFailure(Throwable t) {
                groupData = Group.find(UserManage.getCurrentUser().getGroupId(),getApplicationContext());

                String code = String.format("%0" + (4 - Converter.toString(groupData.getGroupId()).length()) + "d%s", 0, groupData.getGroupId());
                groupCode.setText(code);
                groupName.setText(groupData.getName());
                adminName.setText(groupData.getAdmin().getFacebookFirstName());
                amountMember.setText("" + groupData.getMembers().size());
                groupScore.setText("" + groupData.getScore());

                if(groupData.getAdmin().getId() == userManage.getCurrentUser().getId()){
                    exitGroup.setText("ลบกลุ่ม");
                }
                else {
                    exitGroup.setText("ออกจากกลุ่ม");
                }
            }
        });
//
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
    public void leaveGroup(View view)
    {

        if(groupData.getAdmin().getId() == userManage.getCurrentUser().getId()) {

            GroupServiceImp.getInstance().deleteGroup(userManage.getCurrentUser(), new Callback<User>() {

                @Override
                public void onResponse(retrofit.Response<User> response, Retrofit retrofit) {

                    userManage.getCurrentUser().setGroupId(0);
                    userManage.getCurrentUser().save(GroupMainActivity.this);
                    Intent intent2 = new Intent(GroupMainActivity.this, MainActivity.class);
                    Cache.getInstance().putData("groupData",null);
                    startActivity(intent2);
//                    Log.i("response", response.raw().toString());
                    Snackbar.make(exitGroup, "ลบกลุ่มสำเร็จ", Snackbar.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(Throwable t) {
//                    Log.i("error", t.getMessage());
                    Snackbar.make(exitGroup, "ไม่สามารถลบกลุ่มได้", Snackbar.LENGTH_SHORT).show();
                }
            });
        }
        else {

            GroupServiceImp.getInstance().leaveGroup(userManage.getCurrentUser(), new Callback<User>() {

                @Override
                public void onResponse(retrofit.Response<User> response, Retrofit retrofit) {

                    userManage.getCurrentUser().setGroupId(0);
                    userManage.getCurrentUser().save(GroupMainActivity.this);
                    Intent intent2 = new Intent(GroupMainActivity.this, MainActivity.class);
                    Cache.getInstance().putData("groupData",null);
                    startActivity(intent2);
//                    Log.i("response", response.raw().toString());
                    Snackbar.make(exitGroup, "ออกจากกลุ่มสำเร็จ", Snackbar.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(Throwable t) {
//                    Log.i("error", t.getMessage());
                    Snackbar.make(exitGroup, "ไม่สามารถออกจากกลุ่มได้", Snackbar.LENGTH_SHORT).show();
                }
            });
        }

    }
}
