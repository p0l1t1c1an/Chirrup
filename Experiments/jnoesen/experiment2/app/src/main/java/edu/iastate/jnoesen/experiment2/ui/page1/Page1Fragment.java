package edu.iastate.jnoesen.experiment2.ui.page1;

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

/**
 * fragment for the first page, just a text box
 *
 * @author Jeremy Noesen
 */
public class Page1Fragment extends Fragment {

    /**
     * inflate the layout into a view on view creation
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return inflated view
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_page1, container, false);
    }
}