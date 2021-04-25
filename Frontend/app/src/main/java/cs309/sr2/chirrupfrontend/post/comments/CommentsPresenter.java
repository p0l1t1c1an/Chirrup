package cs309.sr2.chirrupfrontend.post.comments;

import android.view.View;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentTransaction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cs309.sr2.chirrupfrontend.R;
import cs309.sr2.chirrupfrontend.post.PostFragment;
import cs309.sr2.chirrupfrontend.utils.AppController;
import cs309.sr2.chirrupfrontend.volley.VolleyListener;
import cs309.sr2.chirrupfrontend.volley.VolleyRequester;

/**
 * presenter for the comments screen
 *
 * @author Jeremy Noesen
 */
public class CommentsPresenter implements VolleyListener {

    /**
     * volley requester for the fragment
     */
    private VolleyRequester volleyRequester;

    /**
     * view of fragment
     */
    private View view;

    /**
     * url of post
     */
    private String postURL;

    /**
     * create a new presenter for comment thread
     *
     * @param view fragment view
     */
    public CommentsPresenter(View view) {
        this.view = view;
    }

    /**
     * load the data for the fragment
     *
     * @param postURL url for the parent post json object
     */
    public void loadData(String postURL) {
        volleyRequester = new VolleyRequester(this);
        volleyRequester.getObject(postURL);
        this.postURL = postURL;
    }

    /**
     * load in post replies when the request completes
     *
     * @param response response from request
     */
    @Override
    public void onObjectResponse(JSONObject response) {
        try {
            JSONArray posts = response.getJSONArray("comments");
            for (int i = posts.length() - 1; i >= 0; i--) {
                try {
                    PostFragment post = new PostFragment(posts.getInt(i));
                    AppController.getFragmentManager().beginTransaction().add(R.id.comments_feed_layout, post)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * reload comments
     */
    public void reload() {
        LinearLayout layout = view.findViewById(R.id.comments_feed_layout);
        layout.removeAllViews();
        volleyRequester.getObject(postURL);
    }
}
