package cs309.sr2.chirrupfrontend.profile.personal.followers;

import android.view.View;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentTransaction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cs309.sr2.chirrupfrontend.R;
import cs309.sr2.chirrupfrontend.listui.user.UserFragment;
import cs309.sr2.chirrupfrontend.utils.AppController;
import cs309.sr2.chirrupfrontend.volley.VolleyListener;
import cs309.sr2.chirrupfrontend.volley.VolleyRequester;

/**
 * class to handle visual changes for followers list
 *
 * @author Jeremy Noesen
 */
public class FollowersPresenter implements VolleyListener {

    /**
     * volley requester for the fragment
     */
    private VolleyRequester volleyRequester;

    /**
     * view of fragment
     */
    private View view;

    /**
     * url for user
     */
    private String userURL;

    /**
     * create a new presenter for fragment
     *
     * @param view fragment view
     */
    public FollowersPresenter(View view) {
        this.view = view;
    }

    /**
     * load the data for the list
     *
     * @param userURL url for the user json object
     */
    public void loadData(String userURL) {
        volleyRequester = new VolleyRequester(this);
        volleyRequester.getObject(userURL);
        this.userURL = userURL;
    }

    /**
     * fill list of users when the request comes in
     *
     * @param response response from request
     */
    @Override
    public void onObjectResponse(JSONObject response) {
        try {
            JSONArray followers = response.getJSONArray("followers");
            for (int i = followers.length() - 1; i >= 0; i--) {
                UserFragment fragment = new UserFragment(followers.getInt(i));
                AppController.getFragmentManager().beginTransaction().add(R.id.follow_feed_layout, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * reload followers list
     */
    public void reload() {
        LinearLayout layout = view.findViewById(R.id.follow_feed_layout);
        layout.removeAllViews();
        volleyRequester.getObject(userURL);
    }
}
