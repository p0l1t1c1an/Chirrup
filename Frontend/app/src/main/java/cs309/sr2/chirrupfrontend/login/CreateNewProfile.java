package cs309.sr2.chirrupfrontend.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.toolbox.ImageLoader;

import org.json.JSONArray;
import org.json.JSONObject;

import cs309.sr2.chirrupfrontend.R;
import cs309.sr2.chirrupfrontend.volley.VolleyListener;

/**
 * This is the class that runs when a user creates a new profile
 *
 * @author William Zogg
 */
public class CreateNewProfile extends Fragment implements VolleyListener {

    /**
     * This is the method that runs when opening the page. The parameters are given to it by program that calls it.
     *
     * @param inflater Turns the corresponding XML file into a layout.
     * @param container A group of views.
     * @param savedInstanceState A map of string keys.
     * @return New view for this fragment.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_newprofile, container, false);

        root.findViewById(R.id.createNewProfileButton).setOnClickListener((v) -> {
            //send the new user info to the server
        });

        return root;
    }

    /**
     * Runs when a string response is received
     *
     * @param response response from request
     */
    public void onStringResponse(String response) {

    }

    /**
     * Runs when a json object response is received
     *
     * @param response response from request
     */
    public void onObjectResponse(JSONObject response) {

    }

    /**
     * Runs when an image response is received
     *
     * @param response response from request
     */
    public void onImageResponse(ImageLoader.ImageContainer response) {

    }

    /**
     * Runs when an array response is received
     *
     * @param response response from request
     */
    public void onArrayResponse(JSONArray response) {

    }

}
