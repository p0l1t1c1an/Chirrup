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
import cs309.sr2.chirrupfrontend.profile.other.OtherProfileFragment;
import cs309.sr2.chirrupfrontend.utils.AppController;

/**
 * A UI element that will show the contents of a user for the purpose of listing with other users. Can be added with:
 * <br>
 * <code>UserFragment fragment = new UserFragment(userID);
 * AppController.getFragmentManager().beginTransaction().add(R.id.container, fragment).commit();</code>
 *
 * @author Jeremy Noesen
 */
public class UserFragment extends Fragment {

    /**
     * id of user to show profile of
     */
    private int userID;

    /**
     * create a user for the profile of a specific user
     *
     * @param userID id of user to show user of
     */
    public UserFragment(int userID) {
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
        View root = inflater.inflate(R.layout.fragment_usercard, container, false);

        UserPresenter postPresenter = new UserPresenter(root);
        postPresenter.loadData(AppController.getInstance().getString(R.string.base_url) +
                "user/" + userID, "https://api.androidhive.info/volley/volley-image.jpg");

        root.setOnClickListener(v -> {
            OtherProfileFragment profile = new OtherProfileFragment(userID);
            AppController.getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, profile)
                    .addToBackStack(null).commit();
        });

        return root;
    }
}
