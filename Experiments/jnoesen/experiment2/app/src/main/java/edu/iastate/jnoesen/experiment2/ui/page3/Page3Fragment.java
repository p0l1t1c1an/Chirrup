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

public class Page3Fragment extends Fragment {

    private int count = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_page3, container, false);
        TextView text = root.findViewById(R.id.text_p3);
        Button button = root.findViewById(R.id.button);
        float textSize = text.getTextSize();
        button.setOnClickListener((v) -> {
            count++;
            text.setText(Integer.toString(count));
            text.setTextSize(textSize + count);

        });
        return root;
    }
}