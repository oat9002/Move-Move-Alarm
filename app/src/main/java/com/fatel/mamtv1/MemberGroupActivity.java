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
        ArrayList<TextView> membersView = new ArrayList<>();
        membersView.add((TextView) findViewById(R.id.groupMember_member1));
        membersView.add((TextView)findViewById(R.id.groupMember_member2));
        membersView.add((TextView)findViewById(R.id.groupMember_member3));
        membersView.add((TextView)findViewById(R.id.groupMember_member4));
        membersView.add((TextView)findViewById(R.id.groupMember_member5));
        membersView.add((TextView)findViewById(R.id.groupMember_member6));
        membersView.add((TextView)findViewById(R.id.groupMember_member7));
        membersView.add((TextView)findViewById(R.id.groupMember_member8));
        membersView.add((TextView)findViewById(R.id.groupMember_member9));
        membersView.add((TextView)findViewById(R.id.groupMember_member10));
        Group groupData = (Group) getIntent().getSerializableExtra("groupData");
        for(int i = 0; i < groupData.getAmountMember(); i++)
            membersView.get(i).setText(groupData.getMembers().get(i).getFacebookFirstName());
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
