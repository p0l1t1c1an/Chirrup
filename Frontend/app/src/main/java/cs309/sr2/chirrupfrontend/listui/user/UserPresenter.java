package cs309.sr2.chirrupfrontend.listui.user;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import cs309.sr2.chirrupfrontend.R;
import cs309.sr2.chirrupfrontend.volley.VolleyListener;
import cs309.sr2.chirrupfrontend.volley.VolleyRequester;

/**
 * class to handle visual changes for posts
 *
 * @author Jeremy Noesen
 */
public class UserPresenter implements VolleyListener {

    /**
     * volley requester for the post
     */
    private VolleyRequester volleyRequester;

    /**
     * view of profile fragment
     */
    private View view;

    /**
     * create a new presenter for post
     *
     * @param view fragment view
     */
    public UserPresenter(View view) {
        this.view = view;
    }

    /**
     * load the data for the post
     *
     * @param userURL  url for the user json object with id replaced with #
     * @param imageURL url for user avatar with user id replaced with #
     */
    public void loadData(String userURL, String imageURL) {
        volleyRequester = new VolleyRequester(this);
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
     * set the post data when the response occurs
     *
     * @param response response from request
     */
    @Override
    public void onObjectResponse(JSONObject response) {
        try {
            ((TextView) view.findViewById(R.id.user_username)).setText(response.getString("username"));
            ((TextView) view.findViewById(R.id.user_name)).setText(response.getString("firstname")
                    + " " + response.getString("lastname"));
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
        ((ImageView) view.findViewById(R.id.user_avatar)).setImageBitmap(response.getBitmap());
    }
}
