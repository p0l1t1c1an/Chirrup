package com.example.profilesettings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    void sendPerson(JSONObject obj) throws JSONException {
        obj.put("username", currentUserData.currUser.getUserName());
        obj.put("firstname", currentUserData.currUser.getFirstName());
        obj.put("lastname", currentUserData.currUser.getLastName());
        obj.put("bio", currentUserData.currUser.getBio());

        RequestQueue queue = Volley.newRequestQueue(this);
    }

}