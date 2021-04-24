package cs309.sr2.chirrupfrontend.profile.other;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import cs309.sr2.chirrupfrontend.R;
import cs309.sr2.chirrupfrontend.account.Session;
import cs309.sr2.chirrupfrontend.utils.AppController;

/**
 * other user profile page fragment
 *
 * @author Jeremy Noesen
 */
public class OtherProfileFragment extends Fragment {

    /**
     * id of user to show profile of
     */
    private int userID;

    /**
     * create a fragment for the profile of a specific user
     *
     * @param userID id of user to show profile of
     */
    public OtherProfileFragment(int userID) {
        this.userID = userID;
    }

    /**
     * retrieve the profile data to fill in the text fields and avatar
     *
     * @param inflater           layout inflater
     * @param container          view group
     * @param savedInstanceState saved instance state
     * @return full profile page view
     */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_otherprofile, container, false);

        if (userID < 0) userID = Session.getUser();

        OtherProfilePresenter otherProfilePresenter = new OtherProfilePresenter(root);
        otherProfilePresenter.loadData(getString(R.string.base_url) + "user/" + userID,
                AppController.getInstance().getString(R.string.base_url) + "user/" + userID
                        + "/profilePicture", Session.getUser());

        Button follow = root.findViewById(R.id.otherprofile_follow);
        Button block = root.findViewById(R.id.otherprofile_block);

        follow.setOnClickListener(v -> {
            if (userID != Session.getUser())
                otherProfilePresenter.followUser(getString(R.string.base_url) + "user/" +
                        Session.getUser() + "/following/" + userID);
        });

        block.setOnClickListener(v -> {
            if (userID != Session.getUser())
                otherProfilePresenter.blockUser(getString(R.string.base_url) + "user/" +
                        Session.getUser() + "/blocking/" + userID);
        });

        return root;
    }
}