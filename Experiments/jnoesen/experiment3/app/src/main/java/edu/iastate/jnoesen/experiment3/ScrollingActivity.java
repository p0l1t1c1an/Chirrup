package edu.iastate.jnoesen.experiment3;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

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
        TextView textView = new TextView(this);
        textView.setText("You wrote:\n" +
                message + "\n" +
                Calendar.getInstance().getTime().getHours() + ":" +
                Calendar.getInstance().getTime().getMinutes() + "\n");
        textView.setPadding(32, 0, 32, 0);

        LinearLayout linearLayout = findViewById(R.id.linear_layout);
        linearLayout.addView(textView);

        count++;
    }
}