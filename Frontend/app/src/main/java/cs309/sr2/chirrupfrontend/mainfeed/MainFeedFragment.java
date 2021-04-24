package cs309.sr2.chirrupfrontend.mainfeed;

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
public class MainFeedFragment extends Fragment {

    /**
     * id of user to show main feed of
     */
    private int userID;

    /**
     * create a main feed for the current user
     */
    public MainFeedFragment() {
        this.userID = -1;
    }

    /**
     * create a main feed for a user
     *
     * @param userID id of user to show
     */
    public MainFeedFragment(int userID) {
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
        View root = inflater.inflate(R.layout.fragment_mainfeed, container, false);

        if (userID < 0) userID = Session.getUser();

        MainFeedPresenter mainFeedPresenter = new MainFeedPresenter(root);
        mainFeedPresenter.loadData(AppController.getInstance().getString(R.string.base_url) + "feed/" + userID);

        root.findViewById(R.id.main_feed_reload).setOnClickListener(v -> mainFeedPresenter.reload());

        return root;
    }

}
