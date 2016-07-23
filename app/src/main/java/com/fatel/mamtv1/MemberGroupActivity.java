package com.fatel.mamtv1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.fatel.mamtv1.Model.Group;
import com.fatel.mamtv1.Model.User;
import com.fatel.mamtv1.Service.Cache;
import com.fatel.mamtv1.Service.Converter;

import java.util.ArrayList;
import java.util.HashMap;

public class MemberGroupActivity extends AppCompatActivity {
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_group);
        Group groupData = (Group) getIntent().getSerializableExtra("groupData");
        for(int i = 1; i <= groupData.getMembers().size(); i++) {
            int groupMemberId = getResources().getIdentifier("groupMember_member" + i, "id", this.getPackageName());
            ((TextView) findViewById(groupMemberId)).setText(groupData.getMembers().get(i - 1).getFacebookFirstName());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_member_group, menu);
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
}
