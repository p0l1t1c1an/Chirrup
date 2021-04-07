package cs309.sr2.chirrupfrontend.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.toolbox.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cs309.sr2.chirrupfrontend.R;
import cs309.sr2.chirrupfrontend.volley.VolleyListener;
import cs309.sr2.chirrupfrontend.volley.VolleyRequester;

/**
 * This is the class that runs when a user creates a new profile
 *
 * @author William Zogg
 */
public class CreateNewProfile extends Fragment implements VolleyListener {

    //VolleyRequester
    private VolleyRequester VolleyRequester;
    //email text
    private EditText email;
    //password text
    private EditText password;
    //username text
    private EditText username;
    //first name text
    private EditText firstname;
    //last name text
    private EditText lastname;
    //phone number text
    private EditText phoneNumber;
    //biography text
    private EditText bio;
    //birthday text
    private EditText birthday;


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

        VolleyRequester = new VolleyRequester(this);

        root.findViewById(R.id.createNewProfileButton).setOnClickListener((v) -> {
            //send the new user info to the server
            email = root.findViewById(R.id.newProfileEmail);
            password = root.findViewById(R.id.newProfilePassword);
            username = root.findViewById(R.id.newProfileUsername);
            firstname = root.findViewById(R.id.newProfileFirst);
            lastname = root.findViewById(R.id.newProfileLast);
            phoneNumber = root.findViewById(R.id.newProfilePhone);
            bio = root.findViewById(R.id.newProfileBio);
            birthday = root.findViewById(R.id.newProfileBirthday);
            JSONObject toSend = new JSONObject();
            try {
                toSend.put("id", 123);
                toSend.put("email", email.getText().toString());
                toSend.put("password", password.getText().toString());
                toSend.put("username", username.getText().toString());
                toSend.put("firstname", firstname.getText().toString());
                toSend.put("lastname", lastname.getText().toString());
                toSend.put("telephone", phoneNumber.getText().toString());
                toSend.put("biography", bio.getText().toString());
                toSend.put("birthday", birthday.getText().toString());

                VolleyRequester.setObject(getResources().getString(R.string.base_url) + "user/" + 123, toSend, Request.Method.POST);
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
