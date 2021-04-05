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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_personalprofile, container, false);

        PersonalProfilePresenter personalProfilePresenter = new PersonalProfilePresenter(root);
        personalProfilePresenter.loadData(getString(R.string.base_url) + "user/" + Session.getUser(),
                "https://api.androidhive.info/volley/volley-image.jpg");

        Button followers = root.findViewById(R.id.personalprofile_followers);
        Button following = root.findViewById(R.id.personalprofile_following);

        followers.setOnClickListener(v -> {
            //show followers list
        });

        following.setOnClickListener(v -> {
            //show following list
        });

        return root;
    }
}