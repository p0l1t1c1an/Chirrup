package cs309.sr2.chirrupfrontend.profile.personal.following;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import cs309.sr2.chirrupfrontend.R;

/**
 * following list fragment
 *
 * @author Jeremy Noesen
 */
public class FollowingFragment extends Fragment {

    /**
     * id of user to list following users of
     */
    private int userID;

    /**
     * create a list of following users for the user
     *
     * @param userID id of user
     */
    public FollowingFragment(int userID) {
        this.userID = userID;
    }

    /**
     * get the list of users the user is following and list them in the ui
     *
     * @param inflater           layout inflater
     * @param container          view group
     * @param savedInstanceState saved instance state
     * @return following list view
     */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_follow_list, container, false);

        FollowingPresenter followingPresenter = new FollowingPresenter(root);
        followingPresenter.loadData(getString(R.string.base_url) + "user/" + userID + "/following");

        return root;
    }
}
