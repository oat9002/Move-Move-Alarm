package com.fatel.mamtv1;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

public class UserManage {

    private static User currentUser = null;
    private static UserManage instance = null;
    private UserManage(){

    }
    public static UserManage getInstance(Context context) {
        if (instance == null) {
            instance = new UserManage();
            User user = User.checkLogin(context);
            if(user!=null){
                currentUser = user;
            }
        }
        return instance;
    }

    public User getCurrentUser(){
        return currentUser;
    }
    public void setCurrentUser(User user){
       currentUser= user;
    }
    /*
    public void loginUser (String username,String password, final Context context){

        String url = HttpConnector.URL + "user/login"; //url of login API
        final String un = username;
        final String pw = password;
        StringRequest loginRequest = new StringRequest(Request.Method.POST, url, //create new string request with POST method
                new Response.Listener<String>() { //create new listener to traces the data
                    @Override
                    public void onResponse(String response) { //when listener is activated
                        Converter converter = Converter.getInstance();
                        Context context = (Context) Cache.getInstance().getData("loginContext");
                        HashMap<String, Object> data = converter.JSONToHashMap(response); //convert JSON to HashMap format


                        if((boolean) data.get("status")) {
                            HashMap<String, Object> userData = converter.JSONToHashMap(converter.toString(data.get("user")));
                            HashMap<String, Object> groupData = converter.JSONToHashMap(converter.toString(data.get("group")));
                            Cache.getInstance().putData("groupData", groupData);
                            int idUser = converter.toInt(userData.get("id"));
                            String username = converter.toString(userData.get("userName"));
                            User user;
                            if((User.find(idUser, context))!= null){
                                user=User.find(idUser, context);
                                UserManage.getInstance(context).setCurrentUser(user);
                            }
                            else{
                                user = new User(idUser, username);
                                UserManage.getInstance(context).setCurrentUser(user);
                            }
                            user.setFirstName(converter.toString(userData.get("firstName")));
                            user.setLastName(converter.toString(userData.get("lastName")));
                            user.setUserName(username);
                            user.setAge(converter.toInt(userData.get("age")));
                            user.setScore(converter.toInt(userData.get("score")));
                            user.setGender(converter.toInt(userData.get("gender")));
                            user.setEmail(converter.toString(userData.get("email")));
                            user.setFacebookID(converter.toString(userData.get("facebookID")));
                            user.setFacebookFirstName(converter.toString(userData.get("facebookFirstName")));
                            user.setFacebookLastName(converter.toString(userData.get("facebookLastName ")));

                            if(groupData != null)
                                user.setIdGroup(converter.toInt(groupData.get("id")));

                            user.setLogin(1);
                            user.save(context);

                            Toast toast = Toast.makeText(context, "Hello "+username, Toast.LENGTH_SHORT);
                            toast.show();
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            context.startActivity(intent);
                            if(Login_Activity.instance != null)
                                Login_Activity.instance.finish();
                        }
                        else {
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
                map.put("userName", un); //API variable name
                map.put("password", pw);

                return map;
            }
        };

        HttpConnector.getInstance(context).addToRequestQueue(loginRequest); //add the request to HTTPConnector, the class will respond the request automatically at separated thread

    }
    */
    public void loginFBUser(String facebookID,String facebookFirstName,Context context){

        String url = HttpConnector.URL + "login"; //url of login API
        final String id = facebookID;
        final String name = facebookFirstName;
        StringRequest loginFBRequest = new StringRequest(Request.Method.POST, url, //create new string request with POST method
                new Response.Listener<String>() { //create new listener to traces the data
                    @Override
                    public void onResponse(String response) { //when listener is activated
                        Converter converter = Converter.getInstance();
                        Context context = (Context) Cache.getInstance().getData("loginFBContext");
                        HashMap<String, Object> data = converter.JSONToHashMap(response); //convert JSON to HashMap format
                        Log.i("response", data.toString());
                        if((boolean) data.get("status")) {
                            HashMap<String, Object> userData = converter.JSONToHashMap(converter.toString(data.get("user")));
                            HashMap<String, Object> groupData = null;
                            if(data.get("group") != null) {
                                groupData = converter.JSONToHashMap(converter.toString(data.get("group")));
                            }

                            Cache.getInstance().putData("groupData", groupData);
                            int idUser = converter.toInt(userData.get("id"));
                            String facebookID = converter.toString(userData.get("facebookID"));
                            String facebookFirstName = converter.toString(userData.get("facebookFirstName"));
                            User user=User.find(idUser, context);
                            if(user != null){

                                user = User.find(idUser, context);
                            }
                            else{
                                user = new User(idUser, facebookID,facebookFirstName);

                            }
                            user.setBirthDay(converter.toString(userData.get("birthDay")));
                            user.setAge(converter.toInt(userData.get("age")));
                            user.setScore(converter.toInt(userData.get("score")));
                            user.setHeight(converter.toInt(userData.get("height")));
                            user.setWeight(converter.toInt(userData.get("weight")));
                            user.setWaistline(converter.toInt(userData.get("waistline")));
                            user.setBmi(converter.toDouble(userData.get("bmi")));
                            user.setEmail(converter.toString(userData.get("email")));
                            if(groupData != null)
                                user.setIdGroup(converter.toInt(groupData.get("id")));
                            user.setFacebookID(converter.toString(userData.get("facebookID")).substring(2));
                            user.setFacebookFirstName(converter.toString(userData.get("facebookFirstName")));
                            user.setFacebookLastName(converter.toString(userData.get("facebookLastName ")));
                            user.setLogin(1);
                            user.save(context);

                            if(UserManage.currentUser == null) {
                                Toast toast = Toast.makeText(context, "สวัสดี " + facebookFirstName, Toast.LENGTH_SHORT);
                                toast.show();
                                UserManage.getInstance(context).setCurrentUser(user);
                                Intent intent = new Intent(context, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                context.startActivity(intent);
                            }
                            if(Login_Activity.instance != null)
                                Login_Activity.instance.finish();
                        }
                        else {
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
                map.put("facebookID", "fb" + id); //API variable name
                map.put("facebookFirstName", name);

                Log.i("map", map.toString());

                return map;
            }
        };

        HttpConnector.getInstance(context).addToRequestQueue(loginFBRequest); //add the request to HTTPConnector, the class will respond the request automatically at separated thread

    }
    public void logoutUser(Context context){
        currentUser.setLogin(0);
        currentUser.save(context);
        currentUser=null;
    }
    public boolean checkCurrentLogin(Context context){
        User user = User.checkLogin(context);
        if(user!=null){
            return true;
        }

        return false;

    }

/*
    public void createNewUser(String username,String password, final Context context){

        String url = HttpConnector.URL + "user/createUser";
        final String un = username;
        final String pw = password;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() { //create new request
            @Override
            public void onResponse(String s) { //when the results have come
                if (s!=null) {
                    Converter converter = Converter.getInstance();
                    Context context = (Context) Cache.getInstance().getData("CreateAccountContext");
                    Log.i("volley ", s); //throw the result to the console.
                    HashMap<String, Object> data = converter.JSONToHashMap(s);
                    HashMap<String, Object> userData = converter.JSONToHashMap(converter.toString(data.get("user")));

                    if((boolean) data.get("status")) {
                        int idUser = converter.toInt(userData.get("id"));
                        String username = converter.toString(userData.get("userName"));

                        User currentUser = new User(idUser, username);
                        currentUser.setLogin(1);
                        currentUser.save(context);
                        UserManage.getInstance(context).setCurrentUser(currentUser);

                        context.startActivity(new Intent(context, MainActivity.class));
                    }
                    else {
                        Toast toast = Toast.makeText(context, converter.toString(data.get("description")), Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                else
                    Log.i("volley ", "s==null");

            }
        }, new Response.ErrorListener() { //create error listener to catch when the error has occurred
            @Override
            public void onErrorResponse(VolleyError volleyError) { //when the error that the server cannot handle by itself has occurred
                Log.i("volley error", volleyError.toString()); //show the error

            }
        }) {
            @Override //override the send parameters to server manually by POST method
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, Object> user = new HashMap<>(); //create HashMap to keep all the values in one place to be 1 object
                user.put("userName", un);
                user.put("password", pw);
                user.put("facebookID","0");
                Map<String, String> JSON = new HashMap<>(); //create HashMap again to keep the above user object
                JSON.put("JSON", Converter.getInstance().HashMapToJSON(user)); //the API receive the values in one parameter name JSON
                return JSON; //send the value name JSON to the server
            }
        }; //end of request's details
        HttpConnector.getInstance(context).addToRequestQueue(stringRequest); //add the request to HTTPConnector, the class will respond the request automatically at separated thread

    }*/
    private int addNewUserFB(String facebookID,String facebookFirstName){
        return 0;
    }
    private int findUserFB(String facebookID,String facebookFirstName){
        return 0;
    }



    public int checkUser(String username,String password) {
        return 1;
    }


    public void updateUser(final Context context){
        String url = HttpConnector.URL + "user/updateUser";
        StringRequest updateUserDataRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() { //create new request
            @Override
            public void onResponse(String response) { //when the results have come
                Converter converter = Converter.getInstance();
                HashMap<String, Object> data = converter.JSONToHashMap(response);
                Log.i("volley 1", response); //throw the result to the console.
                if((boolean) data.get("status"))
                    Toast.makeText(context, "Data is already updated.", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(context, converter.toString(data.get("description")), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() { //create error listener to catch when the error has occurred
            @Override
            public void onErrorResponse(VolleyError volleyError) { //when the error that the server cannot handle by itself has occurred
                Log.i("volley error", volleyError.toString()); //show the error
            }
        }) {
            @Override //override the send parameters to server manually by POST method
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                HashMap<String, Object> user = UserManage.currentUser.getGeneralValues(); //create HashMap to keep all the values in one place to be 1 object
                HashMap<String, Object> JSON = new HashMap<>(); //create HashMap again to keep the above user object

                JSON.put("user", user); //the API receive the values in one parameter name JSON

                Log.i("user", "" + user);

                map.put("JSON", Converter.getInstance().HashMapToJSON(JSON));

                return map; //send the value name JSON to the server
            }
        };

        HttpConnector.getInstance(context).addToRequestQueue(updateUserDataRequest);
    }

    // get set
    public void addScore(int score,Context context){
        if(currentUser!=null){
            currentUser.addScore(score);
            currentUser.save(context);
            updateUser(context);
        }
    }


}