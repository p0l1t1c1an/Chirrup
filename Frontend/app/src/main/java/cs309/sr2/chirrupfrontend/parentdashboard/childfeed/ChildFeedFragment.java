package cs309.sr2.chirrupfrontend.parentdashboard.childfeed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import cs309.sr2.chirrupfrontend.R;
import cs309.sr2.chirrupfrontend.account.Session;
import cs309.sr2.chirrupfrontend.AppController;

/**
 * screen used to display the main feed
 *
 * @author Jeremy Noesen
 */
public class ChildFeedFragment extends Fragment {

    /**
     * id of user to show main feed of
     */
    private int userID;

    /**
     * create a main feed for the current user
     */
    public ChildFeedFragment() {
        this.userID = -1;
    }

    /**
     * create a main feed for a user
     *
     * @param userID id of user to show
     */
    public ChildFeedFragment(int userID) {
        this.userID = userID;
    }

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
        View root = inflater.inflate(R.layout.fragment_childfeed, container, false);

        if (userID < 0) userID = Session.getUser();

        ChildFeedPresenter childFeedPresenter = new ChildFeedPresenter(root);
        childFeedPresenter.loadData(AppController.getInstance().getString(R.string.base_url) + "feed/" + userID);

        root.findViewById(R.id.child_feed_reload).setOnClickListener(v -> childFeedPresenter.reload());

        return root;
    }

}
