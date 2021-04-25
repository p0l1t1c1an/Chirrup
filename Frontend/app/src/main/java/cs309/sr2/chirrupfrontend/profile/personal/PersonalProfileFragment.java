package cs309.sr2.chirrupfrontend.profile.personal;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cs309.sr2.chirrupfrontend.R;
import cs309.sr2.chirrupfrontend.account.Session;
import cs309.sr2.chirrupfrontend.profile.personal.followers.FollowersFragment;
import cs309.sr2.chirrupfrontend.profile.personal.following.FollowingFragment;
import cs309.sr2.chirrupfrontend.utils.AppController;

/**
 * personal profile page fragment
 *
 * @author Jeremy Noesen
 */
public class PersonalProfileFragment extends Fragment {

    /**
     * retrieve the profile data to fill in the text fields and avatar
     *
     * @param inflater           layout inflater
     * @param container          view group
     * @param savedInstanceState saved instance state
     * @return full profile page view
     */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_personalprofile, container, false);

        PersonalProfilePresenter personalProfilePresenter = new PersonalProfilePresenter(root);
        personalProfilePresenter.loadData(getString(R.string.base_url) + "user/" + Session.getUser(),
                AppController.getInstance().getString(R.string.base_url) + "user/" +
                        Session.getUser() + "/profilePicture");

        Button followers = root.findViewById(R.id.personalprofile_followers);
        Button following = root.findViewById(R.id.personalprofile_following);

        followers.setOnClickListener(v -> {
            FollowersFragment followersFragment = new FollowersFragment(Session.getUser());
            AppController.showFragment(followersFragment);
        });

        following.setOnClickListener(v -> {
            FollowingFragment followingFragment = new FollowingFragment(Session.getUser());
            AppController.showFragment(followingFragment);
        });

        root.findViewById(R.id.personal_profile_reload).setOnClickListener(v -> personalProfilePresenter.reload());


        return root;
    }
}