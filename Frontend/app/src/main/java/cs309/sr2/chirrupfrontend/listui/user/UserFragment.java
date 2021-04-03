package cs309.sr2.chirrupfrontend.listui.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import cs309.sr2.chirrupfrontend.R;
import cs309.sr2.chirrupfrontend.account.Session;
import cs309.sr2.chirrupfrontend.profile.ProfileFragment;
import cs309.sr2.chirrupfrontend.utils.AppController;

/**
 * A UI element that will show the contents of a user for the purpose of listing with other users
 * <br>
 * <code>UserFragment fragment = new UserFragment(userID);
 * AppController.getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();</code>
 *
 * @author Jeremy Noesen
 */
public class UserFragment extends Fragment {

    /**
     * id of user to show profile of
     */
    private int userID;

    /**
     * create a user fragment for the logged in user
     */
    public UserFragment() {
        userID = -1;
    }

    /**
     * create a user for the profile of a specific user
     *
     * @param userID id of user to show user of
     */
    public UserFragment(int userID) {
        this.userID = userID;
    }

    /**
     * retrieve the profile data to fill in the text fields and avatar
     *
     * @param inflater           layout inflater
     * @param container          view group
     * @param savedInstanceState saved instance state
     * @return user card
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.ui_usercard, container, false);

        if (userID < 0) userID = Session.getUser();

        UserPresenter postPresenter = new UserPresenter(root);
        postPresenter.loadData(AppController.getInstance().getString(R.string.base_url) +
                "user/" + userID, "https://api.androidhive.info/volley/volley-image.jpg");

        Button view = root.findViewById(R.id.user_view);

        view.setOnClickListener(v -> {
            ProfileFragment profile = new ProfileFragment(userID);
            AppController.getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, profile).commit();
        });

        return root;
    }
}
