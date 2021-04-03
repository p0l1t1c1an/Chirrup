package cs309.sr2.chirrupfrontend.listui.post;

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
public class PostPresenter implements VolleyListener {

    /**
     * volley requester for the post
     */
    private VolleyRequester volleyRequester;

    /**
     * view of profile fragment
     */
    private View view;

    /**
     * url for the user json object with id replaced with #
     */
    private String userURL;

    /**
     * url for user avatar with user id replaced with #
     */
    private String imageURL;

    /**
     * true if the first request occurred
     */
    private boolean ready;

    /**
     * create a new presenter for post
     *
     * @param view fragment view
     */
    public PostPresenter(View view) {
        this.view = view;
    }

    /**
     * load the data for the post
     *
     * @param postURL url for the post json object
     * @param userURL url for the user json object with id replaced with #
     * @param imageURL url for user avatar with user id replaced with #
     */
    public void loadData(String postURL, String userURL, String imageURL) {
        volleyRequester = new VolleyRequester(this);
        volleyRequester.getObject(postURL);
        this.userURL = userURL;
        this.imageURL = imageURL;
        ready = false;
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
            if (ready) {
                ((TextView) view.findViewById(R.id.postusername)).setText(response.getString("username"));
                ((TextView) view.findViewById(R.id.postname)).setText(response.getString("firstname")
                        + " " + response.getString("lastname"));
            } else {
                ((TextView) view.findViewById(R.id.postbody)).setText(response.getString("content"));
                ((TextView) view.findViewById(R.id.posttimestamp)).setText(response.getString("dateCreated"));
                volleyRequester.getObject(userURL.replace("#", String.valueOf(response.getInt("creator"))));
                volleyRequester.getImage(imageURL.replace("#", String.valueOf(response.getInt("creator"))));
                ready = true;
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
        ((ImageView) view.findViewById(R.id.postavatar)).setImageBitmap(response.getBitmap());
    }
}
