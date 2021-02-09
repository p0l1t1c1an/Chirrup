package edu.iastate.jnoesen.experiment3;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.LinearLayout;
import android.widget.TextView;

public class ScrollingActivity extends AppCompatActivity {

    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        for(int i = 0; i < 100; i++) {
            addMessage();
        }
    }

    /**
     * add a new message to the scrolling layout
     */
    private void addMessage() {
        TextView textView = new TextView(this);
        textView.setText("This is a new message " + count + "\n");

        LinearLayout linearLayout = findViewById(R.id.linear_layout);
        linearLayout.addView(textView);

        count++;
    }
}