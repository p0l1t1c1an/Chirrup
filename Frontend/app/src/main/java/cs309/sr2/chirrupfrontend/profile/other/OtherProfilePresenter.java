package cs309.sr2.chirrupfrontend.profile.other;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
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
     * volley requester for the post
     */
    private VolleyRequester volleyRequester;

    /**
     * view of profile fragment
     */
    private View view;

    /**
     * id of user viewing this page
     */
    private int viewerID;

    /**
     * true if the view user followed this person
     */
    private boolean followed;

    /**
     * number of followers the user has
     */
    private int followers;

    /**
     * whether the current user has the user blocked or not
     */
    private boolean blocked;

    /**
     * url for user json object
     */
    private String userURL;

    /**
     * url for user avatar
     */
    private String imageURL;

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
     * @param userURL  url for the user json object
     * @param imageURL url for user avatar
     * @param viewerID id of user viewing this page
     */
    public void loadData(String userURL, String imageURL, int viewerID) {
        volleyRequester = new VolleyRequester(this);
        volleyRequester.getObject(userURL);
        volleyRequester.getImage(imageURL);
        this.userURL = userURL;
        this.imageURL = imageURL;
        this.viewerID = viewerID;
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
            for (int i = posts.length() - 1; i >= 0; i--) {
                try {
                    PostFragment post = new PostFragment(posts.getInt(i));
                    AppController.getFragmentManager().beginTransaction().add(R.id.otherprofile_feed_layout, post)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            JSONArray followArray = response.getJSONArray("followers");
            followers = followArray.length();
            for (int i = 0; i < followers; i++) {
                if (followArray.getInt(i) == viewerID) {
                    followed = true;
                    break;
                }
            }

            if (followed) {
                ((Button) view.findViewById(R.id.otherprofile_follow)).setText("Unfollow (" + followers + ")");
            } else {
                ((Button) view.findViewById(R.id.otherprofile_follow)).setText("Follow (" + followers + ")");
            }

            JSONArray blockedArray = response.getJSONArray("blockers");
            for (int i = 0; i < blockedArray.length(); i++) {
                if (blockedArray.getInt(i) == viewerID) {
                    blocked = true;
                    break;
                }
            }

            if (blocked) {
                ((Button) view.findViewById(R.id.otherprofile_block)).setText("Unblock");
            } else {
                ((Button) view.findViewById(R.id.otherprofile_block)).setText("Block");
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

    /**
     * follow the user whose profile is being displayed
     *
     * @param url url used for following a user
     */
    public void followUser(String url) {
        Button follow = view.findViewById(R.id.otherprofile_follow);
        if (followed) {
            volleyRequester.setString(url, null, Request.Method.DELETE);
            follow.setText("Follow (" + (followers - 1) + ")");
            followers--;
            followed = false;
        } else {
            volleyRequester.setString(url, null, Request.Method.POST);
            follow.setText("Unfollow (" + (followers + 1) + ")");
            followers++;
            followed = true;
        }
    }

    /**
     * block the user whose profile is being displayed
     *
     * @param url url used for blocking a user
     */
    public void blockUser(String url) {
        Button blockButton = view.findViewById(R.id.otherprofile_block);
        if (blocked) {
            volleyRequester.setString(url, null, Request.Method.DELETE);
            blockButton.setText("Block");
            blocked = false;
        } else {
            volleyRequester.setString(url, null, Request.Method.POST);
            blockButton.setText("Unblock");
            blocked = true;
        }
    }

    /**
     * reload the page and all posts
     */
    public void reload() {
        LinearLayout layout = view.findViewById(R.id.otherprofile_feed_layout);
        layout.removeAllViews();
        volleyRequester.getObject(userURL);
        volleyRequester.getImage(imageURL);
    }
}
