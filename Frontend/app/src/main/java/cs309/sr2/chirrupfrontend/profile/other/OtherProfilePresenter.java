package cs309.sr2.chirrupfrontend.profile.other;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cs309.sr2.chirrupfrontend.R;
import cs309.sr2.chirrupfrontend.listui.post.PostFragment;
import cs309.sr2.chirrupfrontend.utils.AppController;
import cs309.sr2.chirrupfrontend.volley.VolleyListener;
import cs309.sr2.chirrupfrontend.volley.VolleyRequester;

/**
 * class to handle visual changes for the profile page
 *
 * @author Jeremy Noesen
 */
public class OtherProfilePresenter implements VolleyListener {

    /**
     * view of profile fragment
     */
    private View view;

    /**
     * create a new presenter for the profile page
     *
     * @param view fragment view
     */
    public OtherProfilePresenter(View view) {
        this.view = view;
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
            ((TextView) view.findViewById(R.id.otherprofile_bio)).setText(response.getString("biography"));
            ((TextView) view.findViewById(R.id.otherprofile_username)).setText(response.getString("username"));
            ((TextView) view.findViewById(R.id.otherprofile_name)).setText(response.getString("firstname") + " " +
                    response.getString("lastname"));

            JSONArray posts = response.getJSONArray("posts");
            for (int i = 0; i < posts.length(); i++) {
                try {
                    PostFragment post = new PostFragment(posts.getInt(i));
                    AppController.getFragmentManager().beginTransaction().add(R.id.otherprofile_feed_layout, post).commit();
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
        ((ImageView) view.findViewById(R.id.otherprofile_avatar)).setImageBitmap(response.getBitmap());
    }
}
