package com.kmitl.movealarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.util.Log;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.kmitl.movealarm.Model.Group;
import com.kmitl.movealarm.Model.GroupProgress;
import com.kmitl.movealarm.Model.StatusDescription;
import com.kmitl.movealarm.Model.User;
import com.kmitl.movealarm.RESTService.Implement.GroupServiceImp;
import com.kmitl.movealarm.Service.Cache;
import com.kmitl.movealarm.Service.Converter;
import com.kmitl.movealarm.Service.UserManage;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.Retrofit;

public class CreateGroupActivity extends AppCompatActivity {
    @BindView(R.id.edit_message)
    BootstrapEditText gName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_group, menu);
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

    public void linkGroup(View view)
    {
        if(gName.length()<6) {
            Toast.makeText(this, "จำนวนตัวอักษรขั้นต่ำ 6 ตัวอักษร", Toast.LENGTH_SHORT).show();
        } else {
            final User user = UserManage.getInstance(CreateGroupActivity.this).getCurrentUser();
            final Group group = new Group(gName.getText().toString(), user);
            GroupServiceImp.getInstance().createGroup(group, new Callback<StatusDescription>() {
                @Override
                public void onResponse(retrofit.Response<StatusDescription> response, Retrofit retrofit) {
                    if(response.body().isSuccess()) {
                        user.setGroupId(Converter.toInt(response.body().getData().get("id")));
                        group.setGroupId(Converter.toInt(response.body().getData().get("id")));
                        user.save(CreateGroupActivity.this);
                        group.save(CreateGroupActivity.this);
                        group.setProgress(new GroupProgress(group.getGroupId()));
                        group.getProgress().save(CreateGroupActivity.this);
                        Cache.getInstance().putData("groupData", group);
                        Intent intent = new Intent(CreateGroupActivity.this, GroupMainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("groupData", group);
                        startActivity(intent);
                        finish();

                    } else {
                        makeToast("เกิดปัญหาการเชื่อมต่อกับเซิร์ฟเวอร์");
                    }
                }

                @Override
                public void onFailure(Throwable t) {
//                    Log.i("error", t.toString());
                }
            });
        }
    }
    public void makeToast(String text)
    {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
