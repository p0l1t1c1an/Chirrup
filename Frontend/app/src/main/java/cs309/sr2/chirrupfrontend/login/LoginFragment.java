package cs309.sr2.chirrupfrontend.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.toolbox.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cs309.sr2.chirrupfrontend.R;
import cs309.sr2.chirrupfrontend.settings.CurrentUserData;
import cs309.sr2.chirrupfrontend.volley.VolleyListener;
import cs309.sr2.chirrupfrontend.volley.VolleyRequester;

/**
 * This class is what runs when the user enters the app, where they are prompted
 * to create a new profile or create a new one
 *
 * @author William Zogg
 */
public class LoginFragment extends Fragment implements VolleyListener {

    //staus textfield
    private TextView status;
    //VolleyRequester
    private VolleyRequester VolleyRequester;

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
        View root = inflater.inflate(R.layout.fragment_login, container, false);


        root.findViewById(R.id.loginSubmit).setOnClickListener((v) -> {
            //send login info to server and get user back (onResponse)
            try {
                EditText usernameToGet = (EditText) root.findViewById(R.id.login_usernameText);

                JSONObject toSend = new JSONObject();

                VolleyRequester = new VolleyRequester(this);
                VolleyRequester.getString(getResources().getString(R.string.base_url) + "user/?user={" +
                        usernameToGet + "}&first={}&last={}");
            } catch (Exception e) {}
        });

        return root;
    }

    /**
     * Runs when a string response is received
     *
     * @param response response from request
     */
    public void onStringResponse(String response) {
        status = getView().findViewById(R.id.loginStatusText);
        try {
            JSONObject res = new JSONObject(response);
            status.setText(res.getString("username"));
        } catch (Exception e) {}
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
