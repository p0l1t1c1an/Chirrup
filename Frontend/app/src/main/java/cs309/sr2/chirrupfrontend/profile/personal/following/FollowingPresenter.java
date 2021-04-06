package cs309.sr2.chirrupfrontend.profile.personal.following;

import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cs309.sr2.chirrupfrontend.R;
import cs309.sr2.chirrupfrontend.listui.user.UserFragment;
import cs309.sr2.chirrupfrontend.utils.AppController;
import cs309.sr2.chirrupfrontend.volley.VolleyListener;
import cs309.sr2.chirrupfrontend.volley.VolleyRequester;

/**
 * class to handle visual changes for following list
 *
 * @author Jeremy Noesen
 */
public class FollowingPresenter implements VolleyListener {

    /**
     * volley requester for the fragment
     */
    private VolleyRequester volleyRequester;

    /**
     * view of fragment
     */
    private View view;

    /**
     * create a new presenter for fragment
     *
     * @param view fragment view
     */
    public FollowingPresenter(View view) {
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
    }

    /**
     * fill list of users when the request comes in
     *
     * @param response response from request
     */
    @Override
    public void onObjectResponse(JSONObject response) {
        try {
            JSONArray following = response.getJSONArray("following");
            for (int i = following.length() - 1; i >= 0; i--) {
                UserFragment fragment = new UserFragment(following.getInt(i));
                AppController.getFragmentManager().beginTransaction().add(R.id.follow_feed_layout, fragment).commit();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
