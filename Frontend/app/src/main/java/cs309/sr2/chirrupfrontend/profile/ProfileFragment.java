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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;

import cs309.sr2.chirrupfrontend.R;
import cs309.sr2.chirrupfrontend.account.Session;
import cs309.sr2.chirrupfrontend.utils.AppController;
import cs309.sr2.chirrupfrontend.utils.PostCard;

/**
 * profile page fragment
 *
 * @author Jeremy Noesen
 */
public class ProfileFragment extends Fragment {

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
     * @param inflater layout inflater
     * @param container view group
     * @param savedInstanceState saved instance state
     * @return full profile page view
     */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        avatar = root.findViewById(R.id.avatar);
        username = root.findViewById(R.id.username);
        name = root.findViewById(R.id.name);
        bio = root.findViewById(R.id.bio);

        setProfileData();
        setAvatar();

        LinearLayout postLayout = root.findViewById(R.id.profile_feed_layout);
        for(int i : getPosts()) {
            postLayout.addView(new PostCard(inflater, container, i).getView());
        }

        Button followers = root.findViewById(R.id.followers);
        Button following = root.findViewById(R.id.following);

        followers.setOnClickListener(v -> {
            //show followers list
        });

        following.setOnClickListener(v -> {
            //show following list
        });


//        linearLayout.addView(new PostCard(inflater, container, 5).getView());

        return root;
    }

    /**
     * get the user's avatar image from the database and apply it to the image view
     */
    private void setAvatar() {

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
     * get the user's username, name, and bio from the database
     */
    private void setProfileData() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                getString(R.string.base_url) + "user/" + Session.getUser(), null,
                response -> {
                    try {
                        bio.setText(response.getString("biography"));
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
     * get all of the user's posts and add them to the post list
     *
     * @return list of post ids
     */
    private int[] getPosts() {

    }
}