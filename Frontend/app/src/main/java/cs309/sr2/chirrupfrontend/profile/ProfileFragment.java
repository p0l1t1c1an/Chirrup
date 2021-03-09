package cs309.sr2.chirrupfrontend.profile;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import cs309.sr2.chirrupfrontend.R;
import cs309.sr2.chirrupfrontend.utils.AppController;

/**
 * profile page fragment
 *
 * @author Jeremy Noesen
 */
public class ProfileFragment extends Fragment {

    /**
     * identification number representing user
     */
    private int userID = 6;

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
     * bio text view
     */
    private TextView bio;

    /**
     * retrieve the profile data to fill in the text fields and avatar
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return profile page view
     */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        avatar = root.findViewById(R.id.avatar);
        username = root.findViewById(R.id.username);
        name = root.findViewById(R.id.name);
        bio = root.findViewById(R.id.bio);

        getNameAndUsername();
        getBio();
        getAvatar();

        Button followers = root.findViewById(R.id.followers);
        Button following = root.findViewById(R.id.following);

        followers.setOnClickListener(v -> {
            //show followers list
        });

        following.setOnClickListener(v -> {
            //show following list
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
    private void getNameAndUsername() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                getString(R.string.base_url) + "user/" + userID, null,
                (Response.Listener<JSONObject>) response -> {
                    try {
                        username.setText(response.getString("username"));
                        name.setText(response.getString("firstname") + " " +
                                response.getString("lastname"));
                    } catch (JSONException e) {
                        Log.e(ProfileFragment.class.getSimpleName(), "JSON Load Error: " + e.getMessage());
                        e.printStackTrace();
                    }
                }, (Response.ErrorListener) error ->
                VolleyLog.d(ProfileFragment.class.getSimpleName(), "Error: " + error.getMessage()));


        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }


    /**
     * get the user's bio from the database
     */
    private void getBio() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                getString(R.string.base_url) + "profile/" + userID, null,
                (Response.Listener<JSONObject>) response -> {
                    try {
                        bio.setText(response.getString("biography"));
                    } catch (JSONException e) {
                        Log.e(ProfileFragment.class.getSimpleName(), "JSON Load Error: " + e.getMessage());
                        e.printStackTrace();
                    }
                }, (Response.ErrorListener) error ->
                VolleyLog.d(ProfileFragment.class.getSimpleName(), "Error: " + error.getMessage()));


        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

}