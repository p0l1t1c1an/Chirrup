package com.example.a309_demo1_experiments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
    }

    public void changeMessageBucs(View view) {
        Button button = (Button) findViewById(R.id.BucsBut);
        TextView text = (TextView) findViewById(R.id.bowlText);
        text.setText("You think the Chiefs will win!");
    }

    public void changeMessageChiefs(View view) {
        Button button = (Button) findViewById(R.id.ChiefsBut);
        TextView text = (TextView) findViewById(R.id.bowlText);
        text.setText("You think the Bucs will win!");
    }
}