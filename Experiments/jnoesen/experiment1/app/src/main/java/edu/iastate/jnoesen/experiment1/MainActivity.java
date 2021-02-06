package edu.iastate.jnoesen.experiment1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private boolean dark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        TextView textView = (TextView) findViewById(R.id.textView2);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);

        toggleButton.setOnClickListener(v -> {

            if (dark) {
                dark = false;
                new Thread(() -> {
                    for (int i = 1; i <= 255; i++) {
                        if (!dark) {
                            int buttonBG = Math.max(Math.min((int) (i * 0.8), 205), 50);
                            toggleButton.setBackgroundColor(Color.rgb(buttonBG, buttonBG, buttonBG));
                            relativeLayout.setBackgroundColor(Color.rgb(i, i, i));
                            toggleButton.setTextColor(Color.rgb(255 - i, 255 - i, 255 - i));
                            textView.setTextColor(Color.rgb(255 - i, 255 - i, 255 - i));
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
                textView.setText("Tap to switch to light mode");
            } else {
                dark = true;
                new Thread(() -> {
                    for (int i = 255; i >= 1; i--) {
                        if (dark) {
                            int buttonBG = Math.min(Math.max((int) (i * 0.8), 50),205);
                            toggleButton.setBackgroundColor(Color.rgb(buttonBG, buttonBG, buttonBG));
                            relativeLayout.setBackgroundColor(Color.rgb(i, i, i));
                            toggleButton.setTextColor(Color.rgb(255 - i, 255 - i, 255 - i));
                            textView.setTextColor(Color.rgb(255 - i, 255 - i, 255 - i));
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
                textView.setText("Tap to switch to dark mode");
            }
        });
    }
}