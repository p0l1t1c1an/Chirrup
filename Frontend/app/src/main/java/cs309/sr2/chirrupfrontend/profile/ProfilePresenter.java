package cs309.sr2.chirrupfrontend.profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cs309.sr2.chirrupfrontend.R;
import cs309.sr2.chirrupfrontend.Volley.VolleyListener;
import cs309.sr2.chirrupfrontend.account.Session;
import cs309.sr2.chirrupfrontend.post.PostView;
import cs309.sr2.chirrupfrontend.utils.AppController;
import cs309.sr2.chirrupfrontend.Volley.VolleyRequester;

/**
 * class to handle visual changes for the profile page
 *
 * @author Jeremy Noesen
 */
public class ProfilePresenter implements VolleyListener {

    /**
     * view of profile fragment
     */
    private View view;

    /**
     * inflater from fragment, used to make posts
     */
    private LayoutInflater inflater;

    /**
     * view group from fragment, used to make posts
     */
    private ViewGroup container;

    /**
     * create a new presenter for the profile page
     *
     * @param view fragment view
     */
    public ProfilePresenter(View view, LayoutInflater inflater, ViewGroup container) {
        this.view = view;
        this.inflater = inflater;
        this.container = container;
        VolleyRequester volleyRequester = new VolleyRequester(this);
        volleyRequester.getObject(AppController.getInstance()
                .getString(R.string.base_url) + "user/" + Session.getUser());
        volleyRequester.getBitmap("https://api.androidhive.info/volley/volley-image.jpg");
    }

    /**
     * not used for this class
     *
     * @param response response from request
     */
    @Override
    public void onStringResponse(String response) {
    }

    /**
     * set the profile data when the response occurs
     *
     * @param response response from request
     * @throws JSONException
     */
    @Override
    public void onObjectResponse(JSONObject response) {
        try {
            ((TextView) view.findViewById(R.id.bio)).setText(response.getString("biography"));
            ((TextView) view.findViewById(R.id.username)).setText(response.getString("username"));
            ((TextView) view.findViewById(R.id.name)).setText(response.getString("firstname") + " " +
                    response.getString("lastname"));

            LinearLayout postLayout = view.findViewById(R.id.profile_feed_layout);
            JSONArray posts = response.getJSONArray("posts");
            for (int i = 0; i < posts.length(); i++) {
                try {
                    postLayout.addView(new PostView(inflater, container, posts.getInt(i)).getView());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * set the avatar when it arrives
     *
     * @param response response from request
     */
    @Override
    public void onImageResponse(ImageLoader.ImageContainer response) {
        ((ImageView) view.findViewById(R.id.avatar)).setImageBitmap(response.getBitmap());
    }
}
