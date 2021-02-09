package edu.iastate.jnoesen.experiment2.ui.page2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import edu.iastate.jnoesen.experiment2.R;

/**
 * fragment for the second page, has a live-updating clock on screen
 *
 * @author Jeremy Noesen
 */
public class Page2Fragment extends Fragment {

    /**
     * add a live clock to the page when it is created
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return view with live clock
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_page2, container, false);
        final TextView textView = root.findViewById(R.id.text_p2);

        new Thread(() -> {
            while (this.isVisible()) {
                textView.setText(Calendar.getInstance().getTime().toString());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return root;
    }
}