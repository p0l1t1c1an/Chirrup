package cs309.sr2.chirrupfrontend.listui.user;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;

import cs309.sr2.chirrupfrontend.R;
import cs309.sr2.chirrupfrontend.utils.AppController;

/**
 * A UI element that will show the contents of a user for the purpose of listing with other users
 * <br>
 * <code>layout.addView(new UserView(inflater, container, savedInstanceState, userID).getView());</code>
 *
 * @author Jeremy Noesen
 */
public class UserView {

    /**
     * root view for user
     */
    private final View root;

    /**
     * create a new user card within another view from a userID
     *
     * @param inflater LayoutInflater from parent view onCreate
     * @param container ViewGroup from parent view onCreate
     * @param userID id of user to make card for
     */
    public UserView(@NonNull LayoutInflater inflater, ViewGroup container, int userID) {
        root = inflater.inflate(R.layout.ui_usercard, container, false);

        UserPresenter postPresenter = new UserPresenter(root);
        postPresenter.loadData(AppController.getInstance().getString(R.string.base_url) +
                "user/" + userID,"https://api.androidhive.info/volley/volley-image.jpg");

        Button view = root.findViewById(R.id.user_view);

        view.setOnClickListener(v -> {
            //show profile of clicked user
        });
    }

    /**
     * get the view of the post layout
     *
     * @return view of post layout
     */
    public View getView() {
        return root;
    }

}
