package cs309.sr2.chirrupfrontend.parentdashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import cs309.sr2.chirrupfrontend.R;
import cs309.sr2.chirrupfrontend.account.Session;
import cs309.sr2.chirrupfrontend.parentdashboard.childuser.ChildUserFragment;
import cs309.sr2.chirrupfrontend.utils.AppController;

/**
 * children list fragment
 *
 * @author Jeremy Noesen
 */
public class ChildListFragment extends Fragment {

    /**
     * get the list of users that follow the user and list them in the ui
     *
     * @param inflater           layout inflater
     * @param container          view group
     * @param savedInstanceState saved instance state
     * @return followers list view
     */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_child_list, container, false);

        ChildListPresenter childListPresenter = new ChildListPresenter(root);
        childListPresenter.loadData(getString(R.string.base_url) + "/settings/parent/"
                + Session.getUser() + "/children");

        root.findViewById(R.id.child_list_reload).setOnClickListener(v -> childListPresenter.reload());

        ChildUserFragment fragment = new ChildUserFragment(22);
        AppController.getFragmentManager().beginTransaction().add(R.id.child_list_layout, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

        return root;
    }
}
