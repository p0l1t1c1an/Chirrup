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

        try {
            JSONObject obj = new JSONObject();
            obj.put("username", "William");
            obj.put("email", )
            sendRequest(obj);
        } catch (JSONException e) {

        }

    }

    void sendRequest(JSONObject obj) {
        RequestQueue
    }

}