package cs309.sr2.chirrupfrontend.listui.post;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
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
     * url for the post json object
     */
    private String postURL;

    /**
     * url for the user json object with id replaced with #
     */
    private String userURL;

    /**
     * url for user avatar with user id replaced with #
     */
    private String imageURL;

    /**
     * id of user liking post
     */
    private int likeUserID;

    /**
     * status of requests
     */
    private int status;

    /**
     * whether the main user has like the post or not
     */
    private boolean liked;

    /**
     * number of likes on post
     */
    private int likes;

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
        this.postURL = postURL;
        this.userURL = userURL;
        this.imageURL = imageURL;
        status = 0;
    }

    /**
     * set the post data when the response occurs
     *
     * @param response response from request
     */
    @Override
    public void onObjectResponse(JSONObject response) {
        try {
            if (status == 1) {
                ((TextView) view.findViewById(R.id.post_username)).setText(response.getString("username"));
                ((TextView) view.findViewById(R.id.post_name)).setText(response.getString("firstname")
                        + " " + response.getString("lastname"));
                status = 2;
            } else {
                ((TextView) view.findViewById(R.id.post_body)).setText(response.getString("content"));
                ((TextView) view.findViewById(R.id.post_timestamp)).setText(response.getString("dateCreated"));

                if(response.get("likes").toString().contains(Integer.toString(likeUserID))) {
                    liked = true;
                    likes = response.get("likes").toString().split(",").length;
                }

                if(liked) {
                    ((Button) view.findViewById(R.id.post_like)).setText("Unlike (" + this.likes + ")");
                } else {
                    ((Button) view.findViewById(R.id.post_like)).setText("Like (" + this.likes + ")");
                }

                volleyRequester.getObject(userURL.replace("#", String.valueOf(response.getInt("creator"))));
                volleyRequester.getImage(imageURL.replace("#", String.valueOf(response.getInt("creator"))));
                status = 1;
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
        ((ImageView) view.findViewById(R.id.post_avatar)).setImageBitmap(response.getBitmap());
    }

    /**
     * add or remove a like from a post
     *
     * @param url post liking url
     * @param likeUserID id of user liking post
     */
    public void likePost(String url, int likeUserID) {
        this.likeUserID = likeUserID;

        Button like = view.findViewById(R.id.post_like);

        if(liked) {
            volleyRequester.setString(url.replace("#", Integer.toString(likeUserID)), null, Request.Method.DELETE);
            like.setText("Like (" + (likes - 1) + ")");
            likes--;
            liked = false;
        } else {
            volleyRequester.setString(url.replace("#", Integer.toString(likeUserID)), null, Request.Method.POST);
            like.setText("Unlike (" + (likes + 1) + ")");
            likes++;
            liked = true;
        }
    }
}
