package com.fatel.mamtv1;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.bumptech.glide.Glide;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.fatel.mamtv1.Fragment.AboutFragment;
import com.fatel.mamtv1.Fragment.AlarmFragment;
import com.fatel.mamtv1.Fragment.ChoosePostureFragment;
import com.fatel.mamtv1.Fragment.GroupFragment;
import com.fatel.mamtv1.Fragment.HelpFragment;
import com.fatel.mamtv1.Fragment.LoadingFragment;
import com.fatel.mamtv1.Fragment.MainFragment;
import com.fatel.mamtv1.Fragment.ProfileFragment;
import com.fatel.mamtv1.Helper.DBAlarmHelper;
import com.fatel.mamtv1.Model.Event;
import com.fatel.mamtv1.Model.Group;
import com.fatel.mamtv1.Model.History;
import com.fatel.mamtv1.Model.GroupHistory;
import com.fatel.mamtv1.Model.User;
import com.fatel.mamtv1.Service.Cache;
import com.fatel.mamtv1.Service.UserManage;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    @BindView(R.id.profile) TextView header;
    @BindView(R.id.username) TextView user;
    @BindView(R.id.profile_image)
    BootstrapCircleThumbnail profilepic;
    public String id;
    String tempid;
    DBAlarmHelper mAlarmHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mAlarmHelper = new DBAlarmHelper(this);

        Cache.getInstance().putData("MainActivityContext", this);

        // if ((UserManage.getInstance(this).getCurrentUser().getUsername() + "").equals("null"))
        user.setText(UserManage.getInstance(this).getCurrentUser().getFacebookFirstName());
        //else
        //    user.setText(UserManage.getInstance(this).getCurrentUser().getUsername());
        User userData = UserManage.getInstance(this).getCurrentUser();
        tempid = userData.getFacebookId();
        Log.i("fbid", tempid);
        Picasso.with(this).load("https://graph.facebook.com/" + tempid + "/picture?type=large").into(profilepic);

        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        MainFragment fragobj = new MainFragment();
        tx.replace(R.id.container, fragobj);
        tx.commit();
        NavigationView nvDrawer = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                Class fragmentClass;
                fragmentClass = ProfileFragment.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Insert the fragment by replacing any existing fragment
                FragmentManager fragmentManager = getSupportFragmentManager();//getActivity()
                FragmentTransaction tx = fragmentManager.beginTransaction();
                tx.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                tx.addToBackStack(null);
                tx.replace(R.id.container, fragment).commit();

                // Highlight the selected item, update the title, and close the drawer
                //setTitle("Profile");
                mDrawerLayout.closeDrawers();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_18dp);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        //history
        History history = History.findHistory(UserManage.getInstance(this).getCurrentUser().getId(), this);
        if (history == null) {
            history = new History(UserManage.getInstance(this).getCurrentUser().getId());
            history.save(this);
            Cache.getInstance().putData("userHistory", history);
        } else {
            Cache.getInstance().putData("userHistory", history);
        }
//
        //historygroup
        GroupHistory historygroup = GroupHistory.findHistorygroup(UserManage.getInstance(this).getCurrentUser().getGroupId(), this);
        if (historygroup == null && UserManage.getInstance(this).getCurrentUser().getGroupId() != 0) {
            Log.i("historygroup", "success");
            historygroup = new GroupHistory(UserManage.getInstance(this).getCurrentUser().getGroupId());
            historygroup.save(this);
            Cache.getInstance().putData("groupHistory", historygroup);
        } else if (UserManage.getInstance(this).getCurrentUser().getGroupId() != 0) {
            Cache.getInstance().putData("groupHistory", historygroup);
        }


        if (mAlarmHelper.checkdata() != 1) {
            //switch to AlarmFragment
            Fragment fragment = null;
            Class fragmentClass;
            fragmentClass = AlarmFragment.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();//getActivity()
            FragmentTransaction tx2 = fragmentManager.beginTransaction();
            tx2.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            tx2.addToBackStack(null);
            tx2.replace(R.id.container, fragment).commit();

            // Highlight the selected item, update the title, and close the drawer
            setTitle("ตั้งค่าการแจ้งเตือน");
            mDrawerLayout.closeDrawers();

            Toast.makeText(this, "กรุณาตั้งค่าการแจ้งเตือนก่อนใช้งาน", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment fragment = null;
        Class fragmentClass = null;
        int id = item.getItemId();

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the planet to show based on
        // position
        Fragment fragment = null;

        Class fragmentClass = null;
        switch (menuItem.getItemId()) {
            case R.id.nav_home_fragment:
                fragmentClass = MainFragment.class;
                break;
            case R.id.nav_alarm_fragment:
                fragmentClass = AlarmFragment.class;
                break;
            case R.id.nav_posture_fragment:
                fragmentClass = ChoosePostureFragment.class;
                break;
            case R.id.nav_progress_fragment:
                fragmentClass = null;
                mDrawerLayout.closeDrawers();
                Intent intent4 = new Intent(this, ProgressActivity.class);
                startActivity(intent4);
                break;
            case R.id.nav_lboard_fragment:
                fragmentClass = null;
                mDrawerLayout.closeDrawers();
                Intent intent2 = new Intent(this, ScoreboardActivity.class);
                startActivity(intent2);
                break;
            case R.id.nav_help_fragment:
                fragmentClass = HelpFragment.class;
                break;
            case R.id.nav_about_fragment:
                fragmentClass = AboutFragment.class;
                break;
            case R.id.nav_group_fragment:
                User currentUser = UserManage.getInstance(this).getCurrentUser();
                if (currentUser.getGroupId() == 0)
                    fragmentClass = GroupFragment.class;
                else {
                    Intent intent = new Intent(this, GroupMainActivity.class);
                    intent.putExtra("groupData", (Group) Cache.getInstance().getData("groupData"));
                    intent.putExtra("eventData", (Event) Cache.getInstance().getData("eventData"));
                    startActivity(intent);
                }
                break;

            case R.id.nav_logout_fragment:
                fragmentClass = null;
                mAlarmHelper = new DBAlarmHelper(this);


                if (!tempid.equals("0.0")) {
                    FacebookSdk.sdkInitialize(this.getApplicationContext());
                    LoginManager.getInstance().logOut();
                }

                UserManage.getInstance(this).logoutUser(this);


                mAlarmHelper.deleteSetAlarm("1");
                mDrawerLayout.closeDrawers();
                Intent intent = new Intent(this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                break;
            default:
                fragmentClass = MainFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (fragmentClass != null) {
            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();//getActivity()
            FragmentTransaction tx = fragmentManager.beginTransaction();
            if (fragmentClass != MainFragment.class) {
                tx.addToBackStack(null);
            }
            if (fragmentClass != LoadingFragment.class) {
                tx.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                tx.replace(R.id.container, fragment).commit();
            }
            // Highlight the selected item, update the title, and close the drawer

            if (menuItem.getTitle().equals("Home"))
                setTitle("Move Alarm");
            else if(menuItem.getTitle().equals("Posture"))
                setTitle("หมวดท่าบริหาร");
            else
                setTitle(menuItem.getTitle());
            mDrawerLayout.closeDrawers();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }


    public void linkCreateGroup(View view) {

        Intent intent = new Intent(this, CreateGroupActivity.class);
        startActivity(intent);
    }

    public void linkJoinGroup(View view) {
        Intent intent = new Intent(this, JoinGroupActivity.class);

        startActivity(intent);

    }

    public ActionBar getsupportactionbar() {
        ActionBar mActionBar = getSupportActionBar();
        return mActionBar;
    }
}