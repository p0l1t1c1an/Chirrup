package com.example.helloworldexperiment;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class HelloDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_display);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView nameBar = (TextView) findViewById(R.id.nameDisplay);
        nameBar.setText("Hello " + userInfo.userName + ", have a wonderful day!");
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }
}