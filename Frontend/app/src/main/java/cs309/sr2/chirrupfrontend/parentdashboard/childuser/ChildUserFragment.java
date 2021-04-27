package cs309.sr2.chirrupfrontend.parentdashboard.childuser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import cs309.sr2.chirrupfrontend.R;
import cs309.sr2.chirrupfrontend.parentdashboard.childfeed.ChildFeedFragment;
import cs309.sr2.chirrupfrontend.profile.other.OtherProfileFragment;
import cs309.sr2.chirrupfrontend.AppController;

/**
 * A UI element that will show the contents of a user for the purpose of listing with other users. Can be added with:
 * <br>
 * <code>ChildUserFragment fragment = new ChildUserFragment(userID);
 * AppController.showFragment(fragment);</code>
 *
 * @author Jeremy Noesen
 */
public class ChildUserFragment extends Fragment {

    /**
     * id of user to show profile of
     */
    private int userID;

    /**
     * create a user for the profile of a specific user
     *
     * @param userID id of user to show user of
     */
    public ChildUserFragment(int userID) {
        this.userID = userID;
    }

    /**
     * retrieve the profile data to fill in the text fields and avatar to create a user card
     *
     * @param inflater           layout inflater
     * @param container          view group
     * @param savedInstanceState saved instance state
     * @return filled out user card
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_childcard, container, false);

        ChildUserPresenter postPresenter = new ChildUserPresenter(root);
        postPresenter.loadData(AppController.getInstance().getString(R.string.base_url) +
                "user/" + userID, AppController.getInstance().getString(R.string.base_url)
                + "user/" + userID + "/profilePicture");

        Button feed = root.findViewById(R.id.child_feed_button);
        Button profile = root.findViewById(R.id.child_profile_button);

        feed.setOnClickListener(v -> {
            ChildFeedFragment childFeedFragment = new ChildFeedFragment(userID);
            AppController.showFragment(childFeedFragment);
        });

        profile.setOnClickListener(v -> {
            OtherProfileFragment otherProfileFragment = new OtherProfileFragment(userID);
            AppController.showFragment(otherProfileFragment);
        });

        return root;
    }
}
