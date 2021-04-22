package cs309.sr2.chirrupfrontend.mainfeed;

import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cs309.sr2.chirrupfrontend.R;
import cs309.sr2.chirrupfrontend.listui.post.PostFragment;
import cs309.sr2.chirrupfrontend.utils.AppController;
import cs309.sr2.chirrupfrontend.volley.VolleyListener;
import cs309.sr2.chirrupfrontend.volley.VolleyRequester;

/**
 * presenter for the main feed
 *
 * @author Jeremy Noesen
 */
public class MainFeedPresenter implements VolleyListener {

    /**
     * volley requester for the fragment
     */
    private VolleyRequester volleyRequester;

    /**
     * view of fragment
     */
    private View view;

    /**
     * create a new presenter for main feed
     *
     * @param view fragment view
     */
    public MainFeedPresenter(View view) {
        this.view = view;
    }

    /**
     * load the data for the fragment
     *
     * @param feedURL url for the user feed
     */
    public void loadData(String feedURL) {
        volleyRequester = new VolleyRequester(this);
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
                AppController.getFragmentManager().beginTransaction().add(R.id.main_feed_layout, post).commit();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
