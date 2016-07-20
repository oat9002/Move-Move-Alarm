package com.fatel.mamtv1;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
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
import com.fatel.mamtv1.Model.Historygroup;
import com.fatel.mamtv1.Model.User;
import com.fatel.mamtv1.RESTService.Implement.EventServiceImp;
import com.fatel.mamtv1.RESTService.Implement.GroupServiceImp;
import com.fatel.mamtv1.Service.EventReceiver;
import com.fatel.mamtv1.Service.Cache;
import com.fatel.mamtv1.Service.Converter;
import com.fatel.mamtv1.Service.HttpConnector;
import com.fatel.mamtv1.Service.UserManage;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Callback;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    @BindView(R.id.profile) TextView header;
    @BindView(R.id.username) TextView user;
    @BindView(R.id.profile_image) CircleImageView profilepic;
    public String id;
    String tempid;
    DBAlarmHelper mAlarmHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mAlarmHelper = new DBAlarmHelper(this);

        if (UserManage.getInstance(this).getCurrentUser().getGroupId() != 0)
            requestGroupInfo();
        Cache.getInstance().putData("MainActivityContext", this);

        // if ((UserManage.getInstance(this).getCurrentUser().getUsername() + "").equals("null"))
        user.setText(UserManage.getInstance(this).getCurrentUser().getFacebookFirstName());
        //else
        //    user.setText(UserManage.getInstance(this).getCurrentUser().getUsername());
        User userData = UserManage.getInstance(this).getCurrentUser();
        tempid = userData.getFacebookId();
        Log.i("fbid", tempid);
        Glide.with(this).load("https://graph.facebook.com/" + tempid + "/picture?type=large").into(profilepic);

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
                setTitle("Profile");
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
            requestUserDayProgressActivity();
        } else {
            Cache.getInstance().putData("userHistory", history);
        }
