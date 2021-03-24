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
import androidx.fragment.app.Fragment;

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
public class PostCardFragment extends Fragment {

    /**
     * identification number representing user
     */
    private int userID;

    /**
     * avatar image view
     */
    private ImageView avatar;

    /**
     * username text view
     */
    private TextView username;

    /**
     * name text view
     */
    private TextView name;

    /**
     * post body text view
     */
    private TextView body;

    /**
     * timestamp text view
     */
    private TextView timestamp;

    /**
     * create a new post card ui fragment to show a user post
     */
    public PostCardFragment() {
        //todo set which post is being referenced
        userID = 9; //todo set from post data
    }

    /**
     * set up the post card contents and return the full post
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return post card view
     */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_postcard, container, false);

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

        return root;
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
                getString(R.string.base_url) + "user/" + userID, null,
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
                getString(R.string.base_url) + "url_here", null, //todo put proper url here
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

}
