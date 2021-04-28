package cs309.sr2.chirrupfrontend.profile.personal;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cs309.sr2.chirrupfrontend.R;
import cs309.sr2.chirrupfrontend.AppController;
import cs309.sr2.chirrupfrontend.volley.VolleyListener;
import cs309.sr2.chirrupfrontend.post.PostFragment;
import cs309.sr2.chirrupfrontend.volley.VolleyRequester;

/**
 * class to handle visual changes for the profile page
 *
 * @author Jeremy Noesen
 */
public class PersonalProfilePresenter implements VolleyListener {

    /**
     * view of profile fragment
     */
    private View view;

    /**
     * volley requester for class
     */
    private VolleyRequester volleyRequester;

    /**
     * url for user json object
     */
    private String userURL;

    /**
     * url for user avatar
     */
    private String imageURL;

    /**
     * create a new presenter for the profile page
     *
     * @param view fragment view
     */
    public PersonalProfilePresenter(View view) {
        this.view = view;
    }

    /**
     * load the data for the profile page
     *
     * @param userURL  url for the user json object
     * @param imageURL url for user avatar
     */
    public void loadData(String userURL, String imageURL) {
        volleyRequester = new VolleyRequester(this);
        volleyRequester.getObject(userURL);
        volleyRequester.getImage(imageURL);
        this.userURL = userURL;
        this.imageURL = imageURL;
    }

    /**
     * set the profile data when the response occurs
     *
     * @param response response from request
     */
    @Override
    public void onObjectResponse(JSONObject response) {
        try {
            ((TextView) view.findViewById(R.id.personalprofile_bio)).setText(response.getString("biography"));
            ((TextView) view.findViewById(R.id.personalprofile_username)).setText(response.getString("username"));
            ((TextView) view.findViewById(R.id.personalprofile_name)).setText(response.getString("firstname") + " " +
                    response.getString("lastname"));

            JSONArray posts = response.getJSONArray("posts");
            for (int i = posts.length() - 1; i >= 0; i--) {
                try {
                    PostFragment post = new PostFragment(posts.getInt(i));
                    AppController.getFragmentManager().beginTransaction().add(R.id.personalprofile_feed_layout, post)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            int followers = response.getJSONArray("followers").length();
            ((Button) view.findViewById(R.id.personalprofile_followers)).setText("Followers (" + followers + ")");

            int following = response.getJSONArray("following").length();
            ((Button) view.findViewById(R.id.personalprofile_following)).setText("Following (" + following + ")");

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
    public void onImageResponse(Bitmap response) {
        ((ImageView) view.findViewById(R.id.personalprofile_avatar)).setImageBitmap(response);
    }

    /**
     * reload the page and all posts
     */
    public void reload() {
        LinearLayout layout = view.findViewById(R.id.personalprofile_feed_layout);
        layout.removeAllViews();
        volleyRequester.getObject(userURL);
        volleyRequester.getImage(imageURL);
    }
}
