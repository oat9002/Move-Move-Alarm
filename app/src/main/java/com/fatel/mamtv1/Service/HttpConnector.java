package com.fatel.mamtv1.Service;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fatel.mamtv1.LoginActivity;
import com.fatel.mamtv1.MainActivity;
import com.fatel.mamtv1.Model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class HttpConnector {
    private static HttpConnector instance;
    private RequestQueue requestQueue;
    private static Context context;
    public static String URL = "http://enceladusiapetus.me:8001/";
    public static final String LOGIN = URL + "login";
    public static final String USER_UPDATE = URL + "user/update";
    //public static String URL = "http://13.76.94.234:8080/";

    private HttpConnector(Context context) {
        this.context = context;
    }

    public static synchronized HttpConnector getInstance(Context context) {
        if (instance == null) {
            instance = new HttpConnector(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }

    public void updateUserInfo(final User user) {
        JsonArrayRequest test = new JsonArrayRequest(Request.Method.POST, USER_UPDATE, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("json response", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("json error", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                HashMap<String, Object> userInfo = user.getGeneralValues(); //create HashMap to keep all the values in one place to be 1 object
                map.put("user", Converter.getInstance().HashMapToJSON(userInfo));
                map.put("test", "test");
                Log.i("request", map.toString());
                return map;
            }

            @Override
            protected Response parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                    return Response.success(new JSONObject(jsonString),
                            HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (JSONException je) {
                    return Response.error(new ParseError(je));
                }
            }
        };

        StringRequest updateUserDataRequest = new StringRequest(Request.Method.POST, USER_UPDATE, new Response.Listener<String>() { //create new request
            @Override
            public void onResponse(String response) { //when the results have come
                Log.i("response", response);
                try {
                    Converter converter = Converter.getInstance();
                    HashMap<String, Object> data = converter.JSONToHashMap(response);
                    if ((boolean) data.get("status"))
                        Toast.makeText(context, "Data is already updated.", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(context, converter.toString(data.get("description")), Toast.LENGTH_SHORT).show();
                }
                catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() { //create error listener to catch when the error has occurred
            @Override
            public void onErrorResponse(VolleyError volleyError) { //when the error that the server cannot handle by itself has occurred
                Log.i("volley error", new String(volleyError.networkResponse.data)); //show the error
            }
        }) {
            @Override //override the send parameters to server manually by POST method
            public byte[] getBody() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                HashMap<String, Object> userInfo = user.getGeneralValues(); //create HashMap to keep all the values in one place to be 1 object
                map.put("user", Converter.getInstance().HashMapToJSON(userInfo));
                map.put("test", "test");
                Log.i("request", map.toString());

                byte[] data = new byte[0];
                try {
                    data = Converter.getInstance().HashMapToJSON(userInfo).getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    Log.i("req error", e.toString());
                }
                return data; //send the value name JSON to the server
            }
        };

        this.addToRequestQueue(test);
    }

    public void loginFBUser(final String facebookID, final String facebookFirstName) {
        StringRequest loginFBRequest = new StringRequest(Request.Method.POST, LOGIN, //create new string request with POST method
                new Response.Listener<String>() { //create new listener to traces the data
                    @Override
                    public void onResponse(String response) { //when listener is activated
                        Converter converter = Converter.getInstance();
                        Log.i("response", response);
                        HashMap<String, Object> data = converter.JSONToHashMap(response); //convert JSON to HashMap format
                        if ((boolean) data.get("status")) {
                            HashMap<String, Object> userData = converter.JSONToHashMap(converter.toString(data.get("user")));
                            if (data.get("group") != null)
                                userData.put("groupId", converter.JSONToHashMap(converter.toString(data.get("group"))));
                            Cache.getInstance().putData("groupData", data.get("group"));
                            if (UserManage.getInstance(context).getCurrentUser() == null) {
                                Toast.makeText(context, "สวัสดี " + userData.get("facebookFirstName"), Toast.LENGTH_SHORT).show();
                                UserManage.getInstance(context).setCurrentUser((new User()).setData(userData, context));
                                Intent intent = new Intent(context, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                context.startActivity(intent);
                            }
                            if (LoginActivity.instance != null) LoginActivity.instance.finish();
                        } else
                            Toast.makeText(context, converter.toString(data.get("description")), Toast.LENGTH_SHORT).show();
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
                map.put("facebookID", "fb" + facebookID); //API variable name
                map.put("facebookFirstName", facebookFirstName);
                return map;
            }
        };
        this.addToRequestQueue(loginFBRequest); //add the request to HTTPConnector, the class will respond the request automatically at separated thread
    }
}
