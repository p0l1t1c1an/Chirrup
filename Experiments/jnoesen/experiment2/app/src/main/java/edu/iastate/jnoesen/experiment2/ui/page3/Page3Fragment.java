package edu.iastate.jnoesen.experiment2.ui.page3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import edu.iastate.jnoesen.experiment2.R;

/**
 * fragment for the third page, has a counter on it
 *
 * @author Jeremy Noesen
 */
public class Page3Fragment extends Fragment {

    /**
     * current value on the counter
     */
    private int count = 0;

    /**
     * add counter to the page when it is created
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return view with counter
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_page3, container, false);
        TextView text = root.findViewById(R.id.text_p3);
        Button button = root.findViewById(R.id.button);
        float textSize = text.getTextSize();
        button.setOnClickListener((v) -> {
            count++;
            if (count >= 100) count = 1;
            text.setText(Integer.toString(count));
            text.setTextSize(textSize + count);

        });
        return root;
    }
}