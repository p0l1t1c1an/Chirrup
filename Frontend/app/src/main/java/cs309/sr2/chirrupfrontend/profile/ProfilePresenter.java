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
import cs309.sr2.chirrupfrontend.listui.user.UserFragment;
import cs309.sr2.chirrupfrontend.utils.AppController;
import cs309.sr2.chirrupfrontend.volley.VolleyListener;
import cs309.sr2.chirrupfrontend.listui.post.PostView;
import cs309.sr2.chirrupfrontend.volley.VolleyRequester;

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
    }

    /**
     * load the data for the profile page
     *
     * @param userURL url for the user json object
     * @param imageURL url for user avatar
     */
    public void loadData(String userURL, String imageURL) {
        VolleyRequester volleyRequester = new VolleyRequester(this);
        volleyRequester.getObject(userURL);
        volleyRequester.getImage(imageURL);
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
     */
    @Override
    public void onObjectResponse(JSONObject response) {
        try {
            ((TextView) view.findViewById(R.id.profile_bio)).setText(response.getString("biography"));
            ((TextView) view.findViewById(R.id.profile_username)).setText(response.getString("username"));
            ((TextView) view.findViewById(R.id.profile_name)).setText(response.getString("firstname") + " " +
                    response.getString("lastname"));

            LinearLayout postLayout = view.findViewById(R.id.profile_feed_layout);
            JSONArray posts = response.getJSONArray("posts");
            for (int i = 0; i < posts.length(); i++) {
                try {
                    postLayout.addView(new PostView(inflater, container, posts.getInt(i)).getView());
//                    UserFragment profile = new UserFragment(9);
//                    AppController.getFragmentManager().beginTransaction().add(R.id.profile_feed_layout, profile).commit();
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
        ((ImageView) view.findViewById(R.id.profile_avatar)).setImageBitmap(response.getBitmap());
    }
}
