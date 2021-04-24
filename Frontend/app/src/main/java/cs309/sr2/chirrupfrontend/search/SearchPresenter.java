package cs309.sr2.chirrupfrontend.search;

import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;

import cs309.sr2.chirrupfrontend.R;
import cs309.sr2.chirrupfrontend.listui.post.PostFragment;
import cs309.sr2.chirrupfrontend.listui.user.UserFragment;
import cs309.sr2.chirrupfrontend.utils.AppController;
import cs309.sr2.chirrupfrontend.volley.VolleyListener;
import cs309.sr2.chirrupfrontend.volley.VolleyRequester;

/**
 * presenter for search page
 *
 * @author Jeremy Noesen
 */
public class SearchPresenter implements VolleyListener {

    /**
     * volley requester for the fragment
     */
    private VolleyRequester volleyRequester;

    /**
     * view of fragment
     */
    private View view;

    /**
     * create a new presenter for searching
     *
     * @param view fragment view
     */
    public SearchPresenter(View view) {
        this.view = view;
    }

    /**
     * load the data for the fragment
     *
     * @param searchURL url for the user feed
     */
    public void loadData(String searchURL) {
        volleyRequester = new VolleyRequester(this);
        volleyRequester.getArray(searchURL);
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
                UserFragment user = new UserFragment(response.getInt(i));
                AppController.getFragmentManager().beginTransaction().add(R.id.search_feed_layout, user).commit();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
