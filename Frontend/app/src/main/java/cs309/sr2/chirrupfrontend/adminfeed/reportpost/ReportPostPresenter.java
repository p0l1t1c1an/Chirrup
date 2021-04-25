package cs309.sr2.chirrupfrontend.adminfeed.reportpost;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import cs309.sr2.chirrupfrontend.R;
import cs309.sr2.chirrupfrontend.profile.other.OtherProfileFragment;
import cs309.sr2.chirrupfrontend.profile.personal.PersonalProfileFragment;
import cs309.sr2.chirrupfrontend.utils.AppController;
import cs309.sr2.chirrupfrontend.volley.VolleyListener;
import cs309.sr2.chirrupfrontend.volley.VolleyRequester;

/**
 * class to handle visual changes for posts
 *
 * @author Jeremy Noesen
 */
public class ReportPostPresenter implements VolleyListener {

    /**
     * volley requester for the post
     */
    private VolleyRequester volleyRequester;

    /**
     * view of profile fragment
     */
    private View view;

    /**
     * url for post object
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
     * id of post creator
     */
    private int creatorID;

    /**
     * user avatar
     */
    private Bitmap avatar;

    /**
     * data about the post
     */
    private JSONObject postData;

    /**
     * data about the post creator
     */
    private JSONObject userData;

    /**
     * create a new presenter for post
     *
     * @param view fragment view
     */
    public ReportPostPresenter(View view) {
        this.view = view;
    }

    /**
     * load the data for the post
     *
     * @param postURL    url for the post json object
     * @param userURL    url for the user json object with id replaced with #
     * @param imageURL   url for user avatar with user id replaced with #
     * @param likeUserID id of user viewing post
     */
    public void loadData(String postURL, String userURL, String imageURL, int likeUserID) {
        volleyRequester = new VolleyRequester(this);
        volleyRequester.getObject(postURL);
        this.postURL = postURL;
        this.userURL = userURL;
        this.imageURL = imageURL;
        this.likeUserID = likeUserID;
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
                setUserData(response);
                status = 2;
            } else {
                setPostData(response);
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
        setAvatar(response.getBitmap());
    }

    /**
     * show the profile of the post creator
     */
    public void showProfile() {
        if (creatorID == likeUserID) {
            PersonalProfileFragment profile = new PersonalProfileFragment();
            AppController.showFragment(profile);
        } else {
            OtherProfileFragment profile = new OtherProfileFragment(creatorID);
            AppController.showFragment(profile);
        }
    }

    /**
     * get the user avatar bitmap from the post
     *
     * @return user avatar bitmap
     */
    public Bitmap getAvatar() {
        return avatar;
    }

    /**
     * set the user's avatar for the post
     *
     * @param image bitmap image
     */
    public void setAvatar(Bitmap image) {
        avatar = image;
        ((ImageView) view.findViewById(R.id.post_avatar)).setImageBitmap(image);
    }

    /**
     * get the json object data for the post
     *
     * @return json data for the post
     */
    public JSONObject getPostData() {
        return postData;
    }

    /**
     * set the data of the post
     *
     * @param postData post data to set
     * @throws JSONException
     */
    public void setPostData(JSONObject postData) throws JSONException {
        this.postData = postData;
        ((TextView) view.findViewById(R.id.post_body)).setText(postData.getString("content"));
        ((TextView) view.findViewById(R.id.post_timestamp)).setText(postData.getString("dateCreated"));

        creatorID = postData.getInt("creator");
        volleyRequester.getObject(userURL.replace("#", String.valueOf(creatorID)));
        volleyRequester.getImage(imageURL.replace("#", String.valueOf(creatorID)));
    }

    /**
     * get the user data for the post creator
     *
     * @return user json data
     */
    public JSONObject getUserData() {
        return userData;
    }

    /**
     * set the user data for the post
     *
     * @param userData user data to set
     * @throws JSONException
     */
    public void setUserData(JSONObject userData) throws JSONException {
        this.userData = userData;
        ((TextView) view.findViewById(R.id.post_username)).setText(userData.getString("username"));
        ((TextView) view.findViewById(R.id.post_name)).setText(userData.getString("firstname")
                + " " + userData.getString("lastname"));
    }

    /**
     * set the view for the presenter
     *
     * @param view view for presenter
     */
    public void setView(View view) {
        this.view = view;
    }

    /**
     * resolve post report by deleting
     *
     * @param url url for deleting post
     */
    public void delete(String url) {
        volleyRequester.setString(url, null, Request.Method.DELETE);
        view.setVisibility(View.GONE);
        Toast.makeText(AppController.getInstance(), "Post deleted!", Toast.LENGTH_SHORT).show();
    }

    /**
     * solve a post report by dismissing
     *
     * @param url url for dismissing post
     */
    public void dismiss(String url) {
        volleyRequester.setString(url, null, Request.Method.PUT);
        view.setVisibility(View.GONE);
        Toast.makeText(AppController.getInstance(), "Post dismissed!", Toast.LENGTH_SHORT).show();
    }
}
