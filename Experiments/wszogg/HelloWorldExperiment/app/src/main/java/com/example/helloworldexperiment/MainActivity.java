package com.example.helloworldexperiment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText userNameGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userNameGet = (EditText) findViewById(R.id.userNameField);
    }

    public void changeActivity(View view) {
        userInfo.userName = userNameGet.getText().toString();
        Intent intent = new Intent(this, HelloDisplay.class);
        startActivity(intent);
    }
}