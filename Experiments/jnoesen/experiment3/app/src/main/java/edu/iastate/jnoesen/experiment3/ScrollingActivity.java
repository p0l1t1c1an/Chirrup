package edu.iastate.jnoesen.experiment3;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Scrolling activity, for like a message app
 *
 * @author Jeremy Noesen
 */
public class ScrollingActivity extends AppCompatActivity {

    /**
     * number of messages created
     */
    private int count;

    /**
     * initialize the scrolling layout, populating it with 100 messages
     *
     * @param savedInstanceState
     */
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