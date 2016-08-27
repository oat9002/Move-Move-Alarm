package com.fatel.mamtv1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.fatel.mamtv1.Model.Group;
import com.fatel.mamtv1.RESTService.Implement.GroupServiceImp;
import com.fatel.mamtv1.Service.Cache;
import com.fatel.mamtv1.Service.Converter;
import com.fatel.mamtv1.Service.UserManage;


import retrofit.Callback;
import retrofit.Retrofit;

public class JoinGroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_group);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_join_group, menu);
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

    public void joinGroup(View view) {
        BootstrapEditText groupCode = (BootstrapEditText) findViewById(R.id.edit_message);

        if(groupCode==null || groupCode.getText().toString().length() < 4) {
            makeToast("กรุณาใส่ให้ครบ 4 จำนวน");
            return;
        }

        final int groupID = Converter.toInt(groupCode.getText().toString());
        GroupServiceImp.getInstance().joinGroup(groupID, UserManage.getCurrentUser(), new Callback<Group>() {
            @Override
            public void onResponse(retrofit.Response<Group> response, Retrofit retrofit) {
                Log.i("raw json", response.raw().toString());
                UserManage.getCurrentUser().setGroupId(response.body().getId());
                Group group = response.body();
                Cache.getInstance().putData("groupData", group);
                Intent intent = new Intent(JoinGroupActivity.this, GroupMainActivity.class);
                intent.putExtra("groupData", group);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public void makeToast(String text)
    {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
