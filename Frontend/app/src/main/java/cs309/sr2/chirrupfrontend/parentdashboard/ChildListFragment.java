package cs309.sr2.chirrupfrontend.parentdashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import cs309.sr2.chirrupfrontend.R;

/**
 * followers list fragment
 *
 * @author Jeremy Noesen
 */
public class ChildListFragment extends Fragment {

    /**
     * id of user to list followers of
     */
    private int userID;

    /**
     * create a list of followers for the user
     *
     * @param userID id of user
     */
    public ChildListFragment(int userID) {
        this.userID = userID;
    }

    /**
     * get the list of users that follow the user and list them in the ui
     *
     * @param inflater           layout inflater
     * @param container          view group
     * @param savedInstanceState saved instance state
     * @return followers list view
     */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_child_list, container, false);

        ChildListPresenter childListPresenter = new ChildListPresenter(root);
        childListPresenter.loadData(getString(R.string.base_url) + "user/" + userID);

        root.findViewById(R.id.follow_list_reload).setOnClickListener(v -> childListPresenter.reload());

        return root;
    }
}
