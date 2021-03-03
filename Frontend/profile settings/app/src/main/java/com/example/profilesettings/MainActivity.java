package com.example.profilesettings;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JSONObject blank = new JSONObject();
        try {
//            blank.put("username", currentUserData.currUser.getUserName());
//            blank.put("firstname", currentUserData.currUser.getFirstName());
//            blank.put("lastname", currentUserData.currUser.getLastName());
//            blank.put("biography", currentUserData.currUser.getBio());
            getPerson(6);
        }
        catch (JSONException e) {

        }
    }

    void getPerson(int userId) throws JSONException {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String baseUrl = getResources().getString(R.string.base_url);
        String url = baseUrl + "/user/" + userId;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    String responseBody = new String(response.data, StandardCharsets.UTF_8);
                    TextView view = findViewById(R.id.textView);
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };

        requestQueue.add(stringRequest);
    }


    void sendPerson(JsonObject obj) throws JSONException {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String baseUrl = getResources().getString(R.string.base_url);
        String url = baseUrl + userId;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    String responseBody = new String(response.data, StandardCharsets.UTF_8);
                    TextView view = findViewById(R.id.textView);
                    JSONObject response = responseBody.
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };

        requestQueue.add(stringRequest);
    }
}