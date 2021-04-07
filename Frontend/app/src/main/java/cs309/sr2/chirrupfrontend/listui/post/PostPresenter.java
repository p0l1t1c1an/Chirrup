package cs309.sr2.chirrupfrontend.listui.post;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cs309.sr2.chirrupfrontend.R;
import cs309.sr2.chirrupfrontend.profile.other.OtherProfileFragment;
import cs309.sr2.chirrupfrontend.utils.AppController;
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
    public PostPresenter(View view) {
        this.view = view;
    }

    /**
     * load the data for the post
     *
     * @param postURL url for the post json object
     * @param userURL url for the user json object with id replaced with #
     * @param imageURL url for user avatar with user id replaced with #
     * @param likeUserID id of user viewing post
     */
    public void loadData(String postURL, String userURL, String imageURL, int likeUserID) {
        volleyRequester = new VolleyRequester(this);
        volleyRequester.getObject(postURL);
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
                setPostData(postData);
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
     * add or remove a like from a post
     *
     * @param url post liking url
     */
    public void likePost(String url) {
        likePostLocal();
        likePostRemote(url);
    }

    /**
     * like a post locally, but not remotely
     */
    public void likePostLocal() {
        Button like = view.findViewById(R.id.post_like);
        if(liked) {
            like.setText("Like (" + (likes - 1) + ")");
            likes--;
            liked = false;
        } else {
            like.setText("Unlike (" + (likes + 1) + ")");
            likes++;
            liked = true;
        }
    }

    /**
     * add or remove like in the server
     *
     * @param url url for like request
     */
    public void likePostRemote(String url) {
        if(liked) {
            volleyRequester.setString(url, null, Request.Method.DELETE);
        } else {
            volleyRequester.setString(url, null, Request.Method.POST);
        }
    }

    /**
     * get the number of likes on the post
     *
     * @return number of likes on post
     */
    public int getLikes() {
        return likes;
    }

    /**
     * cehck if the post was liked by the current user
     *
     * @return true if the post was liked by the current user
     */
    public boolean isLiked() {
        return liked;
    }

    /**
     * get the number of likes this post has, and if the current user has previously liked it already
     *
     * @param postData post the likes are on
     * @throws JSONException
     */
    public void loadLikeData(JSONObject postData) throws JSONException {
        JSONArray likesArray = postData.getJSONArray("likes");
        likes = likesArray.length();
        for (int i = 0; i < likes; i++) {
            if (likesArray.getInt(i) == likeUserID) {
                liked = true;
                break;
            }
        }
    }

    /**
     * show the profile of the post creator
     */
    public void showProfile() {
        OtherProfileFragment profile = new OtherProfileFragment(creatorID);
        AppController.getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, profile)
                .addToBackStack(null).commit();
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

        loadLikeData(postData);

        if(liked) {
            ((Button) view.findViewById(R.id.post_like)).setText("Unlike (" + likes + ")");
        } else {
            ((Button) view.findViewById(R.id.post_like)).setText("Like (" + likes + ")");
        }

        int comments = postData.getJSONArray("comments").length();
        ((Button) view.findViewById(R.id.post_comment)).setText("Comment (" + comments + ")");

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
}
