package cs309.sr2.chirrupfrontend;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

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
     * bio text view
     */
    private TextView bio;

    /**
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

        //stuff here

        getAvatar();

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

//    /**
//     * get the user's username from the database
//     *
//     * @return user's username
//     */
//    private String getUsername() {
//
//    }
//
//    /**
//     * get the user's name from the database
//     *
//     * @return user's name
//     */
//    private String getName() {
//
//    }
//
//    /**
//     * get the user's bio from the database
//     *
//     * @return user's bio
//     */
//    private String getBio() {
//
//    }

}