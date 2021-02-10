package edu.iastate.jnoesen.experiment3;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

/**
 * Scrolling activity, for like a message app
 *
 * @author Jeremy Noesen
 */
public class ScrollingActivity extends AppCompatActivity {

    /**
     * initialize the scrolling layout, populating it with 100 messages
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        findViewById(R.id.send).setOnClickListener((v) -> {
            TextInputEditText textInput = findViewById(R.id.message_box);
            if (!textInput.getText().toString().equals("")) {
                addMessage(textInput.getText().toString());
                textInput.getText().clear();
            }
        });
    }

    /**
     * add a new message to the scrolling layout
     *
     * @param message message to add
     */
    private void addMessage(String message) {
        TextView header = new TextView(this);
        header.setText("You at " + Calendar.getInstance().getTime().getHours() + ":" +
                Calendar.getInstance().getTime().getMinutes());
        header.setPadding(32, 32, 32, 0);
        header.setTextColor(Color.GRAY);
        header.setTextSize(12);

        TextView text = new TextView(this);
        text.setText(message);
        text.setPadding(32, 0, 32, 32);
        text.setTextColor(Color.BLACK);
        text.setTextSize(18);

        LinearLayout linearLayout = findViewById(R.id.linear_layout);
        linearLayout.addView(header);
        linearLayout.addView(text);
        NestedScrollView nestedScrollView = (NestedScrollView) linearLayout.getParent();
        nestedScrollView.postDelayed(() -> {
            nestedScrollView.fullScroll(NestedScrollView.FOCUS_DOWN);
        }, 100L);
    }
}