package cs309.sr2.chirrupfrontend.adminfeed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import cs309.sr2.chirrupfrontend.R;
import cs309.sr2.chirrupfrontend.AppController;

/**
 * screen used to display the admin feed
 *
 * @author Jeremy Noesen
 */
public class AdminFeedFragment extends Fragment {

    /**
     * retrieve admin feed and list posts
     *
     * @param inflater           layout inflater
     * @param container          view group
     * @param savedInstanceState saved instance state
     * @return admin feed
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_adminfeed, container, false);

        AdminFeedPresenter adminFeedPresenter = new AdminFeedPresenter(root);
        adminFeedPresenter.loadData(AppController.getInstance().getString(R.string.base_url) + "admin/feed");

        root.findViewById(R.id.admin_feed_reload).setOnClickListener(v -> adminFeedPresenter.reload());

        return root;
    }

}
