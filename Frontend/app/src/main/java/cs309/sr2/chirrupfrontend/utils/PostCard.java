package cs309.sr2.chirrupfrontend.utils;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;

import cs309.sr2.chirrupfrontend.R;
import cs309.sr2.chirrupfrontend.profile.ProfileFragment;

/**
 * A UI element that will show the contents of a user post, including details about who posted, what
 * was posted, and likes
 *
 * @author Jeremy Noesen
 */
public class PostCard {

    /**
     * identification number representing user
     */
    private final int userID;

    /**
     * identification number representing post
     */
    private final int postID;

    /**
     * avatar image view
     */
    private final ImageView avatar;

    /**
     * username text view
     */
    private final TextView username;

    /**
     * name text view
     */
    private final TextView name;

    /**
     * post body text view
     */
    private final TextView body;

    /**
     * timestamp text view
     */
    private final TextView timestamp;

    /**
     * root view for post
     */
    private final View root;

    /**
     * set up the post card contents
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     */
    public PostCard(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState,
                    int userID, int postID) {

        this.userID = userID;
        this.postID = postID;

        root = inflater.inflate(R.layout.ui_postcard, container, false);

        avatar = root.findViewById(R.id.postavatar);
        username = root.findViewById(R.id.postusername);
        name = root.findViewById(R.id.postname);
        body = root.findViewById(R.id.postbody);
        timestamp = root.findViewById(R.id.posttimestamp);

        getUserText();
      //  getPostText();
        getAvatar();

        Button like = root.findViewById(R.id.postlike);
        Button share = root.findViewById(R.id.postshare);
        Button comment = root.findViewById(R.id.postcomment);

        like.setOnClickListener(v -> {
            //add or remove like
        });

        share.setOnClickListener(v -> {
            //show share options or just copy a link to the post
        });

        comment.setOnClickListener(v -> {
            //show compose UI to add reply
        });
    }

    /**
     * get the user's avatar image from the database and apply it to the image view
     */
    private void getAvatar() {

        String testURL = "https://api.androidhive.info/volley/volley-image.jpg";

        ImageLoader imageLoader = AppController.getInstance().getImageLoader();

        imageLoader.get(testURL, new ImageLoader.ImageListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(ProfileFragment.class.getSimpleName(), "Image Load Error: " + error.getMessage());
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {
                    avatar.setImageBitmap(response.getBitmap());
                }
            }
        });

    }

    /**
     * get the user's username and name from the database
     */
    private void getUserText() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                AppController.getInstance().getString(R.string.base_url) + "user/" + userID, null,
                response -> {
                    try {
                        username.setText(response.getString("username"));
                        name.setText(response.getString("firstname") + " " +
                                response.getString("lastname"));
                    } catch (JSONException e) {
                        Log.e(ProfileFragment.class.getSimpleName(), "JSON Load Error: " + e.getMessage());
                        e.printStackTrace();
                    }
                }, error ->
                VolleyLog.d(ProfileFragment.class.getSimpleName(), "Error: " + error.getMessage()));


        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    /**
     * get the post data from the database
     */
    private void getPostText() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                AppController.getInstance().getString(R.string.base_url) + "url_here", null, //todo put proper url here
                response -> {
                    try {
                        body.setText(response.getString("body"));
                        timestamp.setText(response.getString("time"));
                    } catch (JSONException e) {
                        Log.e(ProfileFragment.class.getSimpleName(), "JSON Load Error: " + e.getMessage());
                        e.printStackTrace();
                    }
                }, error ->
                VolleyLog.d(ProfileFragment.class.getSimpleName(), "Error: " + error.getMessage()));


        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    /**
     * get the view of the post
     *
     * @return view of post
     */
    public View getView() {
        return root;
    }

}
