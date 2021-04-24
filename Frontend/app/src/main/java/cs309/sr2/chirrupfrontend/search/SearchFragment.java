package cs309.sr2.chirrupfrontend.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import cs309.sr2.chirrupfrontend.R;
import cs309.sr2.chirrupfrontend.account.Session;
import cs309.sr2.chirrupfrontend.utils.AppController;

/**
 * screen used to display the main feed
 *
 * @author Jeremy Noesen
 */
public class SearchFragment extends Fragment {

    /**
     * retrieve user feed and list posts
     *
     * @param inflater           layout inflater
     * @param container          view group
     * @param savedInstanceState saved instance state
     * @return main feed for user
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        SearchPresenter searchPresenter = new SearchPresenter(root);
        searchPresenter.loadData(AppController.getInstance().getString(R.string.base_url) + "");

        return root;
    }

}
