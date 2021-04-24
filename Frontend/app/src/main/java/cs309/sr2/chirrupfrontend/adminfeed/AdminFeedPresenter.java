package cs309.sr2.chirrupfrontend.adminfeed;

import android.view.View;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentTransaction;

import org.json.JSONArray;
import org.json.JSONException;

import cs309.sr2.chirrupfrontend.R;
import cs309.sr2.chirrupfrontend.listui.post.PostFragment;
import cs309.sr2.chirrupfrontend.utils.AppController;
import cs309.sr2.chirrupfrontend.volley.VolleyListener;
import cs309.sr2.chirrupfrontend.volley.VolleyRequester;

/**
 * presenter for the admin feed
 *
 * @author Jeremy Noesen
 */
public class AdminFeedPresenter implements VolleyListener {

    /**
     * volley requester for the fragment
     */
    private VolleyRequester volleyRequester;

    /**
     * view of fragment
     */
    private View view;

    /**
     * url for feed
     */
    private String feedURL;

    /**
     * create a new presenter for main feed
     *
     * @param view fragment view
     */
    public AdminFeedPresenter(View view) {
        this.view = view;
    }

    /**
     * load the data for the fragment
     *
     * @param feedURL url for the user feed
     */
    public void loadData(String feedURL) {
        this.feedURL = feedURL;
        volleyRequester = new VolleyRequester(this);
        volleyRequester.getArray(feedURL);
    }

    /**
     * reload the feed, looking for new posts and adding them to the layout
     */
    public void reload() {
        LinearLayout layout = view.findViewById(R.id.admin_feed_layout);
        layout.removeAllViews();
        volleyRequester.getArray(feedURL);
    }

    /**
     * load the feed from the request
     *
     * @param response response from request
     */
    @Override
    public void onArrayResponse(JSONArray response) {
        try {
            for (int i = 0; i < response.length(); i++) {
                PostFragment post = new PostFragment(response.getInt(i));
                AppController.getFragmentManager().beginTransaction().add(R.id.admin_feed_layout, post)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