//
        //historygroup
        Historygroup historygroup = Historygroup.findHistorygroup(UserManage.getInstance(this).getCurrentUser().getGroupId(), this);
        if (historygroup == null && UserManage.getInstance(this).getCurrentUser().getGroupId() != 0) {
            Log.i("historygroup", "success");
            historygroup = new Historygroup(UserManage.getInstance(this).getCurrentUser().getGroupId());
            historygroup.save(this);
            Cache.getInstance().putData("groupHistory", historygroup);
            requestGroupProgressActivity();
        } else if (UserManage.getInstance(this).getCurrentUser().getGroupId() != 0) {
            Cache.getInstance().putData("groupHistory", historygroup);
            //requestSendGroupProgress();
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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
                    Cache cache = Cache.getInstance();
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


    public void makeToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


    public void requestGroupInfo(Class nextActivity) {
        final Class nxtActivity = nextActivity;
        String url = HttpConnector.URL + "group/findByID";
        StringRequest findGroupRequest = new StringRequest(Request.Method.POST, url, //create new string request with POST method
                new Response.Listener<String>() { //create new listener to traces the data
                    @Override
                    public void onResponse(String response) { //when listener is activated
                        Converter converter = Converter.getInstance();
                        Cache cache = Cache.getInstance();
                        HashMap<String, Object> data = converter.JSONToHashMap(response);
                        if ((boolean) data.get("status")) {
                            HashMap<String, Object> groupData = converter.JSONToHashMap(converter.toString(data.get("group")));
                            cache.putData("groupData", groupData);
                            //requestEvent(nxtActivity);
                        } else {
                            makeToast(converter.toString(data.get("description")));
                        }
                    }
                }, new Response.ErrorListener() { //create error listener to trace an error if download process fail
            @Override
            public void onErrorResponse(VolleyError volleyError) { //when error listener is activated
                Log.i("volley", volleyError.toString());
                makeToast("Cannot connect to server or internal server error.");
            }
        }) { //define POST parameters
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>(); //create map to keep variables
                map.put("id", "" + UserManage.getInstance(MainActivity.this).getCurrentUser().getGroupId());

                return map;
            }
        };

        HttpConnector.getInstance(this).addToRequestQueue(findGroupRequest);
    }

    public void requestGroupInfo() {
        String url = HttpConnector.URL + "group/findByID";
        StringRequest findGroupRequest = new StringRequest(Request.Method.POST, url, //create new string request with POST method
                new Response.Listener<String>() { //create new listener to traces the data
                    @Override
                    public void onResponse(String response) { //when listener is activated
                        Converter converter = Converter.getInstance();
                        Cache cache = Cache.getInstance();
                        HashMap<String, Object> data = converter.JSONToHashMap(response);
                        if ((boolean) data.get("status")) {
                            HashMap<String, Object> groupData = converter.JSONToHashMap(converter.toString(data.get("group")));
                            cache.putData("groupData", groupData);
                        } else {
                            makeToast(converter.toString(data.get("description")));
                        }
                    }
                }, new Response.ErrorListener() { //create error listener to trace an error if download process fail
            @Override
            public void onErrorResponse(VolleyError volleyError) { //when error listener is activated
                Log.i("volley", volleyError.toString());
                makeToast("Cannot connect to server or internal server error.");
            }
        }) { //define POST parameters
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<String, String>(); //create map to keep variables
                map.put("id", "" + UserManage.getInstance(MainActivity.this).getCurrentUser().getGroupId());

                return map;
            }
        };

        HttpConnector.getInstance(this).addToRequestQueue(findGroupRequest);
    }

    public void requestEvent() {
        String url = HttpConnector.URL + "event/getEvent";
        StringRequest eventRequest = new StringRequest(Request.Method.GET, url, //create new string request with POST method
                new Response.Listener<String>() { //create new listener to traces the data
                    @Override
                    public void onResponse(String response) { //when listener is activated
                        Converter converter = Converter.getInstance();
                        Cache cache = Cache.getInstance();
                        HashMap<String, Object> data = converter.JSONToHashMap(response);
                        if ((boolean) data.get("status")) {
                            Log.i("event", "" + data.get("event"));
                            HashMap<String, Object> eventData = converter.JSONToHashMap("" + data.get("event"));
                            cache.putData("eventData", eventData);
                            DateFormat dateFormat = new SimpleDateFormat("HH-mm-ss");
                            Date date = null;
                            try {
                                date = dateFormat.parse(converter.toString(eventData.get("time")));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            String time = "" + date.getHours() + "." + date.getMinutes();
                            Intent intent = new Intent(getBaseContext(), EventReceiver.class);
                            Bundle b = new Bundle();
                            b.putString("event", time);
                            intent.putExtras(b);
                            sendBroadcast(intent);

                        } else {
                            makeToast(converter.toString(data.get("description")));
                        }
                    }
                }, new Response.ErrorListener() { //create error listener to trace an error if download process fail
            @Override
            public void onErrorResponse(VolleyError volleyError) { //when error listener is activated
                Log.i("volley", volleyError.toString());
                makeToast("Cannot connect to server or internal server error.");
            }
        });

        HttpConnector.getInstance(this).addToRequestQueue(eventRequest);
    }

    public void requestEvent(Class nextActivity) {
        final Class nxtActivity = nextActivity;
        String url = HttpConnector.URL + "event/getEvent";
        StringRequest eventRequest = new StringRequest(Request.Method.GET, url, //create new string request with POST method
                new Response.Listener<String>() { //create new listener to traces the data
                    @Override
                    public void onResponse(String response) { //when listener is activated
                        Log.i("volley 5", response);
                        Converter converter = Converter.getInstance();
                        Cache cache = Cache.getInstance();
                        HashMap<String, Object> data = converter.JSONToHashMap(response);
                        if ((boolean) data.get("status")) {
                            Log.i("event", "" + data.get("event"));
                            HashMap<String, Object> eventData = converter.JSONToHashMap("" + data.get("event"));
                            cache.putData("eventData", eventData);
                            DateFormat dateFormat = new SimpleDateFormat("HH-mm-ss");
                            Date date = null;
                            try {
                                date = dateFormat.parse(converter.toString(eventData.get("time")));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            String time = "" + date.getHours() + "." + date.getMinutes();
                            Intent intent = new Intent(getBaseContext(), EventReceiver.class);
                            Bundle b = new Bundle();
                            b.putString("event", time);
                            intent.putExtras(b);
                            sendBroadcast(intent);

                            Intent intent3 = new Intent(MainActivity.this, nxtActivity);
                            startActivity(intent3);

                        } else {
                            makeToast(converter.toString(data.get("description")));
                        }
                    }
                }, new Response.ErrorListener() { //create error listener to trace an error if download process fail
            @Override
            public void onErrorResponse(VolleyError volleyError) { //when error listener is activated
                Log.i("volley", volleyError.toString());
                makeToast("Cannot connect to server or internal server error.");
            }
        });

        HttpConnector.getInstance(this).addToRequestQueue(eventRequest);
    }

    private boolean isEmpty(EditText myeditText) {
        return myeditText.getText().toString().trim().length() == 0;
    }

    public void requestUserProgress() {
        String url = HttpConnector.URL + "userProgress/getByUser";
        StringRequest progressRequest = new StringRequest(Request.Method.POST, url, //create new string request with POST method
                new Response.Listener<String>() { //create new listener to traces the data
                    @Override
                    public void onResponse(String response) { //when listener is activated
                        Log.i("volley 8", response);
                        Converter converter = Converter.getInstance();
                        HashMap<String, Object> data = converter.JSONToHashMap(response);
                        if ((boolean) data.get("status")) {
                            HashMap<String, Object> progress = converter.JSONToHashMap(converter.toString(data.get("progress")));
                            HashMap<String, Object> userData = converter.JSONToHashMap(converter.toString(progress.get("user")));

                            History history = (History) Cache.getInstance().getData("userHistory");
                            history.setCancelActivity(converter.toInt(progress.get("cancelActivity")));
                            history.setNumberOfAccept(converter.toInt(progress.get("numberOfAccept")));
                            history.setIdUser(converter.toInt(userData.get("id")));
                            history.save(MainActivity.this);
                        } else {
                            makeToast(converter.toString(data.get("description")));
                        }
                    }
                }, new Response.ErrorListener() { //create error listener to trace an error if download process fail
            @Override
            public void onErrorResponse(VolleyError volleyError) { //when error listener is activated
                Log.i("volley", volleyError.toString());
                makeToast("Cannot connect to server or internal server error.");
            }
        }) { //define POST parameters
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<String, String>(); //create map to keep variables
                HashMap<String, Object> JSON = new HashMap<>();
                JSON.put("user", UserManage.getInstance(MainActivity.this).getCurrentUser().getGeneralValues());
                map.put("JSON", Converter.getInstance().HashMapToJSON(JSON));
                return map;
            }
        };

        HttpConnector.getInstance(this).addToRequestQueue(progressRequest);
    }

    public void requestSendUserProgress() {
        String url = HttpConnector.URL + "userProgress/save";
        StringRequest progressRequest = new StringRequest(Request.Method.POST, url, //create new string request with POST method
                new Response.Listener<String>() { //create new listener to traces the data
                    @Override
                    public void onResponse(String response) { //when listener is activated
                        Log.i("volley", response);
                        Converter converter = Converter.getInstance();
                        HashMap<String, Object> data = converter.JSONToHashMap(response);
                        if (!(boolean) data.get("status"))
                            makeToast(converter.toString(data.get("description")));
                    }
                }, new Response.ErrorListener() { //create error listener to trace an error if download process fail
            @Override
            public void onErrorResponse(VolleyError volleyError) { //when error listener is activated
                Log.i("volley", volleyError.toString());
                makeToast("Cannot connect to server or internal server error.");
            }
        }) { //define POST parameters
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>(); //create map to keep variables
                HashMap<String, Object> JSON = new HashMap<>();
                HashMap<String, Object> progress;

                Log.i("volley", "user request");

                final History history = (History) Cache.getInstance().getData("userHistory");

                progress = history.getGeneralValues();
                JSON.put("user", UserManage.getInstance(MainActivity.this).getCurrentUser().getGeneralValues());
                JSON.put("activityProgress", progress);
                map.put("JSON", Converter.getInstance().HashMapToJSON(JSON));
                return map;
            }
        };

        HttpConnector.getInstance(this).addToRequestQueue(progressRequest);
    }

    public void requestGroupProgressActivity() {
        String url = HttpConnector.URL + "groupProgress/getByGroup";
        StringRequest progressRequest = new StringRequest(Request.Method.POST, url, //create new string request with POST method
                new Response.Listener<String>() { //create new listener to traces the data
                    @Override
                    public void onResponse(String response) { //when listener is activated
                        Log.i("volley", response);
                        Converter converter = Converter.getInstance();
                        HashMap<String, Object> data = converter.JSONToHashMap(response);
                        if ((boolean) data.get("status")) {
                            HashMap<String, Object> progress = converter.JSONToHashMap("" + data.get("progress"));
                            converter.toInt(progress.get("groupID"));
                            converter.toInt(progress.get("totalExerciseTime"));
                            converter.toInt(progress.get("totalActivity"));
                            converter.toInt(progress.get("numberOfAccept"));
                            converter.toInt(progress.get("neck"));
                            converter.toInt(progress.get("shoulder"));
                            converter.toInt(progress.get("chest_back"));
                            converter.toInt(progress.get("wrist"));
                            converter.toInt(progress.get("waist"));
                            converter.toInt(progress.get("hip_leg_calf"));
                        } else {
                            makeToast(converter.toString(data.get("description")));
                        }
                    }
                }, new Response.ErrorListener() { //create error listener to trace an error if download process fail
            @Override
            public void onErrorResponse(VolleyError volleyError) { //when error listener is activated
                Log.i("volley", volleyError.toString());
                makeToast("Cannot connect to server or internal server error.");
            }
        }) { //define POST parameters
            @Override
            protected Map<String, String> getParams() {
                User currentUser = UserManage.getInstance(MainActivity.this).getCurrentUser();
                Map<String, String> map = new HashMap<>(); //create map to keep variables
                HashMap<String, Object> JSON = new HashMap<>();
                HashMap<String, Object> group = new HashMap<>();
                group.put("id", currentUser.getGroupId());
                JSON.put("group", group);
                map.put("JSON", Converter.getInstance().HashMapToJSON(JSON));
                return map;
            }
        };

        HttpConnector.getInstance(this).addToRequestQueue(progressRequest);
    }

    public void requestSendGroupProgress() {
        String url = HttpConnector.URL + "groupProgress/save";
        StringRequest progressRequest = new StringRequest(Request.Method.POST, url, //create new string request with POST method
                new Response.Listener<String>() { //create new listener to traces the data
                    @Override
                    public void onResponse(String response) { //when listener is activated
                        Log.i("volley", response);
                        Log.i("volley", "in sending group request");
                        Converter converter = Converter.getInstance();
                        HashMap<String, Object> data = converter.JSONToHashMap(response);
                        if (!(boolean) data.get("status"))
                            makeToast(converter.toString(data.get("description")));
                    }
                }, new Response.ErrorListener() { //create error listener to trace an error if download process fail
            @Override
            public void onErrorResponse(VolleyError volleyError) { //when error listener is activated
                Log.i("volley", volleyError.toString());
                makeToast("Cannot connect to server or internal server error.");
            }
        }) { //define POST parameters
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>(); //create map to keep variables
                HashMap<String, Object> JSON = new HashMap<>();
                HashMap<String, Object> group = new HashMap<>();
                HashMap<String, Object> progress;

                Log.i("volley", "group request");

                group.put("id", UserManage.getInstance(MainActivity.this).getCurrentUser().getGroupId());

                final Historygroup history = (Historygroup) Cache.getInstance().getData("groupHistory");

                progress = history.getGeneralValues();
                JSON.put("group", group);
                JSON.put("activityProgress", progress);
                Log.i("progress", "" + progress);
                map.put("JSON", Converter.getInstance().HashMapToJSON(JSON));
                return map;
            }
        };

        HttpConnector.getInstance(this).addToRequestQueue(progressRequest);
    }

    public void requestUserDayProgressActivity() {
        final Context context = this.getApplicationContext();
        final User user = UserManage.getInstance(this).getCurrentUser();
        final Converter converter = Converter.getInstance();
        String url = HttpConnector.URL + "userProgress/getByUser"; //url of login API
        StringRequest progressActRequest = new StringRequest(Request.Method.POST, url, //create new string request with POST method
                new Response.Listener<String>() { //create new listener to traces the data
                    @Override
                    public void onResponse(String response) { //when listener is activated
                        HashMap<String, Object> data = converter.JSONToHashMap(response); //convert JSON to HashMap format

                        if ((boolean) data.get("status")) {
                            HashMap<String, Object> progress = converter.JSONToHashMap(converter.toString(data.get("progress")));
                            converter.toInt(progress.get("userID"));
                            converter.toInt(progress.get("totalExerciseTime"));
                            converter.toInt(progress.get("totalActivity"));
                            converter.toInt(progress.get("numberOfAccept"));
                            converter.toInt(progress.get("neck"));
                            converter.toInt(progress.get("shoulder"));
                            converter.toInt(progress.get("chest_back"));
                            converter.toInt(progress.get("wrist"));
                            converter.toInt(progress.get("waist"));
                            converter.toInt(progress.get("hip_leg_calf"));
                        } else {
                            Toast toast = Toast.makeText(context, converter.toString(data.get("description")), Toast.LENGTH_SHORT);
                            toast.show();
                        }


                    }
                }, new Response.ErrorListener() { //create error listener to trace an error if download process fail
            @Override
            public void onErrorResponse(VolleyError volleyError) { //when error listener is activated
                Log.i("volley", volleyError.toString()); //throw the error message to the console
            }
        }) { //define POST parameters
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>(); //create map to keep variables
                HashMap<String, Object> JSON = new HashMap<>();
                JSON.put("user", converter.HashMapToJSON(user.getGeneralValues())); //API variable name
                JSON.put("type", "0");
                map.put("JSON", converter.HashMapToJSON(JSON));
                Log.i("map", map.toString());
                return map;
            }
        };

        HttpConnector.getInstance(context).addToRequestQueue(progressActRequest); //add the request to HTTPConnector, the class will respond the request automatically at separated thread
        requestUserWeekProgressActivity();
    }

    public void requestUserWeekProgressActivity() {
        final Context context = this.getApplicationContext();
        final User user = UserManage.getInstance(this).getCurrentUser();
        final Converter converter = Converter.getInstance();
        String url = HttpConnector.URL + "userProgress/getByUser"; //url of login API
        StringRequest progressActRequest = new StringRequest(Request.Method.POST, url, //create new string request with POST method
                new Response.Listener<String>() { //create new listener to traces the data
                    @Override
                    public void onResponse(String response) { //when listener is activated
                        HashMap<String, Object> data = converter.JSONToHashMap(response); //convert JSON to HashMap format

                        if ((boolean) data.get("status")) {
                            HashMap<String, Object> progress = converter.JSONToHashMap(converter.toString(data.get("progress")));
                            converter.toInt(progress.get("userID"));
                            converter.toInt(progress.get("totalExerciseTime"));
                            converter.toInt(progress.get("totalActivity"));
                            converter.toInt(progress.get("numberOfAccept"));
                            converter.toInt(progress.get("neck"));
                            converter.toInt(progress.get("shoulder"));
                            converter.toInt(progress.get("chest_back"));
                            converter.toInt(progress.get("wrist"));
                            converter.toInt(progress.get("waist"));
                            converter.toInt(progress.get("hip_leg_calf"));
                        } else {
                            Toast toast = Toast.makeText(context, converter.toString(data.get("description")), Toast.LENGTH_SHORT);
                            toast.show();
                        }


                    }
                }, new Response.ErrorListener() { //create error listener to trace an error if download process fail
            @Override
            public void onErrorResponse(VolleyError volleyError) { //when error listener is activated
                Log.i("volley", volleyError.toString()); //throw the error message to the console
            }
        }) { //define POST parameters
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>(); //create map to keep variables
                HashMap<String, Object> JSON = new HashMap<>();
                JSON.put("user", converter.HashMapToJSON(user.getGeneralValues())); //API variable name
                JSON.put("type", "1");
                map.put("JSON", converter.HashMapToJSON(JSON));
                Log.i("map", map.toString());
                return map;
            }
        };

        HttpConnector.getInstance(context).addToRequestQueue(progressActRequest); //add the request to HTTPConnector, the class will respond the request automatically at separated thread
    }
}