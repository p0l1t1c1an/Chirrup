package edu.iastate.jnoesen.experiment2.ui.page3;

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

public class Page3Fragment extends Fragment {

    private Page3ViewModel page3ViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        page3ViewModel =
                new ViewModelProvider(this).get(Page3ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_page3, container, false);
        final TextView textView = root.findViewById(R.id.text_p3);
        page3ViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}