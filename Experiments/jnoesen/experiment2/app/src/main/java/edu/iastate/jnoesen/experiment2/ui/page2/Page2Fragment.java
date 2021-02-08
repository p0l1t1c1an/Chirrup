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

import edu.iastate.jnoesen.experiment2.R;

public class Page2Fragment extends Fragment {

    private Page2ViewModel page2ViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        page2ViewModel =
                new ViewModelProvider(this).get(Page2ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_page2, container, false);
        final TextView textView = root.findViewById(R.id.text_p2);
        page2ViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}