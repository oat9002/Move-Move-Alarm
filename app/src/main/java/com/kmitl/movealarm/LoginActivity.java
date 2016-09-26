package com.kmitl.movealarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.kmitl.movealarm.Helper.DBAlarmHelper;
import com.kmitl.movealarm.Helper.UserHelper;
import com.kmitl.movealarm.Model.Event;
import com.kmitl.movealarm.Model.Group;
import com.kmitl.movealarm.Model.User;
import com.kmitl.movealarm.RESTService.Implement.EventServiceImp;
import com.kmitl.movealarm.RESTService.Implement.GroupServiceImp;
import com.kmitl.movealarm.RESTService.Implement.UserServiceImp;
import com.kmitl.movealarm.Service.Cache;
import com.kmitl.movealarm.Service.UserManage;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit.Callback;
import retrofit.Retrofit;

public class LoginActivity extends AppCompatActivity {
    public static final String CANNOT_LOGIN = "ไม่สามารถเข้าสู่ระบบได้";
    public static final String CANNOT_LOGIN_TO_FACEBOOK = "ไม่สามารถเชื่อมต่อกับ Facebook ได้";
    private PendingIntent pendingIntent;
    private AlarmManager manager;
    private DBAlarmHelper mAlarmHelper;
    public static LoginActivity instance;
    CallbackManager callbackManager;
    ProfileTracker profileTracker;

    @Override
    public void finish()
    {
        super.finish();
        instance = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        instance = this;
        super.onCreate(savedInstanceState);
//        Log.i("checkCurrentLogin",UserManage.getInstance(this).checkCurrentLogin(this)+"");
        if(UserManage.getInstance(this).checkCurrentLogin(this))
        {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.fatel.mamtv1",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        FacebookSdk.sdkInitialize(this.getApplicationContext());

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        linkWithFB();
                    }

                    @Override
                    public void onCancel() {
                        boolean loggedIn = AccessToken.getCurrentAccessToken() != null;
                        Profile profile = Profile.getCurrentProfile();
                    }

                    @Override
                    public void onError(FacebookException e) {
                        boolean loggedIn = AccessToken.getCurrentAccessToken() != null;
                        Profile profile = Profile.getCurrentProfile();
                        Snackbar.make(findViewById(R.id.fb_btn), CANNOT_LOGIN_TO_FACEBOOK, Snackbar.LENGTH_SHORT).show();
                    }
                });

        setContentView(R.layout.login_layout);
        getSupportActionBar().hide();
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile profile, Profile profile1) {
                linkWithFB();
            }
        };
        mAlarmHelper = new DBAlarmHelper(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void linkWithFB()
    {
        boolean loggedIn = AccessToken.getCurrentAccessToken() != null;
        Profile profile = Profile.getCurrentProfile();

        Cache.getInstance().putData("loginFBContext", this);
        if (loggedIn && (profile != null)) {
            UserServiceImp.getInstance().login(profile.getId(), profile.getFirstName(), new Callback<User>() {
                @Override
                public void onResponse(retrofit.Response<User> response, Retrofit retrofit) {
                    User user = response.body();
                    if(user == null)
                        return;
//                    Log.i("response", response.raw().toString());
                    user.setLogin(1);
                    user.save(getApplicationContext());
                    user.getDailyProgress().save(getApplicationContext(),1);
                    user.getWeeklyProgress().save(getApplicationContext(),0);

                    UserManage.getInstance(getApplicationContext()).setCurrentUser(user);
                    UserHelper userHelper = new UserHelper(getApplicationContext());

                    Intent intent = new Intent(getApplicationContext(), AgreementActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    getApplicationContext().startActivity(intent);
                    GroupServiceImp.getInstance().findByUser(user, new Callback<Group>() {
                        @Override
                        public void onResponse(retrofit.Response<Group> response, Retrofit retrofit) {
                            Cache.getInstance().putData("groupData", response.body());
                        }

                        @Override
                        public void onFailure(Throwable t) {
//                            Log.i("error", t.getMessage());
                        }
                    });

                    EventServiceImp.getInstance().getEvent(new Callback<Event>() {
                        @Override
                        public void onResponse(retrofit.Response<Event> response, Retrofit retrofit) {
                            Cache.getInstance().putData("eventData", response.body());
                        }

                        @Override
                        public void onFailure(Throwable t) {
//                            Log.i("error", t.getMessage());
                            Snackbar.make(findViewById(R.id.fb_btn), CANNOT_LOGIN, Snackbar.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onFailure(Throwable t) {
//                    Log.i("error", t.getMessage());
                    Snackbar.make(findViewById(R.id.fb_btn), CANNOT_LOGIN, Snackbar.LENGTH_SHORT).show();
                }
            });
        }
    }
}
